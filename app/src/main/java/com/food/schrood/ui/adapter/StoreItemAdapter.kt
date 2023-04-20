package com.food.schrood.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.schrood.R
import com.food.schrood.databinding.ListStoreItemBinding
import com.food.schrood.model.CommonDataItem


class StoreItemAdapter(
    mContext: Context,
    categoryList: MutableList<CommonDataItem>,
    val onStoreClick: (position: Int, type: String) -> Unit
) :
    RecyclerView.Adapter<StoreItemAdapter.MainViewHolder>() {
    var dataList = mutableListOf<CommonDataItem>()
    var mContext: Context

    init {
        this.dataList = categoryList
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListStoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvStoreName.text = current.title
        if (current.is_selected) {
            holder.binding.imgFav.setImageResource(R.drawable.ic_favorite_24)
        } else {
            holder.binding.imgFav.setImageResource(R.drawable.ic_un_favorite)
        }
        holder.binding.imgFav.setOnClickListener() {
            if (current.is_selected) {
                dataList[position].is_selected = false
            } else {
                dataList[position].is_selected = true
            }
            notifyDataSetChanged()
        }
        holder.itemView.setOnClickListener() {
            onStoreClick(position, dataList[position].type)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListStoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }

}





