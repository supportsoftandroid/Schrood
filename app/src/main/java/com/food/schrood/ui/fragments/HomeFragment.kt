package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.food.schrood.R

import com.food.schrood.databinding.FragmentHomeBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.adapter.*
import com.food.schrood.utility.StaticData
import com.food.schrood.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var homeViewModel: HomeViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var adaper: CategoryHomeAdapter
    lateinit var orderAdaper: RecentOrderAdapter
    lateinit var adapterStore: StoreAdapter
    var categoryList = mutableListOf<CommonDataItem>()
    var orderList = mutableListOf<CommonDataItem>()
    var storeList = mutableListOf<CommonDataItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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


        adaper = CategoryHomeAdapter(requireActivity(), categoryList) { type, pos ->
            onCategoryClick(
                type,
                pos
            )
        }
        binding.rvCategory.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategory.adapter = adaper

        orderList.clear()
        orderList.add(CommonDataItem("Pizza 4P", "Soups", false))
        orderList.add(CommonDataItem("Spaghetti Land", "Spaghetti Land", false))
        orderList.add(CommonDataItem("Dessert Journey", "Dessert Journey", false))

        orderAdaper = RecentOrderAdapter(requireActivity(), orderList) { type, pos ->
            onOrderClick(
                type,
                pos
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


        adapterStore = StoreAdapter(
            requireActivity(),
            storeList,
            { type, pos -> onStoreClick(type, pos) })
        binding.rvStore.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvStore.adapter = adapterStore


    }

    private fun clickListener() {
        binding.clvSearch.setOnClickListener() {
            StaticData.backStackAddFragment(requireActivity(), SearchFragment())

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCategoryClick(type: String, position: Int) {

    }

    private fun onOrderClick(type: String, position: Int) {

    }

    private fun onStoreClick(type: String, position: Int) {

    }
}