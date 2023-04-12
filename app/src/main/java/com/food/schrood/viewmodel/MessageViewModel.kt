package com.food.schrood.viewmodel

import android.content.ContentValues
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.food.schrood.model.ChatUserItem
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.StaticData.Companion.printLog
/*import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase*/
import java.text.SimpleDateFormat
import java.util.*

class MessageViewModel : ViewModel() {
 /*   private   var database: FirebaseDatabase
    private   var myChatListRef: DatabaseReference*/
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    init {

    /*    database= Firebase.database
        myChatListRef=database.getReference("chat_list")*/
        loading.postValue(false)
    }
    private val _myListLiveData = MutableLiveData<ArrayList<ChatUserItem>>()
    val chatUserList: LiveData<ArrayList<ChatUserItem>> = _myListLiveData
    fun getChatList(  user_id: String) {
     //   myChatListRef.child(user_id).addValueEventListener(chatListener)

    }
    val dateComparator = Comparator<ChatUserItem> { item1, item2 ->
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.ENGLISH)
        val date1 = dateFormat.parse(item1.last_chat_time)
        val date2 = dateFormat.parse(item2.last_chat_time)
        date2.compareTo(date1)
    }
  /*  private val chatListener =   object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            //   printLog("  chat List snapshot ",snapshot.toString())
            var  chatUerList=ArrayList<ChatUserItem>()
            if (snapshot!=null){
                for (postSnapshot in snapshot.children) {
                    val chatUserItem = postSnapshot.getValue(ChatUserItem::class.java)
                    chatUserItem?.let {
                        printLog("it.last_chat_time",it.last_chat_time)
                        if (TextUtils.isEmpty(it.last_chat_time)){
                            it.last_chat_time= StaticData.getCurrentTimestamp()
                        }
                        chatUerList.add(it)
                    }

                }

            }
           val  usUerList= chatUerList.sortedWith(dateComparator)
            _myListLiveData.value= ArrayList(usUerList)
        }

        override fun onCancelled(error: DatabaseError) {

            Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            _myListLiveData.value=ArrayList<ChatUserItem>()
        }
    }*/
    override fun onCleared() {
        super.onCleared()
        printLog("onCleared","")
    //    myChatListRef.removeEventListener(chatListener)


    }
}