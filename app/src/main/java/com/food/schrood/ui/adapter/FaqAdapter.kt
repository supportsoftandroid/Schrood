package com.food.schrood.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.schrood.R
import com.food.schrood.databinding.ListFaqItemBinding
import com.food.schrood.model.FaqItem
import com.food.schrood.utility.StaticData.Companion.printLog


class FaqAdapter(mContext: Context, categoryList: MutableList<FaqItem>) :
    RecyclerView.Adapter<FaqAdapter.MainViewHolder>(),
    View.OnClickListener {
    var dataList = mutableListOf<FaqItem>()

    var selectedPos: Int = -1
    var mContext: Context

    init {
        this.dataList = categoryList
        this.mContext = mContext

        printLog("category adapter", "====")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListFaqItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        /* val current = dataList[position]
         holder.bind(current)*/
        //  holder.binding.tvTitle.text=Html.fromHtml(current.question)
        //  holder.binding.tvMessage.text=Html.fromHtml(current.answer)
        if (position == selectedPos) {
            holder.binding.imgArrow.setImageResource(R.drawable.ic_arrow_up_24)
            holder.binding.tvMessage.visibility = View.VISIBLE
        } else {
            holder.binding.imgArrow.setImageResource(R.drawable.ic_arrow_down_24)
            holder.binding.tvMessage.visibility = View.GONE
        }
        holder.itemView.setOnClickListener {

            if (selectedPos == position) {
                selectedPos = -1
            } else {
                selectedPos = position
            }
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return 10
        //  return dataList.size
    }

    class MainViewHolder(val binding: ListFaqItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modal: FaqItem) {
            binding.modal = modal
        }
    }

    override fun onClick(v: View?) {

    }

}





