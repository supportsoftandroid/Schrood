package com.food.schrood.ui.activities


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.food.schrood.R
import com.food.schrood.databinding.ActivityMainBinding
import com.food.schrood.ui.fragments.DashboardFragment
import com.food.schrood.ui.fragments.HomeFragment
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var navView: BottomNavigationView
        fun hideNavigationTab() {
            navView.visibility= View.GONE
        }
        fun visibleNavigationTab() {
            navView.visibility= View.VISIBLE
        }
    }
    private lateinit var binding: ActivityMainBinding
    var doubleBackToExitPressedOnce:Boolean=false
    lateinit var mContext: Context
    lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
          navView = binding.navView

        binding.let{
            initView()
            clickListener()
        }


    }

    private fun initView() {

    }
    private fun clickListener() {
        navView.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.nav_home -> {
                    StaticData.replaceFragment(mContext, HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_home -> {
                    StaticData.replaceFragment(mContext, DashboardFragment())
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }
}