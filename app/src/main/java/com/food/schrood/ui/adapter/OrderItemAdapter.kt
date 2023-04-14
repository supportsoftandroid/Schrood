package com.food.schrood.ui.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.recyclerview.widget.RecyclerView

import com.food.schrood.databinding.ListOrderItemBinding

import com.food.schrood.model.CommonDataItem


class OrderItemAdapter(mContext: Context, categoryList: MutableList<CommonDataItem>, val onItemClick: ( position:Int,type:String) -> Unit) :
    RecyclerView.Adapter<OrderItemAdapter.MainViewHolder>()  {
    var dataList = mutableListOf<CommonDataItem>()


    var mContext: Context

    init {
        this.dataList = categoryList
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        if (current.is_selected){
            holder.binding.tvRate.visibility=View.VISIBLE
            holder.binding.tvReOrder.visibility=View.VISIBLE
            holder.binding.tvOrderStatus.visibility=View.GONE
        }else{
            holder.binding.tvOrderStatus.visibility=View.VISIBLE
        }
     //   holder.binding.tvName.text=current.title
        holder.itemView.setOnClickListener(){
            onItemClick(position,dataList[position].type)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListOrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }

}





