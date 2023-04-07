package com.food.schrood.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.food.schrood.R
import com.food.schrood.databinding.ActivitySplashBinding
import com.food.schrood.utility.Constants
import com.food.schrood.utility.PreferenceManager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var binding: ActivitySplashBinding
    val activityScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (!isTaskRoot  && intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intent.action != null
            && intent.action == Intent.ACTION_MAIN   ) {
            finish()
            return
        }
        // Set the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.app_color)

        // Set the SYSTEM_UI_FLAG_LAYOUT_STABLE and SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN flags
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


        preferenceManager = PreferenceManager(this)
        val secondsDelayed: Long = 1
        activityScope.launch {
            delay(secondsDelayed*1000)

            if (preferenceManager.getBoolean(Constants.KEY_CHECK_LOGIN)) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
             //   startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            } else
               // startActivity(Intent(this@SplashScreenActivity, WelcomeActivity::class.java))
                startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
                finish()
        }

    }
}
