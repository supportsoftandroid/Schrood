package com.food.schrood.viewmodel


import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.food.schrood.model.ChatUserItem
import com.food.schrood.model.MessageItem
import com.food.schrood.utility.StaticData.Companion.printLog
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File


class ChatMessageViewModel : ViewModel() {
    private var database: FirebaseDatabase
    private var messageListRef: DatabaseReference
    private var myChatListRef: DatabaseReference
    val storage = Firebase.storage


    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private val _myMessageListLiveData = MutableLiveData<ArrayList<MessageItem>>()
    val messageList: LiveData<ArrayList<MessageItem>> = _myMessageListLiveData

    var friendItem: MutableLiveData<ChatUserItem?> = MutableLiveData()
    var chatUserItem: MutableLiveData<ChatUserItem?> = MutableLiveData()
    var singleMessageItem: MutableLiveData<MessageItem?> = MutableLiveData()

    init {

        database = Firebase.database
        messageListRef = database.getReference("message_list")
        myChatListRef = database.getReference("chat_list")
        loading.postValue(false)
    }


    fun getChatMessageList(thread_id: String) {
        //   messageListRef.child("/"+thread_id).addListenerForSingleValueEvent(allMessageListener)
    }

    /*private val allMessageListener =  object : ValueEventListener {
        override fun onDataChange(messageSnapshot : DataSnapshot) {
            printLog("messageSnapshot  all==",messageSnapshot .toString())
            val messageList = ArrayList<MessageItem>()
            if (messageSnapshot.exists()) {
                val messageData = messageSnapshot.children
                messageData.forEach { childSnapshot ->
                    val message = childSnapshot.getValue(MessageItem::class.java)
                    message?.let { messageList.add(it) } // Add the message to the list
                }

            }
            _myMessageListLiveData.value=messageList

        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            _myMessageListLiveData.value=   ArrayList<MessageItem>()
        }
    }
*/
    fun getSingleMessageItem(thread_id: String) {
        // messageListRef.child(thread_id).addChildEventListener(singleMessageListener)

    }
    /* private val singleMessageListener =  object :  ChildEventListener {
         override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
             printLog("onChildAdded==",snapshot.toString())
             printLog("previousChildName==",previousChildName.toString())
             if (snapshot.exists()&&previousChildName!=null) {
                 val message = snapshot.getValue(MessageItem::class.java)
                 printLog("onChild =="," new Added message=="+message?.message)
                 singleMessageItem.value=message
             }
         }

         override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
             printLog("onChildChanged==",snapshot.toString())
             if (snapshot.exists()) {
                 val message = snapshot.getValue(MessageItem::class.java)
                 printLog("onChildChange==","message=="+message?.message)
                 singleMessageItem.value=message
             }

         }

         override fun onChildRemoved(snapshot: DataSnapshot) {
             printLog("onChildRemoved==",snapshot.toString())
         }

         override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
             printLog("onChildMoved==",snapshot.toString())
         }

         override fun onCancelled(error: DatabaseError) {


         }
     }
 */

    fun addMessage(context: Context, deviceToken: String, name: String, messageItem: MessageItem) {


        /* val addMsgRefKey=  messageListRef.child(messageItem.thread_id).push().key.toString()
            messageItem.id=addMsgRefKey
           messageListRef.child(messageItem.thread_id).child(addMsgRefKey).setValue(messageItem).addOnCompleteListener { task ->
               sendNotification(context,deviceToken,name,messageItem)

              *//* if (task.isSuccessful) {
                messageItem.id= messageListRef.key.toString()
                val updatedData = HashMap<String, Any>()
                updatedData["id"] = messageItem.id

// Update the item in the database
                messageListRef.child(messageItem.thread_id).child(addMsgRefKey).updateChildren(updatedData)
                    .addOnSuccessListener {
                        // The update was successful
                        sendNotification(context,deviceToken,name,messageItem)
                    }
                    .addOnFailureListener {
                        // There was an error updating the item
                    }
            } else {
                // handle the error
            }*//*
        }*/
    }


    fun getFriendChatItem(userId: String, friend_id: String) {
        //  myChatListRef.child(userId).child(friend_id).addValueEventListener(friendUserItemListener)

    }

    fun addFileMessage(
        context: Context,
        deviceToken: String,
        name: String,
        messageItem: MessageItem,
        file: File
    ) {
        /*  val storageRef = storage.reference.child("file").child(messageItem.thread_id).child(file.name.toLowerCase(Locale.ENGLISH))

         storageRef.putFile(file.toUri()).addOnSuccessListener {  taskSnapshot ->
             storageRef.downloadUrl.addOnSuccessListener {uri->
              //   printLog("image", " uploaded file uri=="+ uri.toString())
               //  printLog("url", " uploaded file url=="+ uri.path.toString())
                   messageItem.image_url=   uri.toString()
                 addMessage(context ,deviceToken ,name, messageItem )
             }

         }.addOnFailureListener { exception ->
             // Handle unsuccessful upload
             Log.e("TAG", "Image upload failed", exception)
           showToast(context, exception.message.toString() )
         }
  */


    }


    private val friendUserItemListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val userItem = snapshot.getValue(ChatUserItem::class.java)
                friendItem.value = userItem
            }
        }

        override fun onCancelled(error: DatabaseError) {

            Log.w(ContentValues.TAG, "Failed to read value.", error.toException())

        }
    }
    private val chatUserItemListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val userItem = snapshot.getValue(ChatUserItem::class.java)
                chatUserItem.value = userItem
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(ContentValues.TAG, "Failed to read value.", error.toException())

        }

    }

    fun getMyChatItem(friend_id: String, userId: String) {
        myChatListRef.child(friend_id).child(userId).addValueEventListener(chatUserItemListener)

    }

    fun updateChatList(friend_id: String, userId: String, chatUserItem: ChatUserItem) {
        myChatListRef.child(friend_id).child(userId).setValue(chatUserItem)

    }

    override fun onCleared() {
        super.onCleared()
        printLog("onCleared", "")
        /* myChatListRef.removeEventListener(chatUserItemListener)
         myChatListRef.removeEventListener(friendUserItemListener)
         messageListRef.removeEventListener(allMessageListener)*/
        //messageListRef.removeEventListener(singleMessageListener)
    }

    /*   @SuppressLint("SuspiciousIndentation")
       fun sendNotification(context: Context, deviceToken:String, name:String, messageItem: MessageItem) {
   //        val url = "https://fcm.googleapis.com/v1/projects/YOUR_PROJECT_ID/messages:send"
           val data=Data(name,messageItem.message,"chat",messageItem)
           val notification=Notification(name,messageItem.message,"chat",messageItem)

           val sender=Sender(data,notification,deviceToken)

         val  payload=Gson().toJson(sender)
           printLog("payload",payload.toString())
           val retrofit = Retrofit.Builder()
               .baseUrl("https://fcm.googleapis.com/")
               .client(OkHttpClient())
               .addConverterFactory(GsonConverterFactory.create())
               .build()
           val fcmApiService: ApiInterface by lazy {
               retrofit.create(ApiInterface::class.java)
           }
           val call =   fcmApiService.sendNotification(SERVER_KEY,sender)
           call.enqueue(object : Callback<ResponseBody> {
               override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {


                   printLog("notifi  code==",response.code().toString())

                   printLog("notif  message==",response.message())
                   printLog("notifi     response==",   response.toString())
               }
               override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                   printLog("notification error==",t.message.toString())

                   StaticData.showToast(context, t.message.toString())
               } })





       }

   */
}
