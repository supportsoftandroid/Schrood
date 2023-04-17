package com.food.schrood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.FragmentMessageBinding
import com.food.schrood.interfaces.CommonClickListener
import com.food.schrood.model.ChatUserItem
import com.food.schrood.model.LoginResponse
import com.food.schrood.ui.activities.MainActivity
import com.food.schrood.ui.adapter.ChatUserAdapter
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.StaticData.Companion.printLog
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.MessageViewModel


class MessageFragment : Fragment(), CommonClickListener {
    companion object {
        fun newInstance(from: String, title: String): MessageFragment {
            val args = Bundle()
            args.putString("from", from)
            args.putString("title", title)
            val fragment = MessageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(requireActivity())[MessageViewModel::class.java] }
    lateinit var adaper: ChatUserAdapter
    var dataList = mutableListOf<ChatUserItem>()

    lateinit var preferenceManager: PreferenceManager
    lateinit var utilsManager: UtilsManager
    lateinit var loginResponse: LoginResponse
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        StaticData.changeStatusBarColor(requireActivity(), "message")
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        preferenceManager = PreferenceManager(requireActivity())
        utilsManager = UtilsManager(requireActivity())
//        loginResponse = preferenceManager.getLoginData()!!

        initRecycleView()
        clickListener()
        initFirebaseDatabase()
        return root
    }

    private fun initFirebaseDatabase() {


    }

    private fun clickListener() {

        binding.viewHeader.imgMenu1.setOnClickListener() {
            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(requireActivity(), ProfileFragment())

        }
    }

    fun initRecycleView() {

        binding.viewHeader.txtTitle.text = requireActivity().getString(R.string.messages)
        dataList.clear()
        addItemInList("Ittalia Pizza Corner")
        addItemInList("Chicken Corner")
        addItemInList("Burger Center")
        addItemInList("Café Corner")
        addItemInList("Ittalia Pizza Corner")
        addItemInList("Chicken Corner")
        addItemInList("Burger Center")
        addItemInList("Café Corner")
        addItemInList("Ittalia Pizza Corner")
        addItemInList("Chicken Corner")
        addItemInList("Burger Center")
        addItemInList("Café Corner")









        adaper = ChatUserAdapter(requireActivity(), dataList, this)
        binding.viewBody.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.adapter = adaper
        binding.viewBody.tvMessage.visibility = View.GONE

        binding.viewBody.swipeRefreshLayout.setOnRefreshListener {
            binding.viewBody.tvMessage.text = requireActivity().getString(R.string.loading)
            binding.viewBody.tvMessage.visibility = View.VISIBLE

            binding.viewBody.swipeRefreshLayout.isRefreshing = false
            binding.viewBody.tvMessage.visibility = View.GONE
        }


        // intViewModal()

    }

    fun addItemInList(name: String) {
        var userItem = ChatUserItem()
        userItem.name = name
        dataList.add(userItem)
    }

    fun intViewModal() {
        binding.viewBody.tvMessage.visibility = View.VISIBLE
        viewModel.chatUserList.observe(viewLifecycleOwner) { it ->

            printLog("chat list final", it.size.toString())

            if (it.isEmpty()) {
                dataList.clear()
                binding.viewBody.tvMessage.text =
                    requireActivity().getString(R.string.no_chat_user_yet)
            } else {
                // dataList = dataList.sortBy { it.last_chat_time } as MutableList<ChatUserItem>
                dataList = it
                binding.viewBody.tvMessage.visibility = View.GONE
            }
            adaper.update(dataList)
        }
        viewModel.getChatList(loginResponse.data.id)

    }


    override fun onClicked(position: Int, type: String) {
        MainActivity.hideNavigationTab()
        val chatUserItem = dataList[position]
        StaticData.backStackAddFragment(
            requireActivity(),
            ChatHistoryFragment.newInstance(
                "user",
                chatUserItem.name,
                chatUserItem.id,
                chatUserItem
            )
        )

    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
    }
}