package com.food.schrood.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.food.schrood.databinding.ActivityLoginBinding
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
       binding.let {
           clickListener()
           activityScope.launch {



           }
       }


    }
    private fun clickListener() {

        binding.btnLogin.setOnClickListener(){

            val i=Intent(this,  MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
        binding.tvForgotPassword.setOnClickListener(){

            startActivity(Intent(this,  ForgotPasswordActivity::class.java))


        }
        binding.tvSignUp.setOnClickListener(){

            startActivity(Intent(this,  SignupActivity::class.java))
            finish()
        }

    }

    private fun moveNextScreen(java: Class<Activity>) {
        startActivity(Intent(this,  java::class.java))
        finish()
    }
}
