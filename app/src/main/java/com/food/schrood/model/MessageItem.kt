package com.food.schrood.model

data class MessageItem(
    var id: String,
    val sender_id: String,
    val sender_name: String,
    var message: String,
    var image_url: String="",
    var type: String,
    val receiver_id: String,
    var thread_id: String,
    var chat_time: String
    ):SuperCastClass(),java.io.Serializable {

    constructor() : this("", "","","","", "", "", "", "") {
        // constructor code
    }
}