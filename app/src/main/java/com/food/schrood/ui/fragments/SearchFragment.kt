package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.food.schrood.databinding.FragmentHomeBinding
import com.food.schrood.databinding.FragmentSearchBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.adapter.*
import com.food.schrood.utility.StaticData
import com.food.schrood.viewmodel.HomeViewModel
import com.food.schrood.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    lateinit var viewModal: SearchViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var recentSearchList = mutableListOf<CommonDataItem>()
    var dataList = mutableListOf<CommonDataItem>()
    lateinit var adaper: RecentSearchAdapter
    lateinit var adapterProduct: RecommSearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModal = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SearchViewModel::class.java)
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

        adaper = RecentSearchAdapter(requireActivity(), recentSearchList) { type, pos -> onRecentClick(type, pos) }
        binding.rvRecentSearch.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecentSearch.adapter = adaper

        dataList.clear()
        dataList.add(CommonDataItem("Pizza 4P", "Soups", false))
        dataList.add(CommonDataItem("Spaghetti Land", "Spaghetti Land", false))
        dataList.add(CommonDataItem("Dessert Journey", "Dessert Journey", false))

        adapterProduct = RecommSearchAdapter(requireActivity(), dataList) { type, pos -> onProductClick(type, pos) }
        binding.rvRecommended.layoutManager =
        LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
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
    }

    private fun onRecentClick(type: String, position: Int) {

    }

    private fun onProductClick(type: String, position: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}