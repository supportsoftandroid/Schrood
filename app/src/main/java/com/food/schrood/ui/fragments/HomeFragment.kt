package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.DialogDeleteCartBinding
import com.food.schrood.databinding.FragmentHomeBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.activities.MainActivity
import com.food.schrood.ui.adapter.CategoryHomeAdapter
import com.food.schrood.ui.adapter.RecentOrderAdapter
import com.food.schrood.ui.adapter.StoreItemAdapter
import com.food.schrood.utility.StaticData
import com.food.schrood.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var homeViewModel: HomeViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var adaper: CategoryHomeAdapter
    lateinit var orderAdaper: RecentOrderAdapter
    lateinit var adapterStore: StoreItemAdapter
    var categoryList = mutableListOf<CommonDataItem>()
    var orderList = mutableListOf<CommonDataItem>()
    var storeList = mutableListOf<CommonDataItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        StaticData.changeStatusBarColor(requireActivity(), "home")
        homeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.let {
            clickListener()
            initView()
        }
        val root: View = binding.root

        return root
    }

    fun initView() {

        categoryList.clear()
        categoryList.add(CommonDataItem("Appetizers", "all", true))
        categoryList.add(CommonDataItem("Breakfast", "breakfast", false))
        categoryList.add(CommonDataItem("Lunch", "Lunch", false))
        categoryList.add(CommonDataItem("Dinner", "Dinner", false))
        categoryList.add(CommonDataItem("Pasta", "Pasta", false))


        adaper = CategoryHomeAdapter(requireActivity(), categoryList) { pos, type ->
            onCategoryClick(
                pos,
                type

            )
        }
        binding.rvCategory.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategory.adapter = adaper

        orderList.clear()
        orderList.add(CommonDataItem("Pizza 4P", "Soups", false))
        orderList.add(CommonDataItem("Spaghetti Land", "Spaghetti Land", false))
        orderList.add(CommonDataItem("Dessert Journey", "Dessert Journey", false))

        orderAdaper = RecentOrderAdapter(requireActivity(), orderList) { pos,
                                                                         type ->
            onOrderClick(
                pos, type
            )
        }
        binding.rvRecentOrder.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecentOrder.adapter = orderAdaper


        storeList.clear()
        storeList.add(CommonDataItem("Pizza Italia Restaurant & Cafe", "Any", false))
        storeList.add(CommonDataItem("Sydney  Restaurant & Cafe", "Vegetarian", true))
        storeList.add(CommonDataItem("Rock Italia Restaurant & Cafe", "Non Veg", false))
        storeList.add(CommonDataItem("Johan Italia Restaurant & Cafe", "Vegan", false))
        storeList.add(CommonDataItem("Pizza Italia Restaurant & Cafe", "Any", false))
        storeList.add(CommonDataItem("Sydney  Restaurant & Cafe", "Vegetarian", true))
        storeList.add(CommonDataItem("Rock Italia Restaurant & Cafe", "Non Veg", false))
        storeList.add(CommonDataItem("Johan Italia Restaurant & Cafe", "Vegan", false))


        adapterStore = StoreItemAdapter(
            requireActivity(), storeList,
            { pos, type -> onStoreClick(pos, type) })
        binding.rvStore.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvStore.adapter = adapterStore


    }

    private fun clickListener() {
        binding.clvSearch.setOnClickListener() {
            StaticData.changeStatusBarColor(requireActivity(), "other")
            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(requireActivity(), SearchFragment())

        }
        binding.imgProfile.setOnClickListener() {
            StaticData.changeStatusBarColor(requireActivity(), "other")
            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(requireActivity(), ProfileFragment())

        }
        binding.tvLocation.setOnClickListener() {
            StaticData.changeStatusBarColor(requireActivity(), "other")
            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(requireActivity(), AddressListFragment())

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCategoryClick(position: Int, type: String) {
        StaticData.changeStatusBarColor(requireActivity(), "other")
        MainActivity.hideNavigationTab()
        StaticData.backStackAddFragment(
            requireActivity(),
            StoreDetailsFragment.newInstance(storeList[position].title)
        )
    }

    private fun onOrderClick(position: Int, type: String) {

    }

    private fun onStoreClick(position: Int, type: String) {
        StaticData.changeStatusBarColor(requireActivity(), "other")
        MainActivity.hideNavigationTab()
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
}