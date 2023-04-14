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
import com.food.schrood.databinding.FragmentStoreDetailsBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.adapter.ProductItemAdapter
import com.food.schrood.ui.adapter.TypeAdapter
import com.food.schrood.utility.StaticData
import com.food.schrood.viewmodel.StoreDetailsViewModel

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
    lateinit var adaper: ProductItemAdapter
    lateinit var typeAdapter: TypeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModal = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(StoreDetailsViewModel::class.java)
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
            StaticData.backStackAddFragment(requireActivity(),ReviewsFragment())

        }

    }
    fun initView() {


      // Food Type
        storeTypeList.add(CommonDataItem("Veg Items", "Vegetarian", false))
        storeTypeList.add(CommonDataItem("Non-veg Items", "Non Veg", true))
        storeTypeList.add(CommonDataItem("Vegan Items", "Vegan", false))

        typeAdapter = TypeAdapter(requireActivity(), storeTypeList) { pos,type -> onStoreTypeClick(pos,type) }
        binding.rvStatusList.layoutManager = GridLayoutManager(requireActivity(),3)
        binding.rvStatusList.adapter=typeAdapter

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

        adaper = ProductItemAdapter(requireActivity(), productList) {pos,type -> onRecentClick(pos,type) }
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter=adaper
        binding.rgServiceType.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rbDelivery -> {
                    binding.rbDelivery.setTextColor(ContextCompat.getColor(requireActivity(),R.color.app_color))
                    binding.rbTakeAway.setTextColor(ContextCompat.getColor(requireActivity(),R.color.colorText))
                }
                R.id.rbTakeAway -> {
                    binding.rbTakeAway.setTextColor(ContextCompat.getColor(requireActivity(),R.color.app_color))
                    binding.rbDelivery.setTextColor(ContextCompat.getColor(requireActivity(),R.color.textPlaceHolder))

                }

            }


        }

    }

    private fun onRecentClick(pos: Int, type: String) {

    }

    private fun onStoreTypeClick(pos: Int, type: String) {

    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}