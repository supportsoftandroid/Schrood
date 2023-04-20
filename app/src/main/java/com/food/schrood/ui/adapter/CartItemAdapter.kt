package com.food.schrood.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.schrood.databinding.ListCartItemBinding
import com.food.schrood.model.CommonDataItem


class CartItemAdapter(
    mContext: Context,
    categoryList: MutableList<CommonDataItem>,
    val listenerClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<CartItemAdapter.MainViewHolder>() {
    var dataList = mutableListOf<CommonDataItem>()
    var mContext: Context

    init {
        this.dataList = categoryList
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)

        holder.binding.tvName.text = current.title
        holder.itemView.setOnClickListener() {
            listenerClick(position, dataList[position].type)
        }
        holder.binding.tvPlus.setOnClickListener() {
            val count = holder.binding.tvCount.text.toString().toInt()
            val newCountValue = count + 1
            holder.binding.tvCount.text = newCountValue.toString()
        }

        holder.binding.tvMinus.setOnClickListener() {
            val count = holder.binding.tvCount.text.toString().toInt()
            if (count > 0) {
                val newCountValue = count - 1
                holder.binding.tvCount.text = newCountValue.toString()
            } else {
                dataList.removeAt(position)
                notifyDataSetChanged()
            }


        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListCartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }


}





