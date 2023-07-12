package com.food.schrood.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.food.schrood.R
import com.food.schrood.databinding.ActivitySignUpBinding
import com.food.schrood.databinding.ActivityVerifyOtpBinding
import com.food.schrood.model.LocationData
import com.food.schrood.model.LoginResponse
import com.food.schrood.utility.*
import com.food.schrood.utility.Constants.KEY_IS_NOTIFICATION
import com.food.schrood.utility.PermissionUtilityUpdated.Companion.LOCATION_PERMISSION_REQUEST_CODE
import com.food.schrood.utility.StaticData.Companion.printLog
import com.food.schrood.utility.StaticData.Companion.showToast
import com.food.schrood.viewmodel.SignUpViewModel
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    val viewModal by lazy { ViewModelProvider(this)[SignUpViewModel::class.java] }
    lateinit var mContext: Context
    lateinit var preferenceManager: PreferenceManager

    private lateinit var dialogVerify: BottomSheetDialog
    lateinit var utilsManager: UtilsManager
    val activityScope = CoroutineScope(Dispatchers.Main)
    var firebase_token = ""
    lateinit var loginResponse: LoginResponse
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var address = ""
    var landmark = ""
    var city = ""
    var state = ""
    var country = ""
    var zipcode = ""
    private var latitude = 0.0
    private var longitude = 0.0
    var locationData: LocationData? = null
    lateinit var edLocation: EditText
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var  isCurrentLocation=false
    var  isResendOTP=false
    override fun onCreate(savedInstanceState: Bundle?) {
        StaticData.changeStatusBarColor(this, "message")
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mContext = this@SignupActivity
        preferenceManager = PreferenceManager(mContext)
        utilsManager = UtilsManager(mContext)
        activityScope.launch {
            clickListener()
            initView()
            PermissionUtilityUpdated.hasPermissions(mContext,
                PermissionUtilityUpdated.PERMISSIONS_ALL_LIST.toString())
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)
            getUserCurrentLocation()
            getFirebaseRegId()
        }

    }

    private fun initView() {
        binding.countryPickerView.setCountryForPhoneCode(Constants.COUNTRY_CODE.toInt())
        StaticData.passWordEditText(mContext, false, binding.edPassword)
        StaticData.passWordEditText(mContext, false, binding.edCofPassword)
    }

    private fun clickListener() {

        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.btnSubmit.setOnClickListener {

            if (binding.edName.text.isEmpty()) {
                showToast(mContext, getString(R.string.enter_name))
                binding.edName.requestFocus()
            } else if (binding.edEmail.text.isEmpty()) {
                binding.edName.clearFocus()
                showToast(mContext, getString(R.string.enter_email_address))
                binding.edEmail.requestFocus()
            } else if (!utilsManager.isValidEmailId(binding.edEmail.text.toString())) {
                showToast(mContext, getString(R.string.enter_valid_email_address))
                binding.edEmail.requestFocus()
            } else if (binding.edPhoneNumber.text.isEmpty()) {
                showToast(mContext, getString(R.string.enter_mobile_number))
                binding.edPhoneNumber.requestFocus()
            } else if (binding.edPhoneNumber.text.length < 9) {
                showToast(mContext, getString(R.string.enter_9digit_mobile_number))
                binding.edPhoneNumber.requestFocus()
            } else if (binding.edPassword.text.isEmpty()) {
                showToast(mContext, getString(R.string.enter_password))
                binding.edPassword.requestFocus()
            } else if (binding.edCofPassword.text.isEmpty()) {
                showToast(mContext, getString(R.string.enter_conform_password))
                binding.edCofPassword.requestFocus()
            } else if (!binding.edPassword.text.toString()
                    .equals(binding.edCofPassword.text.toString())
            ) {
                showToast(mContext, getString(R.string.confirm_password_not_match))
                binding.edCofPassword.requestFocus()
            } else {
                binding.edName.clearFocus()

                binding.edEmail.clearFocus()
                binding.edPhoneNumber.clearFocus()

                binding.edPassword.clearFocus()
                binding.edCofPassword.clearFocus()
                callSignUpAPI()
            }


            //  showVerifyBottomSheet()
        }
    }

    private fun getFirebaseRegId() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            } else {
                firebase_token = task.result
                printLog("firebase_token", firebase_token.toString())
                // Get new FCM registration token
            }
        })
    }

    private fun callSignUpAPI() {
        if (utilsManager.isNetworkConnected()) {
            viewModal.signUpUser(
                mContext,
                binding.edName.text.toString(),
                binding.edEmail.text.toString(),
                binding.countryPickerView.selectedCountryCode.toString(),
                binding.edPhoneNumber.text.toString(),
                binding.edPassword.text.toString(),
                firebase_token
            ).observe(this, androidx.lifecycle.Observer { res ->
                showToast(mContext, res.message)
                if (res.status) {
                    loginResponse = res
                    utilsManager.showOTPDialogBottom(mContext,false, { type, otp, dialog -> onOTPVerified(type, otp, dialog) })



                }

            })
        }
    }

    private fun onOTPVerified(type: String, otp: String, dialog: BottomSheetDialog) {
        dialogVerify = dialog
        if (type.equals("resend")) {
            isResendOTP=true
           callResendOTPAPI()
        } else {
            callVerifyOTPAPI(otp)
        }


    }

    private fun callVerifyOTPAPI(otp: String) {
        if (utilsManager.isNetworkConnected()) {
            viewModal.verifyUser(mContext, binding.edEmail.text.toString(), otp,).observe(this, androidx.lifecycle.Observer { res ->
                showToast(mContext, res.message)
                if (res.status) {
                    preferenceManager.setLoginData(loginResponse)
                    preferenceManager.saveBoolean(Constants.KEY_CHECK_LOGIN, true)
                    utilsManager.showNotificationBottomSheet(mContext, { type, dialog -> onNotificAllowClick(type, dialog) })
                    dialogVerify.dismiss()

                }

            })
        }
    }

    private fun callResendOTPAPI() {
        if (utilsManager.isNetworkConnected()) {
            viewModal.reSendOTP(mContext, country,
                binding.edPhoneNumber.text.toString(),).observe(this, androidx.lifecycle.Observer { res ->
                showToast(mContext, res.message)

            } )
        }
    }

    private fun moveToNextClass() {
        val i = Intent(mContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }

    /*private fun showVerifyBottomSheet() {
        val dialogBinding = ActivityVerifyOtpBinding.inflate(LayoutInflater.from(mContext), null, false)
        dialogVerify = BottomSheetDialog(mContext, R.style.CustomBottomSheetStyle)
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

            dialogVerify.dismiss()
        }
        dialogBinding.tvResend.setOnClickListener {

            callResendOTPAPI()
        }
        dialogBinding.btnSubmit.setOnClickListener {
            if (dialogBinding.pinview.value.length == 4) {
                dialogBinding.pinview.clearFocus()
                callVerifyOTPAPI(dialogBinding.pinview.value)
            } else {
                dialogBinding.pinview.requestPinEntryFocus()
                showToast(mContext, getString(R.string.enter_your_otp_))
            }


        }

    }*/

    private fun onNotificAllowClick(type: String, dialog: BottomSheetDialog) {
        if (type.equals("allow")) {
            preferenceManager.saveBoolean(KEY_IS_NOTIFICATION,true)
            StaticData.askIsNotificationPermission(mContext)
        }
        utilsManager.showLocationBottomSheet(
            mContext,
            { type, dialog -> onLocationAllowClick(type, dialog) })
    }

    private fun onLocationAllowClick(type: String, dialog: BottomSheetDialog) {
        if (type.equals("allow")) {
            isCurrentLocation=true
            getUserCurrentLocation()

        } else {
            utilsManager.showManualLocationDialog(
                mContext,
                { type, edLocation, dialog -> onManualLocationClick(type, edLocation, dialog) })
        }
    }


    private fun onManualLocationClick(type: String, editText: EditText, dialog: BottomSheetDialog) {

        if (type.equals("address")) {
            edLocation = editText
            StaticData.searchPlace(mContext as Activity)

        } else {
            locationData?.let { preferenceManager.setLocationData(it) }
            moveToNextClass()
        }

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
                        printLog("latlng===", latlong.toString())
                        latitude = latlong.latitude
                        longitude = latlong.longitude
                        locationData = StaticData.getAddressFromLatLan(mContext, latitude, longitude)
                        locationData?.type = "manual"
                        locationData?.title = "Location"
                        locationData?.latitude = latitude.toString()
                        locationData?.longitude = longitude.toString()

                        coroutineScope.launch {
                            edLocation.setText(locationData?.address.toString())

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isCurrentLocation) {
                    requestLocation()
                }
            } else {
                // Handle the case when location permission is denied
            }
        }
    }

    private fun getUserCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                mContext as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            requestLocation()
        }
    }

    private fun requestLocation() {
        if (ActivityCompat.checkSelfPermission(
                mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    // Use the obtained location
                    val latitude = location.latitude
                    val longitude = location.longitude
                    // Do something with the coordinates
                    locationData = StaticData.getAddressFromLatLan(mContext, latitude, longitude)
                    locationData?.type = "current"
                    locationData?.title = "Current Location"
                    locationData?.latitude = latitude.toString()
                    locationData?.longitude = longitude.toString()
                    preferenceManager.setLocationData(locationData!!)
                    if (isCurrentLocation){
                        moveToNextClass()
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occurred while retrieving the location
            }
    }
}