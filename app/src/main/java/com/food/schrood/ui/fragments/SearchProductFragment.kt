package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.DialogBottomProductDetailsBinding
import com.food.schrood.databinding.DialogDeleteCartBinding
import com.food.schrood.databinding.DialogReplaceCartBinding
import com.food.schrood.databinding.FragmentSearchFoodStoreBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.activities.MainActivity
import com.food.schrood.ui.adapter.*
import com.food.schrood.utility.StaticData
import com.food.schrood.viewmodel.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class SearchProductFragment : Fragment() {

    private var _binding: FragmentSearchFoodStoreBinding? = null
    lateinit var viewModal: SearchViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var statusList = mutableListOf<CommonDataItem>()
    var productList = mutableListOf<CommonDataItem>()
    var storeList = mutableListOf<CommonDataItem>()
    lateinit var typeAdapter: FoodTypeAdapter
    lateinit var adaper: ProductItemAdapter
    lateinit var adapterStore: StoreItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SearchViewModel::class.java)
        _binding = FragmentSearchFoodStoreBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.let {
            initView()
            clickListener()
        }

        return root
    }

    fun initView() {

        // Food Type
        statusList.add(CommonDataItem("Any", "Any", false))
        statusList.add(CommonDataItem("Vegetarian", "Vegetarian", false))
        statusList.add(CommonDataItem("Non-Veg ", "Non Veg", true))
        statusList.add(CommonDataItem("Vegan", "Vegan", false))

        typeAdapter = FoodTypeAdapter(requireActivity(), statusList) { pos, type -> onStatusClick(pos, type)
        }
        binding.rvStatusList.layoutManager = GridLayoutManager(requireActivity(), 4)
        binding.rvStatusList.adapter = typeAdapter


        //product
        productList.clear()
        productList.add(CommonDataItem("Appetizers", "all", true))
        productList.add(CommonDataItem("Breakfast", "breakfast", false))
        productList.add(CommonDataItem("Lunch", "Lunch", false))
        productList.add(CommonDataItem("Dinner", "Dinner", false))
        productList.add(CommonDataItem("Pasta", "Pasta", false))

        adaper = ProductItemAdapter(requireActivity(), productList) { pos, type ->
            onProductClick(
                pos,
                type
            )
        }

        storeList.add(CommonDataItem("Pizza Italia Restaurant & Cafe", "Any", false))
        storeList.add(CommonDataItem("Sydney  Restaurant & Cafe", "Vegetarian", true))
        storeList.add(CommonDataItem("Rock Italia Restaurant & Cafe", "Non Veg", false))
        storeList.add(CommonDataItem("Johan Italia Restaurant & Cafe", "Vegan", false))

        adapterStore = StoreItemAdapter(requireActivity(), storeList) { pos, type -> onStoreClick(pos, type) }
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter = adapterStore
        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbStore -> {
                    binding.rbStore.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.app_color
                        )
                    )
                    binding.rbProduct.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.colorText
                        )
                    )
                    binding.rvList.adapter = adapterStore

                }
                R.id.rbProduct -> {

                    binding.rbProduct.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.app_color
                        )
                    )
                    binding.rbStore.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.textPlaceHolder
                        )
                    )
                    binding.rvList.adapter = adaper
                }

            }


        }

    }

    private fun onStatusClick(pos: Int, type: String) {

    }

    private fun clickListener() {
        binding.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()

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

    private fun onProductClick(position: Int, type: String) {
        dialogProductDetails()
    }

    private fun onStoreClick(position: Int, type: String) {
        StaticData.backStackAddFragment(
            requireActivity(),
            StoreDetailsFragment.newInstance(storeList[position].title)
        )
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
            binding.llCartView.visibility=View.GONE
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
        dialogBinding.rvList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        dialogBinding.rvList.adapter = adapterImage


        // unit Type
        val unitList = mutableListOf<CommonDataItem>()
        unitList.add(CommonDataItem("Small", "Small", false))
        unitList.add(CommonDataItem("Medium", "Medium", false))
        unitList.add(CommonDataItem("Large", "Large", true))


        val unitAdapter = UnitItemAdapter(requireActivity(), unitList) { pos, type -> onUnitClick(pos, type)
        }
        dialogBinding.rvFoodVariantList.layoutManager =LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        dialogBinding.rvFoodVariantList.adapter = unitAdapter

        dialogBinding.imgBack.setOnClickListener() {
          dialogProduct.dismiss()
        }
        dialogBinding.tvPlus.setOnClickListener() {
            val count =dialogBinding.tvCount.text.toString().toInt()
            val newCountValue = count + 1
            dialogBinding.tvCount.text = newCountValue.toString()
        }

        dialogBinding.tvMinus.setOnClickListener() {
            val count =dialogBinding.tvCount.text.toString().toInt()
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
            binding.llCartView.visibility=View.VISIBLE
        }

        dialogReplaceCard.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}