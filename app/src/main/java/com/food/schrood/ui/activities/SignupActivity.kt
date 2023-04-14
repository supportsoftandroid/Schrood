package com.food.schrood.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.food.schrood.R
import com.food.schrood.databinding.ActivitySignUpBinding
import com.food.schrood.databinding.ActivityVerifyOtpBinding
import com.food.schrood.databinding.DialogAllowLocationsBinding
import com.food.schrood.databinding.DialogAllowNotificationsBinding
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.viewmodel.SignUpViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var binding: ActivitySignUpBinding
    private lateinit var dialogVerify: BottomSheetDialog
    private lateinit var dialogNotfic: BottomSheetDialog
    private lateinit var dialogLocation: BottomSheetDialog
    val activityScope = CoroutineScope(Dispatchers.Main)
    lateinit var viewModal: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        viewModal = ViewModelProvider(this).get(SignUpViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(this)

        activityScope.launch {
            clickListener()

        }

    }

    private fun clickListener() {

        binding.btnSubmit.setOnClickListener {
            showVerifyBottomSheet()
        }
    }

    private fun moveToNextClass() {
        val i = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }

    private fun showVerifyBottomSheet() {
        val dialogBinding = ActivityVerifyOtpBinding.inflate(LayoutInflater.from(this), null, false)
        dialogVerify = BottomSheetDialog(this, R.style.CustomBottomSheetStyle)
        val sheetView = dialogBinding.root
        dialogVerify.setContentView(sheetView)
        dialogVerify.setCancelable(false)

        val screenHeight = resources.displayMetrics.heightPixels
        val layoutParams = sheetView.layoutParams
        layoutParams.height = screenHeight
        sheetView.layoutParams = layoutParams

        // Set the bottom sheet to be fullscreen
        dialogVerify.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialogVerify.show()
        dialogBinding.imgBack.visibility = View.VISIBLE
        dialogBinding.imgBack.setOnClickListener {
            // Delete code here;
            dialogVerify.dismiss()
        }
        dialogBinding.btnSubmit.setOnClickListener {

            dialogVerify.dismiss()

            showNotificationsBottomSheet()
        }

    }

    private fun showNotificationsBottomSheet() {
        val dialogBinding =
            DialogAllowNotificationsBinding.inflate(LayoutInflater.from(this), null, false)
        dialogNotfic = BottomSheetDialog(this, R.style.CustomBottomSheetStyle)
        val sheetView = dialogBinding.root
        dialogNotfic.setContentView(sheetView)
        dialogNotfic.setCancelable(false)

        val screenHeight = resources.displayMetrics.heightPixels
        val layoutParams = sheetView.layoutParams
        layoutParams.height = screenHeight
        sheetView.layoutParams = layoutParams

        // Set the bottom sheet to be fullscreen
        dialogNotfic.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialogNotfic.show()



        dialogBinding.imgBack.visibility = View.VISIBLE
        dialogBinding.imgBack.setOnClickListener {
            // Delete code here;
            dialogNotfic.dismiss()
        }
        dialogBinding.btnAllow.setOnClickListener {
            dialogNotfic.dismiss()
            showLocationBottomSheet()

        }

    }
    private fun showLocationBottomSheet() {
        val dialogBinding =
            DialogAllowLocationsBinding.inflate(LayoutInflater.from(this), null, false)
        dialogLocation = BottomSheetDialog(this, R.style.CustomBottomSheetStyle)
        val sheetView = dialogBinding.root
        dialogLocation.setContentView(sheetView)
        dialogLocation.setCancelable(false)

        val screenHeight = resources.displayMetrics.heightPixels
        val layoutParams = sheetView.layoutParams
        layoutParams.height = screenHeight
        sheetView.layoutParams = layoutParams

        // Set the bottom sheet to be fullscreen
        dialogLocation.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialogLocation.show()



        dialogBinding.imgBack.visibility = View.VISIBLE
        dialogBinding.imgBack.setOnClickListener {
            // Delete code here;
            dialogLocation.dismiss()
        }
        dialogBinding.btnAllow.setOnClickListener {
            dialogLocation.dismiss()

            moveToNextClass()
        }

    }
}