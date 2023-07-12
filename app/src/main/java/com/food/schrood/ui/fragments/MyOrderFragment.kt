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
import com.food.schrood.databinding.DialogBottomAddRateProductBinding
import com.food.schrood.databinding.FragmentMyOrdersBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.activities.MainActivity
import com.food.schrood.ui.adapter.OrderItemAdapter
import com.food.schrood.utility.StaticData
import com.food.schrood.viewmodel.MyOrderViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class MyOrderFragment : Fragment() {

    private var _binding: FragmentMyOrdersBinding? = null
    lateinit var viewModal: MyOrderViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var orderOngoingList = mutableListOf<CommonDataItem>()
    var orderCompletedList = mutableListOf<CommonDataItem>()
    var dataList = mutableListOf<CommonDataItem>()

    lateinit var adapter: OrderItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        StaticData.changeStatusBarColor(requireActivity(), "other")
        viewModal = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(MyOrderViewModel::class.java)
        _binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.let {
            initView()
            clickListener()
        }

        return root
    }

    fun initView() {

        binding.viewHeader.txtTitle.text = requireActivity().getString(R.string.my_orders)
        orderOngoingList.clear()
        orderOngoingList.add(CommonDataItem("Pizza Italia Restaurant & Cafe", "Any", false))
        orderOngoingList.add(CommonDataItem("Sydney  Restaurant & Cafe", "Vegetarian", true))
        orderOngoingList.add(CommonDataItem("Rock Italia Restaurant & Cafe", "Non Veg", false))
        orderOngoingList.add(CommonDataItem("Johan Italia Restaurant & Cafe", "Vegan", false))


        orderOngoingList.add(CommonDataItem("Pizza Italia Restaurant & Cafe", "Any", false))
        orderOngoingList.add(CommonDataItem("Sydney  Restaurant & Cafe", "Vegetarian", true))
        orderOngoingList.add(CommonDataItem("Rock Italia Restaurant & Cafe", "Non Veg", false))
        orderOngoingList.add(CommonDataItem("Johan Italia Restaurant & Cafe", "Vegan", false))


        orderOngoingList.add(CommonDataItem("Pizza Italia Restaurant & Cafe", "Any", false))
        orderOngoingList.add(CommonDataItem("Sydney  Restaurant & Cafe", "Vegetarian", true))
        orderOngoingList.add(CommonDataItem("Rock Italia Restaurant & Cafe", "Non Veg", false))
        orderOngoingList.add(CommonDataItem("Johan Italia Restaurant & Cafe", "Vegan", false))



        orderCompletedList.clear()
        orderCompletedList.add(CommonDataItem("Sydney  Restaurant & Cafe", "Vegetarian", true))
        orderCompletedList.add(CommonDataItem("Rock Italia Restaurant & Cafe", "Non Veg", false))
        orderCompletedList.add(CommonDataItem("Pizza Italia Restaurant & Cafe", "Any", false))
        orderCompletedList.add(CommonDataItem("Johan Italia Restaurant & Cafe", "Vegan", false))
        orderCompletedList.add(CommonDataItem("Sydney  Restaurant & Cafe", "Vegetarian", true))
        orderCompletedList.add(CommonDataItem("Rock Italia Restaurant & Cafe", "Non Veg", false))
        orderCompletedList.add(CommonDataItem("Pizza Italia Restaurant & Cafe", "Any", false))
        orderCompletedList.add(CommonDataItem("Johan Italia Restaurant & Cafe", "Vegan", false))
        orderCompletedList.add(CommonDataItem("Sydney  Restaurant & Cafe", "Vegetarian", true))
        orderCompletedList.add(CommonDataItem("Rock Italia Restaurant & Cafe", "Non Veg", false))
        orderCompletedList.add(CommonDataItem("Pizza Italia Restaurant & Cafe", "Any", false))
        orderCompletedList.add(CommonDataItem("Johan Italia Restaurant & Cafe", "Vegan", false))

        dataList.clear()
        dataList = orderOngoingList

        adapter =
            OrderItemAdapter(requireActivity(), dataList) { pos, type -> onOrderClick(pos, type) }
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter = adapter
        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbOngoing -> {
                    binding.rbOngoing.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(), R.color.app_color
                        )
                    )
                    binding.rbHistory.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(), R.color.colorText
                        )
                    )
                    dataList.clear()
                    dataList.addAll(orderOngoingList)
                    adapter.notifyDataSetChanged()
                }
                R.id.rbHistory -> {
                    binding.rbHistory.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(), R.color.app_color
                        )
                    )
                    binding.rbOngoing.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(), R.color.textPlaceHolder
                        )
                    )
                    dataList.clear()
                    dataList.addAll(orderCompletedList)
                    adapter.notifyDataSetChanged()
                }

            }


        }

    }

    private fun clickListener() {

        binding.viewHeader.imgMenu1.setOnClickListener() {
            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(requireActivity(), ProfileFragment())

        }
    }

    private fun onOrderClick(position: Int, type: String) {
        if (type.equals("rate")) {
            dialogRateProduct()
        } else if (type.equals("view")) {
            MainActivity.hideNavigationTab()

            StaticData.backStackAddFragment(requireActivity(), OrderDetailsFragment())
        } else if (type.equals("reorder")) {
            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(requireActivity(), CartFragment())
        }

    }

    fun dialogRateProduct() {
        val dialogBinding = DialogBottomAddRateProductBinding.inflate(
            LayoutInflater.from(requireActivity()), null, false
        )
        val dialogRate = BottomSheetDialog(requireActivity(), R.style.GalleryDialog)
        dialogRate.setContentView(dialogBinding.root)

        dialogBinding.imgClose.setOnClickListener {
            dialogRate.dismiss()
        }
        dialogBinding.btnSubmit.setOnClickListener {

            dialogRate.dismiss()


        }
        dialogRate.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}