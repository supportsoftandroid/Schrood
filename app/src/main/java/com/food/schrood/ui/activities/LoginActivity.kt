package com.food.schrood.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.food.schrood.R
import com.food.schrood.databinding.ActivityLoginBinding
import com.food.schrood.databinding.ActivityVerifyOtpBinding
import com.food.schrood.model.LocationData
import com.food.schrood.utility.*
import com.food.schrood.utility.PermissionUtilityUpdated.Companion.PERMISSIONS_ALL_LIST
import com.food.schrood.utility.PermissionUtilityUpdated.Companion.PERMISSION_ALL
import com.food.schrood.utility.StaticData.Companion.printLog
import com.food.schrood.utility.StaticData.Companion.showToast
import com.food.schrood.viewmodel.LoginViewModel
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class LoginActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var binding: ActivityLoginBinding
    val activityScope = CoroutineScope(Dispatchers.Main)
    private val viewModal by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }
    lateinit var mContext: Context

    lateinit var dialogVerify: BottomSheetDialog
    lateinit var dialogLocation: BottomSheetDialog
    lateinit var utilsManager: UtilsManager
    var firebase_token=""

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var address = ""
    var landmark = ""
    var city = ""
    var state = ""
    var country = ""
    var zipcode = ""
    private var latitude = 0.0
    private var longitude = 0.0
    var locationData:LocationData?=null
    var  edLocation:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        mContext=this@LoginActivity
        StaticData.changeStatusBarColor(mContext, "message")
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(mContext)
        utilsManager = UtilsManager(mContext)
        val request_code= 45546564
        PermissionUtilityUpdated.hasPermissions(mContext,PERMISSIONS_ALL_LIST.toString())
        binding.let {
            clickListener()
            activityScope.launch {
                initView()
                getFirebaseRegId()


            }
        }


    }

    private fun initView() {
        StaticData.passWordEditText(mContext, false, binding.edPassword)
    }
    private fun getFirebaseRegId() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            } else {
                firebase_token = task.result
                printLog("firebase_token", firebase_token)
                // Get new FCM registration token
            }
        })
    }
    private fun onNotificAllowClick(type: String, dialog: BottomSheetDialog) {
        utilsManager.showLocationBottomSheet(
            mContext,
            { type, dialog -> onLocationAllowClick(type, dialog) })
    }

    private fun onLocationAllowClick(type: String, dialog: BottomSheetDialog) {
        if (type.equals("allow")) {
            moveNextScreen()
        } else {
            utilsManager.showManualLocationDialog(
                mContext,
                { type, edLocation, dialog -> onManualLocationClick(type,edLocation, dialog) })
        }
    }

    private fun onManualLocationClick(type: String, editText: EditText , dialog: BottomSheetDialog) {
        edLocation=editText
        if (type.equals("address")){
            StaticData.searchPlace(mContext as Activity)

        }else{
            locationData?.let { preferenceManager.setLocationData(it) }
            moveNextScreen()
        }



    }

    private fun clickListener() {
        binding.btnLogin.setOnClickListener() {

            if (binding.edPhoneNumber.text.isEmpty()){
                showToast(mContext,getString(R.string.enter_mobile_number))
                binding.edPhoneNumber.requestFocus()
            } else if (binding.edPhoneNumber.text.toString().length<9){
                showToast(mContext,getString(R.string.enter_9digit_mobile_number))
                binding.edPhoneNumber.requestFocus()
            }else if (binding.edPassword.text.isEmpty()) {
                showToast(mContext, getString(R.string.enter_password))
                binding.edPassword.requestFocus()
            } else{
                binding.edPhoneNumber.clearFocus()
                binding.edPassword.clearFocus()
                callLoginAPI()
            }
            /* utilsManager.showOTPDialogBottom(
                 mContext,
                 { type, otp,dialog -> onOTPVerified(type,otp, dialog) })
 */

        }


       /* binding.btnLogin.setOnClickListener() {
            utilsManager.showNotificationBottomSheet(
                mContext,
                { type, dialog -> onNotificAllowClick(type, dialog) })


        }*/
        binding.tvForgotPassword.setOnClickListener() {

            startActivity(Intent(mContext, ForgotPasswordActivity::class.java))


        }
        binding.tvSignUp.setOnClickListener() {
            startActivity(Intent(mContext, SignupActivity::class.java))

        }

    }
    fun callLoginAPI() {
        if (utilsManager.isNetworkConnected()) {
            var countryCode= binding.countryPickerView.selectedCountryCode.toString()
            if (!countryCode.contains("+")){
                countryCode="+"+countryCode
            }
            viewModal.getLogInData(mContext,
                binding.edPhoneNumber.text.toString(),
                countryCode,
                binding.edPassword.text.toString(),firebase_token).observe(this,
                Observer {res->
                    showToast(mContext,res.message)
                    if (res.status){
                        preferenceManager.setLoginData(res)
                        preferenceManager.saveAuthToken("${res.data.type} ${res.data.token}")
                         moveNextScreen()

                    }

                })
        }
    }
    private fun moveNextScreen() {
        preferenceManager.saveBoolean(Constants.KEY_CHECK_LOGIN,true)
        val i = Intent(mContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }

    private fun showVerifyBottomSheet() {
        dialogVerify = BottomSheetDialog(mContext, R.style.CustomBottomSheetStyle)
        val dialogBinding = ActivityVerifyOtpBinding.inflate(LayoutInflater.from(mContext), null, false)
        val sheetView = dialogBinding.root
        dialogVerify.setContentView(sheetView)
        dialogVerify.setCancelable(false)

        val screenHeight = resources.displayMetrics.heightPixels
        val layoutParams = sheetView.layoutParams
        layoutParams.height = screenHeight
        sheetView.layoutParams = layoutParams

        // Set the bottom sheet to be fullscreen
        dialogVerify.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialogVerify.behavior.isDraggable = false

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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StaticData.AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    printLog(" location place===", place.toString())

                    if (place.latLng != null) {
                        val latlong = place.latLng!!
                        printLog("latlong===", latlong.toString())
                        latitude = latlong.latitude
                        longitude = latlong.longitude
                        locationData=  StaticData.getAddressFromLatLan(mContext,latitude,longitude)
                        locationData?.type="manual"
                        locationData?.title="Saved Address"
                        locationData?.latitude=latitude.toString()
                        locationData?.longitude=longitude.toString()

                        coroutineScope.launch {
                            edLocation?.setText(locationData?.address.toString())

                        }
                    }

                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

                val status: Status = Autocomplete.getStatusFromIntent(data!!)
                printLog(" Place onActivityResult:", status.statusMessage.toString())
                showToast(mContext, status.statusMessage.toString())
//            Toast.makeText(mContext, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show()
            }

        }
    }


}
