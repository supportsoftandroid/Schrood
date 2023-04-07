package com.food.schrood.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.food.schrood.databinding.ActivityLoginBinding
import com.food.schrood.databinding.ActivitySignUpBinding
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.viewmodel.SignUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var binding: ActivitySignUpBinding
    val activityScope = CoroutineScope(Dispatchers.Main)
    lateinit var viewModal: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        viewModal = ViewModelProvider(this).get(SignUpViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(this)

        activityScope.launch {



        }

    }
}
