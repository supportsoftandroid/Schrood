package com.food.schrood.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.food.schrood.databinding.ListItemWelcomeBinding
import com.food.schrood.model.SliderItem


class WelcomePagerAdapter(internal var context: Context, var itemList: ArrayList<SliderItem>) :
    PagerAdapter() {

    internal var mLayoutInflater: LayoutInflater


    init {
        mLayoutInflater =
            this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ListItemWelcomeBinding.inflate(LayoutInflater.from(context), container, false)
        val current = itemList[position]

        //  var view = mLayoutInflater.inflate(binding.root, container, false)
        binding.imgBanner.setImageResource(itemList[position].id)

        /*   Glide.with(context)
               .load(current.image)
               .apply(
                   RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.welcome_2_language_no_barreer)
                      // .centerCrop()
               ).into(binding.imgBanner)*/
        binding.tvheader.setText(itemList[position].title)

        binding.tvDescription.setText(itemList[position].content)
        (container as ViewPager).addView(binding.root)
        return binding.root
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1 as View
    }

    @Deprecated("Deprecated in Java")
    override fun destroyItem(arg0: View, arg1: Int, arg2: Any) {
        (arg0 as ViewPager).removeView(arg2 as View)
    }

    fun updateAdapter(dataList: ArrayList<SliderItem>) {
        this.itemList = dataList
        notifyDataSetChanged()


    }
}