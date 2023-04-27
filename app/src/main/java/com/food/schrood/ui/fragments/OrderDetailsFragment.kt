package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.DialogBottomAddRateProductBinding
import com.food.schrood.databinding.FragmentOrderDetailsBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.activities.MainActivity
import com.food.schrood.ui.adapter.OrderProductItemAdapter
import com.food.schrood.utility.StaticData
import com.food.schrood.viewmodel.MyOrderViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class OrderDetailsFragment : Fragment() {

    private var _binding: FragmentOrderDetailsBinding? = null
    lateinit var viewModal: MyOrderViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var orderOngoingList = mutableListOf<CommonDataItem>()
    var orderCompletedList = mutableListOf<CommonDataItem>()
    var dataList = mutableListOf<CommonDataItem>()

    lateinit var adapter: OrderProductItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        viewModal = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(MyOrderViewModel::class.java)
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.let {
            initView()
            clickListener()
        }

        return root
    }

    fun initView() {

        binding.viewHeader.txtTitle.text =  "Order ID: SC00001"
        dataList.clear()
        dataList.add(CommonDataItem("Creamy Burger", "", false))
        dataList.add(CommonDataItem("Mountain Dew", "", false))
        dataList.add(CommonDataItem("Appetizers", "", false))

        adapter = OrderProductItemAdapter(requireActivity(), dataList) { pos, type -> onOrderClick(pos, type) }
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter = adapter


    }

    private fun clickListener() {

        binding.viewHeader.imgBack.setOnClickListener() {
           requireActivity().onBackPressed()

        }
        binding.tvRate.setOnClickListener() {
            dialogRateProduct()

        }
        binding.tvReOrder.setOnClickListener() {
           StaticData.backStackAddFragment(requireActivity(),CartFragment())

        }
        binding.viewHeader.imgMenu1.setOnClickListener() {
            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(requireActivity(), ProfileFragment())

        }
    }

    private fun onOrderClick(position: Int, type: String) {


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