package com.food.schrood.ui.fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.food.schrood.R
import com.food.schrood.databinding.FragmentChatHistoryBinding
import com.food.schrood.model.ChatUserItem
import com.food.schrood.model.LoginResponse
import com.food.schrood.model.MessageItem
import com.food.schrood.model.UserDetails
import com.food.schrood.ui.adapter.ChatMessageAdapter
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.StaticData.Companion.KEY_IS_CHAT
import com.food.schrood.utility.StaticData.Companion.getCurrentTimestamp
import com.food.schrood.utility.StaticData.Companion.printLog
import com.food.schrood.utility.StaticData.Companion.showToast
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.ChatMessageViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File


class ChatHistoryFragment : Fragment() {
    companion object {
        fun newInstance(
            from: String,
            title: String,
            friend_id: String,
            friendItem: ChatUserItem
        ): ChatHistoryFragment {
            val args = Bundle()
            args.putString("from", from)
            args.putString("title", title)
            args.putString("friend_id", friend_id)
            args.putParcelable("friend_item", friendItem)
            val fragment = ChatHistoryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentChatHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(requireActivity())[ChatMessageViewModel::class.java] }
    lateinit var adaper: ChatMessageAdapter
    var dataList = ArrayList<MessageItem>()
    lateinit var preferenceManager: PreferenceManager
    lateinit var utilsManager: UtilsManager
    lateinit var loginResponse: LoginResponse
    lateinit var userDetails: UserDetails
    lateinit var userChatItem: ChatUserItem
    lateinit var friendItem: ChatUserItem
    lateinit var messageItem: MessageItem
    var threadId: String = ""
    var firebase_token: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChatHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        preferenceManager = PreferenceManager(requireActivity())
        utilsManager = UtilsManager(requireActivity())
        //  loginResponse = preferenceManager.getLoginData()!!
        //  userDetails = loginResponse.data
        //  friendItem = arguments?.getParcelable<ChatUserItem>("friend_item") as ChatUserItem
        //  threadId = friendItem.thread_id
        viewModelStore.clear()

        initRecycleView()
        clickListener()
        /* if (!StaticData.hasPermissions(requireActivity(), *StaticData.PERMISSIONSList)) {
             ActivityCompat.requestPermissions( requireActivity(),StaticData.PERMISSIONSList,StaticData.PERMISSION_ALL
             )
         }*/

        return root
    }


