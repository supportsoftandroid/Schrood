package com.food.schrood.ui.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.food.schrood.databinding.ListReviewItemBinding

import com.food.schrood.model.CommonDataItem


class ReviewAdapter(mContext: Context, categoryList: MutableList<CommonDataItem>, val listenerClick: (Int, String) -> Unit ) :
    RecyclerView.Adapter<ReviewAdapter.MainViewHolder>()  {
    var dataList = mutableListOf<CommonDataItem>()
    var mContext: Context

    init {
        this.dataList = categoryList
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)

        holder.binding.tvName.text=current.title
        holder.itemView.setOnClickListener(){
            listenerClick( position,dataList[position].type)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }


}





