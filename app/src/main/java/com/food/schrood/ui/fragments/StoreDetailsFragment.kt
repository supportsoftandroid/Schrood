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
import com.food.schrood.databinding.FragmentStoreDetailsBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.activities.MainActivity
import com.food.schrood.ui.adapter.ProductImageAdapter
import com.food.schrood.ui.adapter.ProductStoreItemAdapter
import com.food.schrood.ui.adapter.TypeAdapter
import com.food.schrood.ui.adapter.UnitItemAdapter
import com.food.schrood.utility.StaticData
import com.food.schrood.viewmodel.StoreDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class StoreDetailsFragment : Fragment() {
    companion object {
        const val Store_Details_REQUEST_KEY = "store_details"
        fun newInstance(title: String): StoreDetailsFragment {
            val args = Bundle()
            args.putString("title", title)
            val fragment = StoreDetailsFragment()
            fragment.arguments = args
            return fragment
        }


    }

    private var _binding: FragmentStoreDetailsBinding? = null
    lateinit var viewModal: StoreDetailsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var productList = mutableListOf<CommonDataItem>()
    var storeTypeList = mutableListOf<CommonDataItem>()
    lateinit var adaper: ProductStoreItemAdapter
    lateinit var typeAdapter: TypeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModal = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            StoreDetailsViewModel::class.java
        )
        _binding = FragmentStoreDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.let {
            initView()
            clickListener()
        }

        return root
    }

    private fun clickListener() {
        binding.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()

        }
        binding.viewStoreInfo.llStoreReview.setOnClickListener() {
            StaticData.backStackAddFragment(requireActivity(), ReviewsFragment())

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

    fun initView() {
        binding.viewStoreInfo.tvStoreWorkingHours.visibility=View.VISIBLE
        binding.viewStoreInfo.llButtonOption.visibility=View.VISIBLE
        binding.viewStoreInfo.imgCall.visibility=View.VISIBLE
        binding.viewStoreInfo.imgMessage.visibility=View.VISIBLE


        // Food Type
        storeTypeList.add(CommonDataItem("Veg Items", "Vegetarian", false))
        storeTypeList.add(CommonDataItem("Non-veg Items", "Non Veg", true))
        storeTypeList.add(CommonDataItem("Vegan Items", "Vegan", false))

        typeAdapter = TypeAdapter(requireActivity(), storeTypeList) { pos, type ->
            onStoreTypeClick(
                pos,
                type
            )
        }
        binding.rvStatusList.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.rvStatusList.adapter = typeAdapter

        productList.clear()
        productList.add(CommonDataItem("Appetizers", "all", true))
        productList.add(CommonDataItem("Breakfast", "breakfast", false))
        productList.add(CommonDataItem("Lunch", "Lunch", true))
        productList.add(CommonDataItem("Dinner", "Dinner", true))
        productList.add(CommonDataItem("Pasta", "Pasta", false))
        productList.add(CommonDataItem("Appetizers", "all", true))
        productList.add(CommonDataItem("Breakfast", "breakfast", false))
        productList.add(CommonDataItem("Lunch", "Lunch", true))
        productList.add(CommonDataItem("Dinner", "Dinner", true))
        productList.add(CommonDataItem("Pasta", "Pasta", false))

        adaper = ProductStoreItemAdapter(requireActivity(), productList) { pos, type ->
            onProductClick(
                pos,
                type
            )
        }
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter = adaper
        binding.rgServiceType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbDelivery -> {
                    binding.rbDelivery.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.app_color
                        )
                    )
                    binding.rbTakeAway.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.colorText
                        )
                    )
                }
                R.id.rbTakeAway -> {
                    binding.rbTakeAway.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.app_color
                        )
                    )
                    binding.rbDelivery.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.textPlaceHolder
                        )
                    )

                }

            }


        }

    }

    private fun onProductClick(pos: Int, type: String) {
        dialogProductDetails()

    }

    private fun onStoreTypeClick(pos: Int, type: String) {

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