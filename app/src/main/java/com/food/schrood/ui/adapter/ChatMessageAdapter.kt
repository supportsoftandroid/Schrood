package com.food.schrood.ui.adapter


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.food.schrood.R
import com.food.schrood.databinding.DialogViewChatItemBinding
import com.food.schrood.databinding.ListChatFriendMessageItemBinding
import com.food.schrood.databinding.ListChatMyMessageItemBinding
import com.food.schrood.model.MessageItem
import com.food.schrood.utility.StaticData.Companion.printLog


class ChatMessageAdapter(mContext: Context, userId: String, dataList: MutableList<MessageItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = mutableListOf<MessageItem>()

    private val LAYOUT_SENDER = 0
    private val LAYOUT_RECIPIENT = 1
    var mContext: Context
    var userId: String


    init {
        this.dataList = dataList
        this.mContext = mContext
        this.userId = userId

    }

    fun update(newdataList: MutableList<MessageItem>) {
        if (newdataList != null) {
            dataList = newdataList
            notifyDataSetChanged()
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            LAYOUT_SENDER -> {
                return MyViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.list_chat_my_message_item,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return FriendViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.list_chat_friend_message_item,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            getItemViewType(position) == LAYOUT_SENDER -> {
                (holder as MyViewHolder).bind(dataList[position])
            }
            getItemViewType(position) == LAYOUT_RECIPIENT -> {
                (holder as FriendViewHolder).bind(dataList[position])
            }

        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        dataList[position].also { message ->
            return when {
                message.sender_id == userId -> {
                    LAYOUT_SENDER
                }
                else -> LAYOUT_RECIPIENT
            }
        }

    }

    inner class MyViewHolder(private val item: ListChatMyMessageItemBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(message: MessageItem) {
            item.tvChatTime.text = message.chat_time
            item.tvMessage.text = message.message
            if (message.type.equals("text")) {
                item.tvMessage.visibility = View.VISIBLE
                item.cardImage.visibility = View.GONE
            } else {
                item.tvMessage.visibility = View.GONE
                item.cardImage.visibility = View.VISIBLE
                loadFile(message, item.imageView)
                item.imageView.setOnClickListener {

                    viewDocumentImage(message)

                }
            }
        }
    }

    inner class FriendViewHolder(private val item: ListChatFriendMessageItemBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(message: MessageItem) {
            item.tvMessage.text = message.message
            item.tvChatTime.text = message.chat_time.toString()
            if (message.type.equals("text")) {
                item.tvMessage.visibility = View.VISIBLE
                item.tvMessage.text = message.message
                item.cardImage.visibility = View.GONE
            } else {
                item.tvMessage.visibility = View.GONE
                item.cardImage.visibility = View.VISIBLE
                loadFile(message, item.imageView)
                item.imageView.setOnClickListener {

                    viewDocumentImage(message)

                }
            }
        }
    }

    fun loadFile(message: MessageItem, imageView: ImageView) {
        //printLog("message.file_format",message.file_format)
        Glide.with(mContext)
            .load(message.image_url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_logo)

            ).into(imageView)
/*        when(message.file_format){
            "image"->{
                Glide.with(mContext)
                    .load(fileUrl)
                    .apply(
                        RequestOptions()
                            .placeholder( R.drawable.ic_loading)
                            .error(R.drawable.ic_logo)

                    ).into(imageView)
            }
            "video"->{
                Glide.with(mContext)
                    .load("")
                    .apply(
                        RequestOptions()
                            .placeholder( R.drawable.ic_loading)
                            .error(R.drawable.ic_video)
                    ).into(imageView)

            }
            "audio","mpeg"->{
                Glide.with(mContext)
                    .load("")
                    .apply(
                        RequestOptions()
                            .placeholder( R.drawable.ic_loading)
                            .error(R.drawable.ic_audio)

                    ).into(imageView)

            }
            "pdf"->{
                Glide.with(mContext)
                    .load("")
                    .apply(
                        RequestOptions()
                            .placeholder( R.drawable.ic_loading)
                            .error(R.drawable.ic_pdf)

                    ).into(imageView)

            }
            else->  Glide.with(mContext)
                .load("")
                .apply(
                    RequestOptions()
                        .placeholder( R.drawable.ic_loading)
                        .error(R.drawable.ic_text)

                ).into(imageView)

        }*/

    }

    @SuppressLint("SetJavaScriptEnabled")
    fun viewDocumentImage(message: MessageItem) {
        val dialogBinding =
            DialogViewChatItemBinding.inflate(LayoutInflater.from(mContext), null, false)
        val dialog = Dialog(mContext, R.style.GalleryDialog)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)
        val fileUrl = message.image_url

        dialogBinding.imgClose.setOnClickListener {
            dialogBinding.webView.loadUrl("")
            dialog.dismiss()
        }
        when ("image") {
            "image" -> {
                dialogBinding.imgDocument.visibility = View.VISIBLE
                Glide.with(mContext)
                    .load(fileUrl)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_logo)

                    ).into(dialogBinding.imgDocument)
            }
            "video" -> {
                dialogBinding.webVideo.visibility = View.VISIBLE
                loadVideoUrlInWebView(dialogBinding.webVideo, fileUrl)
            }
            "pdf" -> {
                dialogBinding.webView.visibility = View.VISIBLE
                val googleDocsUrl = "https://docs.google.com/gview?embedded=true&url=" + fileUrl
                dialogBinding.webView.settings.javaScriptEnabled = true
                dialogBinding.webView.settings.pluginState = WebSettings.PluginState.ON
                dialogBinding.webView.webChromeClient = WebChromeClient()
                dialogBinding.webView.loadUrl(googleDocsUrl)
            }
            else -> {
                dialogBinding.webView.visibility = View.VISIBLE

// Enable JavaScript and other web settings
                dialogBinding.webView.settings.javaScriptEnabled = true
                dialogBinding.webView.getSettings().setJavaScriptEnabled(true);
                dialogBinding.webView.settings.allowFileAccess = true
                dialogBinding.webView.settings.allowFileAccessFromFileURLs = true
                dialogBinding.webView.settings.allowUniversalAccessFromFileURLs = true

// Load the URL in the web view


                dialogBinding.webView.loadUrl(fileUrl)
            }

        }

        dialog.show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun loadVideoUrlInWebView(webView: WebView, fileUrl: String) {
        printLog(" file url", fileUrl.toString())
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.webChromeClient = WebChromeClient()

        webView.loadDataWithBaseURL(
            null,
            "<html><body><iframe width=\"100%\" height=\"100%\" src=\"$fileUrl\" frameborder=\"0\" allowfullscreen></iframe></body></html>",
            "text/html",
            "utf-8",
            null
        )
    }
}





