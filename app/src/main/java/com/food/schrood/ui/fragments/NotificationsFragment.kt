package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.DialogBottomAllowNotificationsBinding
import com.food.schrood.databinding.FragmentNotificationsBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.adapter.NotificationAdapter
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.NotificationsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModal: NotificationsViewModel
    lateinit var adaper: NotificationAdapter
    var dataList = mutableListOf<CommonDataItem>()
    lateinit var preferenceManager: PreferenceManager
    lateinit var utilsManager: UtilsManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModal =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                NotificationsViewModel::class.java
            )

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        preferenceManager = PreferenceManager(requireActivity())
        utilsManager = UtilsManager(requireActivity())
        initView()
        clickListener()

        return root
    }

    private fun clickListener() {
        binding.viewHeader.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()
        }

        binding.viewHeader.btnCheckSwitch.visibility = View.VISIBLE

    }

    fun initView() {
        binding.viewHeader.txtTitle.text = requireActivity().getString(R.string.notifications)
        dataList.clear()

        dataList.add(CommonDataItem("JCO Restaurant", "", false))
        dataList.add(CommonDataItem("Starbuck Ambarukmo Plaza", "", false))
        dataList.add(CommonDataItem("Dunkin Donuts Ambarukmo P..", "", false))

        dataList.add(CommonDataItem("JCO Restaurant", "", false))
        dataList.add(CommonDataItem("Starbuck Ambarukmo Plaza", "", false))
        dataList.add(CommonDataItem("Dunkin Donuts Ambarukmo P..", "", false))

        dataList.add(CommonDataItem("JCO Restaurant", "", false))
        dataList.add(CommonDataItem("Starbuck Ambarukmo Plaza", "", false))
        dataList.add(CommonDataItem("Dunkin Donuts Ambarukmo P..", "", false))


        binding.viewBody.tvMessage.visibility = View.VISIBLE
        adaper =
            NotificationAdapter(
                requireActivity(),
                dataList,
                { pos, type -> onAdapterClick(pos, type) })
        binding.viewBody.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.adapter = adaper
        binding.viewBody.tvMessage.visibility = View.GONE

        binding.viewBody.swipeRefreshLayout.setOnRefreshListener {
            binding.viewBody.swipeRefreshLayout.isRefreshing = false
        }
        binding.viewHeader.btnCheckSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            // do something when the checkbox state changes
            if (isChecked) {
                showAllowNotificationBottomSheet()
                // checkbox is checked
            } else {
                // checkbox is unchecked
            }
        }
    }
    private fun showAllowNotificationBottomSheet() {
        val dialog = BottomSheetDialog(requireActivity(), R.style.GalleryDialog)
        val dialogBinding = DialogBottomAllowNotificationsBinding.inflate(
            LayoutInflater.from(requireActivity()),
            null,
            false
        )
        val sheetView = dialogBinding.root
        dialog.setContentView(sheetView)
        dialog.setCancelable(false)
        dialog.show()
        dialogBinding.imgBack.setOnClickListener {
            // Delete code here;
            dialog.dismiss()
        }
        dialogBinding.btnAllow.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.tvCancel.setOnClickListener {
            binding.viewHeader.btnCheckSwitch.isChecked=false
            dialog.dismiss()
        }

    }
    private fun onAdapterClick(pos: Int, type: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}