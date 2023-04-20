package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.FragmentAddressListBinding
import com.food.schrood.model.CommonDataItem
import com.food.schrood.ui.adapter.AddressItemAdapter
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.NotificationsViewModel

class AddressListFragment : Fragment() {
    companion object {
        const val ADDRESS_LIST_REQUEST_KEY = "address_list"
        fun newInstance(title: String): AddressListFragment {
            val args = Bundle()
            args.putString("title", title)
            val fragment = AddressListFragment()
            fragment.arguments = args
            return fragment
        }


    }

    private var _binding: FragmentAddressListBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModal: NotificationsViewModel
    lateinit var adaper: AddressItemAdapter
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

        _binding = FragmentAddressListBinding.inflate(inflater, container, false)
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

        binding.clvAddCard.setOnClickListener() {
            StaticData.backStackAddFragment(
                requireActivity(),
                AddNewAddressFragment.newInstance("")
            )

        }

    }

    fun initView() {
        binding.viewHeader.txtTitle.text = requireActivity().getString(R.string.saved_address)
        dataList.clear()

        dataList.add(CommonDataItem("Michael Smith", "", false))
        dataList.add(CommonDataItem("Sarah Smith", "", false))
        dataList.add(CommonDataItem("Angela Smith", "", false))
        dataList.add(CommonDataItem("Michael Smith", "", false))
        dataList.add(CommonDataItem("Sarah Smith", "", false))
        dataList.add(CommonDataItem("Angela Smith", "", false))
        dataList.add(CommonDataItem("Michael Smith", "", false))
        dataList.add(CommonDataItem("Sarah Smith", "", false))
        dataList.add(CommonDataItem("Angela Smith", "", false))



        binding.viewBody.tvMessage.visibility = View.VISIBLE
        adaper =
            AddressItemAdapter(
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
        requireActivity().onBackPressed()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}