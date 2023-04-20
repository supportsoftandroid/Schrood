package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.DialogBottomProductDetailsBinding
import com.food.schrood.databinding.DialogDeleteCartBinding
import com.food.schrood.databinding.DialogReplaceCartBinding
import com.food.schrood.databinding.FragmentSearchBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.activities.MainActivity
import com.food.schrood.ui.adapter.ProductImageAdapter
import com.food.schrood.ui.adapter.ProductItemAdapter
import com.food.schrood.ui.adapter.RecentSearchAdapter
import com.food.schrood.ui.adapter.UnitItemAdapter
import com.food.schrood.utility.StaticData
import com.food.schrood.viewmodel.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    lateinit var viewModal: SearchViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var recentSearchList = mutableListOf<CommonDataItem>()
    var dataList = mutableListOf<CommonDataItem>()
    lateinit var adaper: RecentSearchAdapter
    lateinit var adapterProduct: ProductItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SearchViewModel::class.java)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.let {
            initView()
            clickListener()
        }

        return root
    }

    fun initView() {


        recentSearchList.clear()
        recentSearchList.add(CommonDataItem("Appetizers", "all", true))
        recentSearchList.add(CommonDataItem("Breakfast", "breakfast", false))
        recentSearchList.add(CommonDataItem("Lunch", "Lunch", false))
        recentSearchList.add(CommonDataItem("Dinner", "Dinner", false))
        recentSearchList.add(CommonDataItem("Pasta", "Pasta", false))

        adaper = RecentSearchAdapter(requireActivity(), recentSearchList) { pos, type ->
            onRecentClick(
                pos,
                type
            )
        }
        binding.rvRecentSearch.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRecentSearch.adapter = adaper

        dataList.clear()
        dataList.add(CommonDataItem("Pizza 4P", "Soups", false))
        dataList.add(CommonDataItem("Spaghetti Land", "Spaghetti Land", false))
        dataList.add(CommonDataItem("Dessert Journey", "Dessert Journey", false))

        adapterProduct = ProductItemAdapter(requireActivity(), dataList) { pos, type ->
            onProductClick(
                pos,
                type
            )
        }
        binding.rvRecommended.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRecommended.adapter = adapterProduct


    }

    private fun clickListener() {
        binding.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()

        }
        binding.clvSearch.setOnClickListener() {
            StaticData.backStackAddFragment(requireActivity(), SearchProductFragment())

        }
        binding.imgFilter.setOnClickListener() {
            StaticData.backStackAddFragment(requireActivity(), FilterFragment())

        }
        binding.cartViewBody.tvViewCart.setOnClickListener() {
            StaticData.changeStatusBarColor(requireActivity(), "other")
            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(requireActivity(), CartFragment())

        }
        binding.cartViewBody.imgDelete.setOnClickListener() {
            dialogDeleteCart()

        }
    }

    private fun onRecentClick(position: Int, type: String) {
        StaticData.backStackAddFragment(requireActivity(), SearchProductFragment())
    }

    private fun onProductClick(position: Int, type: String) {
        dialogProductDetails()
    }

    fun dialogDeleteCart() {
        val dialogBinding =
            DialogDeleteCartBinding.inflate(
                LayoutInflater.from(requireActivity()),
                null,
                false
            )
        val dialogDelete = BottomSheetDialog(requireActivity(), R.style.GalleryDialog)
        dialogDelete.setContentView(dialogBinding.root)



        dialogBinding.btnCancel.setOnClickListener {
            dialogDelete.dismiss()
        }
        dialogBinding.btnDelete.setOnClickListener {
            dialogDelete.dismiss()
            binding.llCartView.visibility = View.GONE
        }

        dialogDelete.show()
    }

    fun dialogProductDetails() {
        val dialogBinding =
            DialogBottomProductDetailsBinding.inflate(
                LayoutInflater.from(requireActivity()),
                null,
                false
            )
        val dialogProduct = BottomSheetDialog(requireActivity(), R.style.GalleryDialog)
        dialogProduct.setContentView(dialogBinding.root)
        val sheetView = dialogBinding.root
        dialogProduct.setCancelable(false)
        val screenHeight = resources.displayMetrics.heightPixels
        val layoutParams = sheetView.layoutParams
        layoutParams.height = screenHeight
        sheetView.layoutParams = layoutParams

        // Set the bottom sheet to be fullscreen
        dialogProduct.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        val productImageList = mutableListOf<CommonDataItem>()
        productImageList.add(CommonDataItem("Pizza Italia Restaurant & Cafe", "Any", false))
        productImageList.add(CommonDataItem("Sydney  Restaurant & Cafe", "Vegetarian", true))
        productImageList.add(CommonDataItem("Rock Italia Restaurant & Cafe", "Non Veg", false))
        productImageList.add(CommonDataItem("Johan Italia Restaurant & Cafe", "Vegan", false))

        val adapterImage = ProductImageAdapter(requireActivity(), productImageList)
        dialogBinding.rvList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        dialogBinding.rvList.adapter = adapterImage


        // unit Type
        val unitList = mutableListOf<CommonDataItem>()
        unitList.add(CommonDataItem("Small", "Small", false))
        unitList.add(CommonDataItem("Medium", "Medium", false))
        unitList.add(CommonDataItem("Large", "Large", true))


        val unitAdapter = UnitItemAdapter(requireActivity(), unitList) { pos, type ->
            onUnitClick(pos, type)
        }
        dialogBinding.rvFoodVariantList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        dialogBinding.rvFoodVariantList.adapter = unitAdapter

        dialogBinding.imgBack.setOnClickListener() {
            dialogProduct.dismiss()
        }
        dialogBinding.tvPlus.setOnClickListener() {
            val count = dialogBinding.tvCount.text.toString().toInt()
            val newCountValue = count + 1
            dialogBinding.tvCount.text = newCountValue.toString()
        }

        dialogBinding.tvMinus.setOnClickListener() {
            val count = dialogBinding.tvCount.text.toString().toInt()
            if (count > 0) {
                val newCountValue = count - 1
                dialogBinding.tvCount.text = newCountValue.toString()
            }


        }



        dialogBinding.llAddToCart.setOnClickListener {
            dialogProduct.dismiss()
            dialogReplaceCart()
        }

        dialogProduct.show()
    }

    private fun onUnitClick(pos: Int, type: String) {

    }

    fun dialogReplaceCart() {
        val dialogBinding =
            DialogReplaceCartBinding.inflate(
                LayoutInflater.from(requireActivity()),
                null,
                false
            )
        val dialogReplaceCard = BottomSheetDialog(requireActivity(), R.style.GalleryDialog)
        dialogReplaceCard.setContentView(dialogBinding.root)



        dialogBinding.btnCancel.setOnClickListener {
            dialogReplaceCard.dismiss()
        }
        dialogBinding.btnReplace.setOnClickListener {
            dialogReplaceCard.dismiss()
            binding.llCartView.visibility = View.VISIBLE
        }

        dialogReplaceCard.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}