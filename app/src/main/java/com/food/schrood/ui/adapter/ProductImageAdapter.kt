package com.food.schrood.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.schrood.databinding.ListImageItemBinding
import com.food.schrood.model.CommonDataItem


class ProductImageAdapter(mContext: Context, dataList: MutableList<CommonDataItem>) :
    RecyclerView.Adapter<ProductImageAdapter.MainViewHolder>(),
    View.OnClickListener {
    var dataList = mutableListOf<CommonDataItem>()


    var mContext: Context

    init {
        this.dataList = dataList
        this.mContext = mContext

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
          val current = dataList[position]
         holder.bind(current)

    }

    override fun getItemCount(): Int {
          return dataList.size
    }

    class MainViewHolder(val binding: ListImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }

    override fun onClick(v: View?) {

    }

}





