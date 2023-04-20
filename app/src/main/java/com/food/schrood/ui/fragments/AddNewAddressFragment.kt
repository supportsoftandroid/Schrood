package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider


import com.food.schrood.databinding.FragmentAddAddressBinding

import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.adapter.NotificationAdapter
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.NotificationsViewModel

class AddNewAddressFragment : Fragment() {
    companion object {
        const val ADD_ADDRESS_REQUEST_KEY = "ADD_ADDRESS"
        fun newInstance(title: String): AddNewAddressFragment {
            val args = Bundle()
            args.putString("title", title)
            val fragment = AddNewAddressFragment()
            fragment.arguments = args
            return fragment
        }


    }

    private var _binding: FragmentAddAddressBinding? = null
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

        _binding = FragmentAddAddressBinding.inflate(inflater, container, false)
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
        binding.btnSubmit.setOnClickListener() {
            requireActivity().onBackPressed()
        }


    }

    fun initView() {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}