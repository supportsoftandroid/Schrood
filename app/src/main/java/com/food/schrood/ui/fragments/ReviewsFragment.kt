package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.DialogBottomAddRateStoreBinding
import com.food.schrood.databinding.FragmentReviewsBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.adapter.ReviewAdapter
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.NotificationsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class ReviewsFragment : Fragment() {

    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModal: NotificationsViewModel
    lateinit var adaper: ReviewAdapter
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

        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        preferenceManager = PreferenceManager(requireActivity())
        utilsManager = UtilsManager(requireActivity())
        initView()
        clickListener()

        return root
    }

    private fun clickListener() {
        binding.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()
        }
        binding.tvWriteReview.setOnClickListener() {
            showAddReviewBottomSheet()
        }


    }

    fun initView() {
        binding.tvTitle.text = requireActivity().getString(R.string.reviews)
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
            ReviewAdapter(
                requireActivity(),
                dataList,
                { pos, type -> onAdapterClick(pos, type) })
        binding.viewBody.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.adapter = adaper
        binding.viewBody.tvMessage.visibility = View.GONE

        binding.viewBody.swipeRefreshLayout.setOnRefreshListener {
            binding.viewBody.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun onAdapterClick(pos: Int, type: String) {

    }

    private fun showAddReviewBottomSheet() {
      val  dialog= BottomSheetDialog(requireActivity(), R.style.CustomBottomSheetStyle)
        val dialogBinding = DialogBottomAddRateStoreBinding.inflate(LayoutInflater.from(requireActivity()), null, false)
        val sheetView = dialogBinding.root
        dialog.setContentView(sheetView)
        dialog.setCancelable(false)


        dialog.show()
        dialogBinding.imgClose.setOnClickListener {
            // Delete code here;
            dialog.dismiss()
        }
        dialogBinding.btnSubmit.setOnClickListener {
            dialog.dismiss()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}