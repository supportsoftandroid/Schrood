package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.FragmentSearchFoodStoreBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.adapter.ProductItemAdapter
import com.food.schrood.ui.adapter.StoreItemAdapter
import com.food.schrood.utility.StaticData
import com.food.schrood.viewmodel.SearchViewModel

class SearchProductFragment : Fragment() {

    private var _binding: FragmentSearchFoodStoreBinding? = null
    lateinit var viewModal: SearchViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var productList = mutableListOf<CommonDataItem>()
    var storeList = mutableListOf<CommonDataItem>()
    lateinit var adaper: ProductItemAdapter
    lateinit var adapterStore: StoreItemAdapter
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

          //Status
        productList.clear()
        productList.add(CommonDataItem("Appetizers", "all", true))
        productList.add(CommonDataItem("Breakfast", "breakfast", false))
        productList.add(CommonDataItem("Lunch", "Lunch", false))
        productList.add(CommonDataItem("Dinner", "Dinner", false))
        productList.add(CommonDataItem("Pasta", "Pasta", false))

        adaper = ProductItemAdapter(requireActivity(), productList) {pos,type -> onRecentClick(pos,type) }
        binding.rvStatusList.layoutManager = LinearLayoutManager(requireActivity())
        storeList.add(CommonDataItem("Pizza Italia Restaurant & Cafe", "Any", false))
        storeList.add(CommonDataItem("Sydney  Restaurant & Cafe", "Vegetarian", true))
        storeList.add(CommonDataItem("Rock Italia Restaurant & Cafe", "Non Veg", false))
        storeList.add(CommonDataItem("Johan Italia Restaurant & Cafe", "Vegan", false))

        adapterStore = StoreItemAdapter(requireActivity(), storeList) { pos,type -> onStoreClick(pos,type) }
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())

        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rbStore -> {
                    binding.rbStore.setTextColor(ContextCompat.getColor(requireActivity(),R.color.app_color))
                    binding.rbProduct.setTextColor(ContextCompat.getColor(requireActivity(),R.color.colorText))
                    binding.rvList.adapter = adapterStore

                }
                R.id.rbProduct -> {

                    binding.rbProduct.setTextColor(ContextCompat.getColor(requireActivity(),R.color.app_color))
                    binding.rbStore.setTextColor(ContextCompat.getColor(requireActivity(),R.color.textPlaceHolder))
                    binding.rvList.adapter = adaper
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

    private fun onRecentClick( position: Int,type: String) {

    }

    private fun onStoreClick(position: Int,type: String) {
        StaticData.backStackAddFragment(requireActivity(),StoreDetailsFragment.newInstance(storeList[position].title))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}