package com.food.schrood.model

import android.os.Parcel
import android.os.Parcelable

class ChatUserItem(
    val id: String,
    var name: String,
    var image: String,
    var thread_id: String,
    var type: String,
    var device_token: String,
    var device_type: String,
    var last_chat_time: String,
    var last_chat_message: String,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    constructor() : this("", "", "", "", "", "", "", "", "") {
        // constructor code
    }

    override fun describeContents(): Int {
        return 0
    }


    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest?.writeString(this.id)
        dest?.writeString(this.name)
        dest?.writeString(this.image)
        dest?.writeString(this.thread_id)
        dest?.writeString(this.type)
        dest?.writeString(this.device_token)
        dest?.writeString(this.last_chat_time)
        dest?.writeString(this.last_chat_message)
    }

    companion object CREATOR : Parcelable.Creator<ChatUserItem> {
        override fun createFromParcel(parcel: Parcel): ChatUserItem {
            return ChatUserItem(parcel)
        }

        override fun newArray(size: Int): Array<ChatUserItem?> {
            return arrayOfNulls(size)
        }
    }
}