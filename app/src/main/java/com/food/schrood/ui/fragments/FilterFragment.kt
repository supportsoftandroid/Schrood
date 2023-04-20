package com.food.schrood.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.FragmentFilterBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.adapter.CategoryFilterAdapter
import com.food.schrood.ui.adapter.FoodTypeFilterAdapter
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.FilterViewModel


class FilterFragment : Fragment() {
    companion object {
        const val Filter_REQUEST_KEY = "filter"
        fun newInstance(title: String): FilterFragment {
            val args = Bundle()
            args.putString("title", title)
            val fragment = FilterFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(context: Context, title: String) {
            val args = Bundle()
            args.putString("title", title)
            val fragment = FilterFragment()
            fragment.arguments = args

            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.add(R.id.frame, fragment!!, fragment.tag)
                .addToBackStack(fragment.tag)
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    private var _binding: FragmentFilterBinding? = null
    lateinit var viewModal: FilterViewModel
    private val binding get() = _binding!!
    lateinit var adaper: CategoryFilterAdapter
    lateinit var adapterFoodType: FoodTypeFilterAdapter
    var categoryList = mutableListOf<CommonDataItem>()
    var foodTypeList = mutableListOf<CommonDataItem>()
    lateinit var preferenceManager: PreferenceManager
    lateinit var utilsManager: UtilsManager

    // lateinit var loginResponse: LoginResponse
    private var price_range = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModal = ViewModelProvider(this).get(FilterViewModel::class.java)

        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        preferenceManager = PreferenceManager(requireActivity())
        utilsManager = UtilsManager(requireActivity())
        val root: View = binding.root
        //  loginResponse = preferenceManager.getLoginData()!!
        initView()
        clickListener()
        return root
    }

    private fun clickListener() {
        binding.viewHeader.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()
        }
        binding.btnApply.setOnClickListener() {

            requireActivity().onBackPressed()
            /* val jsonObject = JsonObject()
            jsonObject.addProperty("max_distance", max_distance)
            jsonObject.addProperty("price_range", price_range)
            jsonObject.addProperty("subject_id", subjectIds)
            jsonObject.addProperty("subject_name", subjectName)
            jsonObject.addProperty("branch", branch_id)
            jsonObject.addProperty("gender", gender)
            jsonObject.addProperty("contact_type", contact_type)
                printLog("filter",jsonObject.toString())
                val bundle=Bundle()
                bundle.putString("from","filter")
                bundle.putString("data",jsonObject.toString())
                if (utilsManager.isNetworkConnected()) {
                    parentFragmentManager.setFragmentResult(Filter_REQUEST_KEY, bundle)
                    requireActivity().onBackPressed()
                }*/
        }


    }

    fun initView() {
        binding.viewHeader.txtTitle.text = requireActivity().getString(R.string.filter_your_search)
        categoryList.clear()
        categoryList.add(CommonDataItem("All", "all", true))
        categoryList.add(CommonDataItem("Breakfast", "breakfast", false))
        categoryList.add(CommonDataItem("Lunch", "Lunch", false))
        categoryList.add(CommonDataItem("Dinner", "Dinner", false))
        categoryList.add(CommonDataItem("Pasta", "Pasta", false))
        categoryList.add(CommonDataItem("Burgers", "Burgers", false))
        categoryList.add(CommonDataItem("Chinese", "Chinese", false))
        categoryList.add(CommonDataItem("Pizza", "Pizza", false))
        categoryList.add(CommonDataItem("Salads", "Salads", false))
        categoryList.add(CommonDataItem("Soups", "Soups", false))
        adaper = CategoryFilterAdapter(requireActivity(), categoryList) { pos, type ->
            onCategoryClick(
                pos, type,
            )
        }
        binding.rvCategory.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.rvCategory.adapter = adaper

        foodTypeList.clear()
        foodTypeList.add(CommonDataItem("Any", "Any", false))
        foodTypeList.add(CommonDataItem("Vegetarian", "Vegetarian", true))
        foodTypeList.add(CommonDataItem("Non Veg", "Non Veg", false))
        foodTypeList.add(CommonDataItem("Vegan", "Vegan", false))


        adapterFoodType = FoodTypeFilterAdapter(
            requireActivity(),
            foodTypeList,
            { pos, type -> onFoodClick(pos, type) })
        binding.rvFoodType.layoutManager = GridLayoutManager(requireActivity(), 4)
        binding.rvFoodType.adapter = adapterFoodType

        binding.seekPrice.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                price_range = progress
                binding.tvMaxPrice.setText(requireActivity().getString(R.string.currency_symbol) + price_range.toString())

            }
        })

        binding.seekPrice.setProgress(5)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun onCategoryClick(position: Int, type: String) {

    }

    fun onFoodClick(position: Int, type: String) {

    }

}