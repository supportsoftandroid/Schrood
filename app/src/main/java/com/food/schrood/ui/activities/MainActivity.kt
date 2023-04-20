package com.food.schrood.ui.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.food.schrood.R
import com.food.schrood.databinding.ActivityMainBinding
import com.food.schrood.ui.fragments.*
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.StaticData.Companion.printLog
import com.food.schrood.utility.StaticData.Companion.showToast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var navView: BottomNavigationView
        fun hideNavigationTab() {
            navView.visibility = View.GONE
        }

        fun visibleNavigationTab() {
            navView.visibility = View.VISIBLE
        }
    }

    private lateinit var binding: ActivityMainBinding
    var doubleBackToExitPressedOnce: Boolean = false
    var isHomeFragment: Boolean = true
    lateinit var mContext: Context
    lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mContext = this@MainActivity
        preferenceManager = PreferenceManager(mContext)
        navView = binding.navView
        //  navView.inflateMenu(R.menu.bottom_nav_menu)
        binding.let {
            initView()
            clickListener()
            // navView.menu.findItem(R.id.nav_home).isChecked=true
            StaticData.replaceFragment(mContext, HomeFragment())
        }


    }

    private fun initView() {


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                printLog("TAG", "Activity back pressed invoked")
                // Do custom work here
                // if you want onBackPressed() to be called as normal afterwards

                // isEnabled = false
                onBackPressed()

            }
        }
        )
    }

    private fun clickListener() {
        navView.setOnItemSelectedListener {
            isHomeFragment = false
            when (it.itemId) {
                R.id.nav_home -> {
                    isHomeFragment = true
                    StaticData.replaceFragment(mContext, HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_my_order -> {
                    StaticData.replaceFragment(mContext, MyOrderFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_favorite -> {
                    StaticData.replaceFragment(mContext, FavoriteFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_message -> {
                    StaticData.replaceFragment(mContext, MessageFragment())
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        if (backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
            if (backStackEntryCount == 1) {
                navView.visibility = View.VISIBLE
                //  super.onBackPressed();
            } else {
                navView.visibility = View.GONE
                //  super.onBackPressed();
            }
        } else {

            navView.visibility = View.VISIBLE
            if (!isHomeFragment) {
                StaticData.changeStatusBarColor(this, "home")
                navView.selectedItemId = R.id.nav_home
                doubleBackToExitPressedOnce = false
                //StaticData.replaceFragment(mContext, HomeFragment())
            } else {
                if (doubleBackToExitPressedOnce) {
                    finish()
                } else {
                    doubleBackToExitPressedOnce = true
                    showToast(applicationContext, getString(R.string.press_again_to_exit_from_app))
                    GlobalScope.launch {
                        delay(2000)
                        doubleBackToExitPressedOnce = false
                    }
                }
            }


        }
    }

    @Deprecated("Deprecated in Java")

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}