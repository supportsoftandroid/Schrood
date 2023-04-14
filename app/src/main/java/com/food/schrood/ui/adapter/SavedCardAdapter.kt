package com.food.schrood.ui.adapter



import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.recyclerview.widget.RecyclerView
import com.food.schrood.databinding.ListCardItemBinding

import com.food.schrood.model.CardData


class SavedCardAdapter(mContext: Context, categoryList: MutableList<CardData>, userName: String, isDelete: Boolean,
                       val listenerClick: (  Int,String ) -> Unit  ) :
    RecyclerView.Adapter<SavedCardAdapter.MainViewHolder>()  {
    var dataList = mutableListOf<CardData>()

    var userName:String=""
    var isDelete:Boolean = false

    var mContext: Context

    init {
        this.dataList = categoryList
        this.mContext = mContext

        this.userName = userName
        this.isDelete = isDelete
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.imgCard.text= current.brand
        holder.binding.txtName.text=if(TextUtils.isEmpty(current.name)) userName else current.name
        holder.binding.tvCardNo.text= "**** **** **** ${current.last4}"
        holder.itemView.setOnClickListener(){
            listenerClick(position,"view")
        }
        if (isDelete){
            holder.binding.imgRight.visibility=View.GONE
        }else{
            holder.binding.imgRight.visibility=View.VISIBLE
        }
        holder.binding.imgRight.setOnClickListener(){
            listenerClick(position,"view")
        }
        holder.binding.imgDelete.setOnClickListener(){
            listenerClick(position,"delete")
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CardData) {
            binding.modal = modal
        }
    }


}





