package com.food.schrood.ui.activities

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.food.schrood.R
import com.food.schrood.databinding.ActivityForgotPasswordBinding
import com.food.schrood.databinding.ActivityLoginBinding
import com.food.schrood.databinding.ActivityVerifyOtpBinding
import com.food.schrood.databinding.DialogAllowLocationsBinding
import com.food.schrood.databinding.DialogResetPasswordBinding
import com.food.schrood.utility.PreferenceManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.UtilsManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordBinding
    val activityScope = CoroutineScope(Dispatchers.Main)
    lateinit var dialogVerify:BottomSheetDialog
    lateinit var dialogResetPass:BottomSheetDialog
    lateinit var mContext: Context
    lateinit var preferenceManager: PreferenceManager
    private lateinit var utilsManager: UtilsManager
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityForgotPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mContext=this

        preferenceManager = PreferenceManager(mContext)
        utilsManager = UtilsManager(mContext)
        binding.let {
            clickListener()
        }

    }

    private fun clickListener() {

        binding.btnSubmit.setOnClickListener {
          showVerifyBottomSheet()

        }


    }
    private fun showVerifyBottomSheet() {
        dialogVerify= BottomSheetDialog(this, R.style.CustomBottomSheetStyle)
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
        dialogVerify.show()




        binding.imgBack.visibility = View.VISIBLE
        binding.imgBack.setOnClickListener {
            // Delete code here;
            dialogVerify.dismiss()
        }
        binding.btnSubmit.setOnClickListener {

            dialogVerify.dismiss()
            showResetPasswordBottomSheet()

        }

    }
    private fun showResetPasswordBottomSheet() {
        dialogResetPass= BottomSheetDialog(this, R.style.CustomBottomSheetStyle)
        val dialogBinding = DialogResetPasswordBinding.inflate(LayoutInflater.from(this), null, false)
        val sheetView = dialogBinding.root

        dialogResetPass.setContentView(sheetView)
        dialogResetPass.setCancelable(false)
        val screenHeight = resources.displayMetrics.heightPixels
        val layoutParams = sheetView.layoutParams
        layoutParams.height = screenHeight
        sheetView.layoutParams = layoutParams

        // Set the bottom sheet to be fullscreen
        dialogVerify.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialogResetPass.show()





        StaticData.passWordEditText( mContext,false,dialogBinding.edPassword)
        StaticData.passWordEditText( mContext,false,dialogBinding.edConPassword)
        dialogBinding.imgBack.visibility = View.VISIBLE
        dialogBinding.imgBack.setOnClickListener {
            // Delete code here;
            dialogResetPass.dismiss()
        }
        dialogBinding.btnSubmit.setOnClickListener {

            dialogResetPass.dismiss()
            finish()
        }

    }
}