    fun initRecycleView() {

        binding.viewHeader.txtTitle.text = arguments?.getString("title")
        binding.viewHeader.txtTitle.gravity = Gravity.START
        dataList.clear()
        adaper = ChatMessageAdapter(requireActivity(), "1", dataList)
        binding.viewBody.rvList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.viewBody.rvList.adapter = adaper
        // binding.viewBody.tvMessage.visibility = View.GONE

        binding.viewBody.swipeRefreshLayout.setOnRefreshListener {
            binding.viewBody.swipeRefreshLayout.isRefreshing = false
        }

        //  binding.viewBody.tvMessage.text = requireActivity().getString(R.string.lets_start_chat)
        notifyMessageList()
        /* val lastLame = if (TextUtils.isEmpty(userDetails.l_name)) "" else " " + userDetails.l_name
         val userType = if (friendItem.type.equals("tutor")) "student" else "tutor"

         userChatItem = ChatUserItem(
             userDetails.id,
             userDetails.name + lastLame,
             userDetails.image.toString(),
             threadId,
             userType,
             firebase_token,
             Constants.DEVICE_TYPE,
             "",
             ""
         )
         //update my Chat list
         viewModel.updateChatList(userChatItem.id, friendItem.id, friendItem)
       initChatMessageItem()
         getChatMessageList()
         getFriendChatItem()
         getUserChatItem()
         getSingleMessageItem()*/

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    printLog("TAG", "Fragment back pressed invoked")
                    // Do custom work here
                    // if you want onBackPressed() to be called as normal afterwards
                    // isEnabled = false
                    viewModelStore.clear()
                    requireActivity().onBackPressed()

                }
            }
        )

    }

    private fun initChatMessageItem() {
        messageItem = MessageItem(
            "", "1", "", "",
            "", "", "2", threadId, getCurrentTimestamp()
        )
        /* messageItem = MessageItem(
             "", userChatItem.id, userChatItem.name, "",
             "", "", friendItem.id, threadId, getCurrentTimestamp()
         )*/
    }

    fun getChatMessageList() {
        viewModel.messageList.observe(viewLifecycleOwner) {
            dataList = it
            printLog("messageList", "==final===" + dataList.size.toString())

            notifyMessageList()
        }


        viewModel.getChatMessageList(threadId)

    }

    fun getSingleMessageItem() {
        viewModel.singleMessageItem.observe(viewLifecycleOwner) {
            if (it != null) {
                printLog("update ==", "=single message==" + it?.message)
                dataList.add(it)
                notifyMessageList()

            }
        }

        viewModel.getSingleMessageItem(threadId)
    }

    fun getFriendChatItem() {
        viewModel.friendItem.observe(viewLifecycleOwner) {
            if (it != null) {
                friendItem = it
            }
        }
        viewModel.getFriendChatItem(userDetails.id, friendItem.id)
    }

    fun getUserChatItem() {
        viewModel.chatUserItem.observe(viewLifecycleOwner) {
            if (it != null) {
                userChatItem = it
            }
        }
        viewModel.getMyChatItem(friendItem.id, userDetails.id)

    }


    private fun notifyMessageList() {
        if (!dataList.isEmpty()) {
            binding.viewBody.tvMessage.visibility = View.GONE
            binding.viewBody.rvList.scrollToPosition(dataList.size - 1)
        } else {
            binding.viewBody.tvMessage.visibility = View.VISIBLE
            binding.viewBody.tvMessage.text = requireActivity().getString(R.string.lets_start_chat)
        }
        adaper.update(dataList)

    }

    private fun clickListener() {
        binding.viewHeader.imgBack.setOnClickListener() {
            requireActivity().onBackPressed()
        }
        binding.viewHeader.imgMenu1.visibility=View.VISIBLE
        binding.viewHeader.imgMenu1.setImageResource(R.drawable.ic_call)
        binding.viewHeader.imgMenu1.setOnClickListener() {
            requireActivity().onBackPressed()
        }
        binding.imgSendMsg.setOnClickListener() {
            if (binding.etReply.text.toString().trim().length == 0) {
                showToast(
                    requireActivity(),
                    requireActivity().getString(R.string.please_enter_message)
                )
            } else {
                initChatMessageItem()
                messageItem.message = binding.etReply.text.toString()
                messageItem.type = "text"
                binding.etReply.text = null

                dataList.add(messageItem)
                notifyMessageList()

                // addMessage(messageItem)

            }
        }
        /*     binding.etReply.setOnTouchListener(View.OnTouchListener { v, event ->
                 val DRAWABLE_LEFT = 0
                 val DRAWABLE_TOP = 1
                 val DRAWABLE_RIGHT = 2
                 val DRAWABLE_BOTTOM = 3
                 if (event.action == MotionEvent.ACTION_UP) {
                     if (event.rawX >= binding.etReply.getRight() - binding.etReply.getCompoundDrawables()
                             .get(DRAWABLE_RIGHT).getBounds().width()
                     ) {
                         binding.etReply.clearFocus()
                         utilsManager.showGallaryBottomModelSheet(requireActivity())

                         return@OnTouchListener true
                     }
                 }
                 false
             })*/
    }

    private fun addMessage(messageItem: MessageItem) {
        messageItem.id = ""
        messageItem.thread_id = threadId
        messageItem.chat_time = getCurrentTimestamp()
        /* dataList.add(messageItem)
         notifyMessageList()*/
        viewModel.addMessage(
            requireActivity(),
            friendItem.device_token,
            userChatItem.name,
            messageItem
        )
        updateChatList(messageItem)


    }

    private fun updateChatList(messageItem: MessageItem) {
        friendItem.last_chat_time = messageItem.chat_time
        friendItem.last_chat_message = messageItem.message
        userChatItem.device_token = firebase_token
        userChatItem.last_chat_time = messageItem.chat_time
        userChatItem.last_chat_message = messageItem.message
        //update Friend Chat List
        viewModel.updateChatList(friendItem.id, userChatItem.id, userChatItem)
        //update my Chat list
        viewModel.updateChatList(userChatItem.id, friendItem.id, friendItem)
    }


    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        override fun onReceive(context: Context, intent: Intent) {
            intent.getStringExtra("type")
                .let { printLog("Chat History Board cast from===", it.toString()) }
            printLog("Chat History Board cast type===", intent?.getStringExtra("type").toString())
            if (intent.getStringExtra("type").equals("chat")) {
                val chatDetails: MessageItem = intent.getSerializableExtra("data") as MessageItem
                printLog(
                    "current message threadId===" + threadId + "   firebase threadId==",
                    chatDetails.thread_id
                )
                if (chatDetails.thread_id.contentEquals(threadId)) {
                    printLog("current message  ===", chatDetails.toString())
                    // dataList.add(chatDetails)
                    //  notifyMessageList()
                }

            }

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StaticData.IMAGE_CROP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val imagefileUri = data?.data!!
                if (imagefileUri != null) {
                    initChatMessageItem()
                    messageItem.type = "image"
                    messageItem.message = "file"
                    messageItem.image_url = imagefileUri?.path.toString()
                    messageItem.id = ""
                    messageItem.thread_id = threadId
                    messageItem.chat_time = getCurrentTimestamp()
                    /* dataList.add(messageItem)
                     notifyMessageList()*/
                    viewModel.addFileMessage(
                        requireActivity(), friendItem.device_token, userChatItem.name, messageItem,
                        File(messageItem.image_url)
                    )
                    updateChatList(messageItem)
                }

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.saveBoolean(KEY_IS_CHAT, true)
        val intentFilter = IntentFilter(StaticData.FCM_BROADCAST)
        LocalBroadcastManager.getInstance(requireActivity())
            .registerReceiver(broadcastReceiver, intentFilter)

    }

    override fun onStop() {
        preferenceManager.saveBoolean(KEY_IS_CHAT, false)
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(broadcastReceiver)
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        _binding = null
    }
}