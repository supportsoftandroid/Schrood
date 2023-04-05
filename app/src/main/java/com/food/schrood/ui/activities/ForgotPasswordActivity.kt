package com.food.schrood.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.food.schrood.databinding.ActivityLoginBinding
import com.food.schrood.utility.PreferenceManager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var binding: ActivityLoginBinding
    val activityScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(this)
        val secondsDelayed: Long = 1
        activityScope.launch {
            delay(secondsDelayed*1000)


        }

    }
}
