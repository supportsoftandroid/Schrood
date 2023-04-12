package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R

import com.food.schrood.databinding.FragmentHomeBinding
import com.food.schrood.databinding.FragmentSearchBinding
import com.food.schrood.databinding.FragmentSearchFoodStoreBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.adapter.*
import com.food.schrood.utility.StaticData
import com.food.schrood.viewmodel.HomeViewModel
import com.food.schrood.viewmodel.SearchViewModel

class SearchProductFragment : Fragment() {

    private var _binding: FragmentSearchFoodStoreBinding? = null
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
        _binding = FragmentSearchFoodStoreBinding.inflate(inflater, container, false)
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
        binding.rvStatusList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvStatusList.adapter = adaper

        dataList.clear()
        dataList.add(CommonDataItem("Pizza 4P", "Soups", false))
        dataList.add(CommonDataItem("Spaghetti Land", "Spaghetti Land", false))
        dataList.add(CommonDataItem("Dessert Journey", "Dessert Journey", false))

        adapterProduct = RecommSearchAdapter(requireActivity(), dataList) { type, pos -> onProductClick(type, pos) }
        binding.rvList.layoutManager =
        LinearLayoutManager(requireActivity())
        binding.rvList.adapter = adapterProduct
        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rbStore -> {
                    binding.rbStore.setTextColor(ContextCompat.getColor(requireActivity(),R.color.app_color))
                    binding.rbProduct.setTextColor(ContextCompat.getColor(requireActivity(),R.color.colorText))


                }
                R.id.rbProduct -> {

                    binding.rbProduct.setTextColor(ContextCompat.getColor(requireActivity(),R.color.app_color))
                    binding.rbStore.setTextColor(ContextCompat.getColor(requireActivity(),R.color.textPlaceHolder))

                }

            }


        }

    }

    private fun clickListener() {
        binding.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()

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