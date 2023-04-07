package com.food.schrood.ui.adapter



import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup


import androidx.recyclerview.widget.RecyclerView

import com.food.schrood.databinding.ListStoreItemBinding

import com.food.schrood.model.CommonDataItem


class StoreAdapter(mContext: Context, categoryList: MutableList<CommonDataItem>,val onStoreClick: (type:String,position:Int) -> Unit ) :
    RecyclerView.Adapter<StoreAdapter.MainViewHolder>()  {
    var dataList = mutableListOf<CommonDataItem>()


    var mContext: Context

    init {
        this.dataList = categoryList
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListStoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvStoreName.text=current.title
        holder.itemView.setOnClickListener(){
            onStoreClick(dataList[position].type,position)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListStoreItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }


}





