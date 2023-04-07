package com.food.schrood.ui.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup


import androidx.recyclerview.widget.RecyclerView
import com.food.schrood.databinding.ListOrderHomeItemBinding
import com.food.schrood.databinding.ListTextItemBinding
import com.food.schrood.interfaces.CommonClickListener
import com.food.schrood.model.CommonDataItem


class RecentOrderAdapter(mContext: Context, categoryList: MutableList<CommonDataItem>,val onOrderClick: (type:String,position:Int) -> Unit) :
    RecyclerView.Adapter<RecentOrderAdapter.MainViewHolder>()  {
    var dataList = mutableListOf<CommonDataItem>()


    var mContext: Context

    init {
        this.dataList = categoryList
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListOrderHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTitle.text=current.title
        holder.itemView.setOnClickListener(){
            onOrderClick(dataList[position].type,position)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListOrderHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }


}





