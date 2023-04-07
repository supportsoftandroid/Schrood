package com.food.schrood.ui.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup


import androidx.recyclerview.widget.RecyclerView
import com.food.schrood.databinding.ListTextItemBinding
import com.food.schrood.interfaces.FoodClickListener
import com.food.schrood.model.CommonDataItem


class FoodTypeFilterAdapter(mContext: Context, categoryList: MutableList<CommonDataItem>, val onFoodClick: ( String, Int) -> Unit) :
    RecyclerView.Adapter<FoodTypeFilterAdapter.MainViewHolder>()  {
    var dataList = mutableListOf<CommonDataItem>()


    var mContext: Context

    init {
        this.dataList = categoryList
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListTextItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTilte.text=current.title
        holder.itemView.setOnClickListener(){
            onFoodClick(dataList[position].type,position)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListTextItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }


}





