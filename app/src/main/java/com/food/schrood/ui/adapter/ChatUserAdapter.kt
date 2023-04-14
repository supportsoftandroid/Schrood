package com.food.schrood.ui.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.schrood.R
import com.food.schrood.databinding.ListChatUserItemBinding
import com.food.schrood.interfaces.CommonClickListener
import com.food.schrood.model.ChatUserItem


class ChatUserAdapter(mContext: Context, dataList: MutableList<ChatUserItem>, listener: CommonClickListener ) :
    RecyclerView.Adapter<ChatUserAdapter.MainViewHolder>()
     {
    var dataList = mutableListOf<ChatUserItem>()
      var listener:CommonClickListener
    var selectedPos:Int =-1
    var mContext: Context

    init {
        this.dataList = dataList
        this.mContext = mContext
        this.listener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListChatUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.txtUserName.text=current.name
        if (position/2==0){
            holder.binding.clvMain.setBackgroundColor(mContext.resources.getColor( R.color.colorWhite))
        }else{
            holder.binding.clvMain.setBackgroundColor(mContext.resources.getColor( R.color.colorFAF9FE))
        }

     /*   holder.binding.tvMessage.text=current.last_chat_message
        holder.binding.tvTime.text=getTimeFromDate(current.last_chat_time)*/
        holder.itemView.setOnClickListener(){
            listener.onClicked(position,current.name)
        }
      /*  Glide.with(mContext)
            .load(current.image)
            .apply(
                RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_logo)

            ).into(holder.binding.imgProfile)*/
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

         fun update(newdataList: MutableList<ChatUserItem>) {
             if (newdataList != null) {
                 dataList=newdataList
                 notifyDataSetChanged()
             }

         }

         class MainViewHolder(val binding: ListChatUserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: ChatUserItem) {
            binding.modal = modal
        }
    }


}





