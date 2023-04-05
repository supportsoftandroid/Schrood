package com.food.schrood.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.food.schrood.databinding.ActivityLoginBinding
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var binding: ActivityLoginBinding
    val activityScope = CoroutineScope(Dispatchers.Main)
    lateinit var viewModal: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityLoginBinding.inflate(layoutInflater)
        viewModal = ViewModelProvider(this).get(LoginViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(this)
        val secondsDelayed: Long = 1
        activityScope.launch {
            delay(secondsDelayed*1000)


        }

    }
}
