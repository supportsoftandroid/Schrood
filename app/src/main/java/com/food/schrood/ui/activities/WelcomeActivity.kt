package com.food.schrood.ui.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.food.schrood.R
import com.food.schrood.databinding.ActivityWelcomeBinding
import com.food.schrood.model.SliderItem
import com.food.schrood.ui.adapter.WelcomePagerAdapter
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.StaticData.Companion.getURLForResource
import com.food.schrood.utility.StaticData.Companion.printLog
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.WelcomeViewModal


class WelcomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityWelcomeBinding
    lateinit var mContext: Context
    var dataList: ArrayList<SliderItem> = ArrayList()
    var currentPos: Int = 0

    private lateinit var utilsManager: UtilsManager
    private lateinit var mAdapter: WelcomePagerAdapter

    private val viewModel by lazy { ViewModelProvider(this@WelcomeActivity)[WelcomeViewModal::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        StaticData.changeStatusBarColor(this, "message")
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorLightYellow)

        // Set the SYSTEM_UI_FLAG_LAYOUT_STABLE and SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN flags
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mContext = this@WelcomeActivity
        utilsManager = UtilsManager(mContext)
        binding.let {
            initPager()
            clickListener()
            getApiData()
        }



    }

    private fun clickListener() {

        binding.btnGetStarted.setOnClickListener() {
            /* if (currentPos < dataList.size - 1) {
                 currentPos = currentPos + 1
                 binding.viewPagerWelcome.setCurrentItem(currentPos)
             } else {
                 moveNextScreen()
             }*/
            moveNextScreen()
        }

    }

    private fun moveNextScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun initPager() {
        dataList = ArrayList<SliderItem>()

        dataList.add(
            SliderItem(
                R.drawable.welcome1_your_food,
                getString(R.string.your_food_your_way_everywhere_everyday),
                getString(R.string.with_scrhood_you_can_make_sure_you_get_exactly_what_you_want_before_your_order),
                getURLForResource(R.drawable.welcome1_your_food).toString()
            )
        )

        dataList.add(
            SliderItem(
                R.drawable.welcome_2_language_no_barreer,
                getString(R.string.language_is_no_barrier_wherever_you_are_in_the_world),
                getString(R.string.schrood_translates_your_messages_into_any_language),
                getURLForResource(R.drawable.welcome_2_language_no_barreer).toString()
            )
        )
        dataList.add(
            SliderItem(
                R.drawable.welcome_3_pick_up_delivery,
                getString(R.string.pick_up_eat_in_or_delivery),
                getString(R.string.schrood_is_free_fast_and_easy_be_schrood_with_your_food_now_that_s_schrood),
                getURLForResource(R.drawable.welcome_3_pick_up_delivery).toString()
            )
        )
        setupPagerAdapter()
        binding.viewPagerWelcome.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == dataList.size - 1) {
                    // last page. make button text to GOT IT
                    //  binding.tvNext.setText(mContext.getString( R.string.next))
                    binding.tvSkip.visibility = View.GONE
                } else {
                    // still pages are left

                    binding.tvSkip.setVisibility(View.GONE)
                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    }

    fun setupPagerAdapter() {
        mAdapter = WelcomePagerAdapter(mContext, dataList)
        binding.viewPagerWelcome.adapter = mAdapter
    }
      fun getApiData(){
          if (utilsManager.isNetworkConnected()) {
              viewModel.getBannerData(this).observe(this, Observer { res ->
                  if (res.status) {
                      if (!res.data.isEmpty()) {
                          dataList.clear()
                          dataList.addAll(res.data)
                          setupPagerAdapter()
                      }
                  }

              })
          }
      }


}