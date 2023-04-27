package com.food.schrood.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.food.schrood.R
import com.food.schrood.databinding.ListUnitItemBinding
import com.food.schrood.model.CommonDataItem


class UnitItemAdapter(
    mContext: Context,
    categoryList: MutableList<CommonDataItem>,
    val onViewItemClick: (Int, String) -> Unit
) :
    RecyclerView.Adapter<UnitItemAdapter.MainViewHolder>() {
    var dataList = mutableListOf<CommonDataItem>()


    var mContext: Context
    var selectedPos = 0

    init {
        this.dataList = categoryList
        this.mContext = mContext


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ListUnitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = dataList[position]
        holder.bind(current)
        holder.binding.tvTitle.text = current.title
        if (position == selectedPos) {
            holder.binding.llMain.setBackgroundResource(R.drawable.btn_background_app_color_radius_8)
            holder.binding.tvTitle.setTextColor(
                ContextCompat.getColor(mContext, R.color.colorWhite)
            )

        } else {
            holder.binding.llMain.setBackgroundResource(R.drawable.btn_background_light_gray_radius_8)
            holder.binding.tvTitle.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.textPlaceHolder
                )
            )
        }
        holder.itemView.setOnClickListener() {
            selectedPos = position
            onViewItemClick(position, dataList[position].type)
            notifyDataSetChanged()
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MainViewHolder(val binding: ListUnitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: CommonDataItem) {
            binding.modal = modal
        }
    }


}





