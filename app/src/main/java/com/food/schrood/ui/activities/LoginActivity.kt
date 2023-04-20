package com.food.schrood.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.food.schrood.R
import com.food.schrood.databinding.ActivityLoginBinding
import com.food.schrood.databinding.ActivityVerifyOtpBinding
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.UtilsManager
import com.food.schrood.viewmodel.LoginViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var binding: ActivityLoginBinding
    val activityScope = CoroutineScope(Dispatchers.Main)
    lateinit var viewModal: LoginViewModel
    lateinit var dialogVerify: BottomSheetDialog
    lateinit var utilsManager: UtilsManager
    override fun onCreate(savedInstanceState: Bundle?) {
        StaticData.changeStatusBarColor(this, "message")
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModal = ViewModelProvider(this).get(LoginViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(this@LoginActivity)
        utilsManager = UtilsManager(this)
        binding.let {
            clickListener()
            activityScope.launch {


            }
        }


    }

    private fun onNotificAllowClick(type: String, dialog: BottomSheetDialog) {
        utilsManager.showLocationBottomSheet(
            this@LoginActivity,
            { type, dialog -> onLocationAllowClick(type, dialog) })
    }

    private fun onLocationAllowClick(type: String, dialog: BottomSheetDialog) {
        if (type.equals("allow")) {
            moveNextScreen()
        } else {
            utilsManager.showManualLocationDialog(
                this@LoginActivity,
                { type, dialog -> onManualLocationClick(type, dialog) })
        }
    }

    private fun onManualLocationClick(type: String, dialog: BottomSheetDialog) {
        moveNextScreen()

    }

    private fun clickListener() {

        binding.btnLogin.setOnClickListener() {
            utilsManager.showNotificationBottomSheet(
                this@LoginActivity,
                { type, dialog -> onNotificAllowClick(type, dialog) })


        }
        binding.tvForgotPassword.setOnClickListener() {

            startActivity(Intent(this, ForgotPasswordActivity::class.java))


        }
        binding.tvSignUp.setOnClickListener() {
            startActivity(Intent(this, SignupActivity::class.java))

        }

    }

    private fun moveNextScreen() {
        val i = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }

    private fun showVerifyBottomSheet() {
        dialogVerify = BottomSheetDialog(this, R.style.CustomBottomSheetStyle)
        val dialogBinding = ActivityVerifyOtpBinding.inflate(LayoutInflater.from(this), null, false)
        val sheetView = dialogBinding.root
        dialogVerify.setContentView(sheetView)
        dialogVerify.setCancelable(false)

        val screenHeight = resources.displayMetrics.heightPixels
        val layoutParams = sheetView.layoutParams
        layoutParams.height = screenHeight
        sheetView.layoutParams = layoutParams

        // Set the bottom sheet to be fullscreen
        dialogVerify.behavior.state = BottomSheetBehavior.STATE_EXPANDED


        dialogBinding.imgBack.visibility = View.VISIBLE
        dialogBinding.imgBack.setOnClickListener {
            // Delete code here;
            dialogVerify.dismiss()
        }
        dialogBinding.btnSubmit.setOnClickListener {

            dialogVerify.dismiss()

        }
        dialogVerify.show()

    }
}
