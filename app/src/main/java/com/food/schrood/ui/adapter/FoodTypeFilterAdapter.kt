package com.food.schrood.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.food.schrood.R
import com.food.schrood.databinding.ListTextItemBinding
import com.food.schrood.model.CommonDataItem


class FoodTypeFilterAdapter(
    mContext: Context,
    categoryList: MutableList<CommonDataItem>,
    val onViewItemClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<FoodTypeFilterAdapter.MainViewHolder>() {
    var dataList = mutableListOf<CommonDataItem>()


    var mContext: Context

    init {
        this.dataList = categoryList
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListTextItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTilte.text = current.title
        if (current.is_selected) {
            holder.binding.constraintStar.setBackgroundResource(R.drawable.btn_background_app_color_radius_8)
            holder.binding.tvTilte.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.colorWhite
                )
            )

        } else {
            holder.binding.constraintStar.setBackgroundResource(R.drawable.btn_background_light_gray_radius_8)
            holder.binding.tvTilte.setTextColor(ContextCompat.getColor(mContext, R.color.colorText))
        }
        holder.itemView.setOnClickListener() {
            if (current.is_selected) {
                dataList[position].is_selected = false
            } else {
                dataList[position].is_selected = true
            }
            onViewItemClick(position, dataList[position].type)
            notifyDataSetChanged()
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





