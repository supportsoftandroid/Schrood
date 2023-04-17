package com.food.schrood.utility


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.food.schrood.R
import com.food.schrood.databinding.ActivityVerifyOtpBinding
import com.food.schrood.databinding.DialogAllowLocationsBinding
import com.food.schrood.databinding.DialogAllowNotificationsBinding
import com.food.schrood.databinding.DialogBottomlocationSearchBinding
import com.food.schrood.databinding.DialogImageUploadBinding
import com.food.schrood.ui.activities.LoginActivity

import com.food.schrood.utility.Constants.ERROR_ALERT
import com.food.schrood.utility.StaticData.Companion.IMAGE_CROP_REQUEST_CODE
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

/**
 * Created by Shoukin Choudhary 9166900279  on 09/01/2023.
 */
class UtilsManager(private val context: Context) {
    private lateinit var dialogImageUpload: BottomSheetDialog
    private val coroutineScope= CoroutineScope(Dispatchers.Main)
    companion object {
        private const val EMAIL_PATTERN = ("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    }
    fun dpToPx(dp: Int): Int {
        val r = context.resources
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
            )
        )
    }

   fun hideKeyboard() {
        val activity = context as AppCompatActivity
        val view = activity.currentFocus
        if (view != null) {
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


      fun showGallaryBottomModelSheet(  activity: Activity, ) {
          val  bindingDialog = DialogImageUploadBinding.inflate(LayoutInflater.from(context), null,false)
        //val sheetView: View =  LayoutInflater.from(context).inflate(R.layout.dialog_image_upload,null)

       dialogImageUpload = BottomSheetDialog(activity,R.style.GalleryDialog)
       dialogImageUpload.setContentView(bindingDialog.root)


      /*  val cameraView = sheetView.findViewById<LinearLayout>(R.id.layout_camera)
        val viewGallary = sheetView.findViewById<LinearLayout>(R.id.layout_gallery)
        val imgClose = sheetView.findViewById<ImageView>(R.id.imgClose)*/
         // checking for runtime permission
        StaticData.checkFileAccessPermission(context)
          bindingDialog.imgClose.setOnClickListener {
            dialogImageUpload.dismiss()

        }
          bindingDialog.layoutCamera.setOnClickListener {
            // Camera code here;
            dialogImageUpload.dismiss()

                //  StaticData.takeNewPicture(context as Activity)
                ImagePicker.with(context as Activity)
                    .crop()
                    .cameraOnly()//Crop image(Optional), Check Customization for more option
                    .compress(150)			//Final image size will be less than 1 MB(Optional)
                    .createIntent { intent ->
                        activity.startActivityForResult(intent,IMAGE_CROP_REQUEST_CODE)
                    //    startForImageResult.launch(intent)
                    }





        }
        bindingDialog.layoutGallery.setOnClickListener {
            dialogImageUpload.dismiss()

                ImagePicker.with(context as Activity)
                    .crop()
                    .galleryOnly()
                    .compress(150)			//Final image size will be less than 1 MB(Optional)
                    .createIntent { intent ->
                        activity.startActivityForResult(intent,IMAGE_CROP_REQUEST_CODE)
                      //  startForImageResult.launch(intent)
                    }

        }
        dialogImageUpload.show()
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isValidEmailId(target: String?): Boolean {
        return target != null && Pattern.compile(EMAIL_PATTERN).matcher(
            target
        ).matches()
    }


    fun checkEditText(editText: EditText, lable: String?): Boolean {
        var isSuccess=true
        if (TextUtils.isEmpty(editText.text.toString().trim())){
          Toast.makeText(context,lable,Toast.LENGTH_SHORT).show()
            editText.requestFocus()
            isSuccess=false
        }
        return isSuccess
    }
    fun showAlertConnectionError() {
        val builder =
            AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle(Constants.ERROR_NO_INTERNET_ALERT)
        builder.setMessage(Constants.ERROR_MESSAGE)
        builder.setPositiveButton("Ok") { dialogInterface, i -> dialogInterface.dismiss() }
        builder.show()
    }
    fun showAlertMessageError(mContext:Context,message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle(ERROR_ALERT)
        builder.setMessage(message)
        builder.setPositiveButton("Ok") { dialogInterface, i -> dialogInterface.dismiss() }
        builder.show()

    }
    fun showAlertMessageAutoDismiss(message: String) {
        val dialog = AlertDialog.Builder(context)
            .setMessage(message)
            .setCancelable(true)
            .create()
        dialog.show()

        coroutineScope.launch {
            delay(2000)
            dialog.dismiss()
        }
    }

    fun isNetworkConnected(): Boolean {
        var isConnected=false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        isConnected= activeNetworkInfo != null && activeNetworkInfo.isConnected
        if (!isConnected){
         //   Toast.makeText(context, Constants.ERROR_MESSAGE,1).show()
            showAlertConnectionError()

        }
        return isConnected
    }

   /* fun bodyToString(request: RequestBody?): String {
        return try {
            val buffer = Buffer()
            if (request != null) request.writeTo(buffer) else return ""
            URLDecoder.decode(buffer.readUtf8(), "UTF-8")
        } catch (e: IOException) {
            "did not work"
        }
    }
 */
    fun callToUser(mobileNo: String?) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +mobileNo.toString().trim()))
       context.startActivity(intent)
    }

      fun showNotificationBottomSheet(context: Context,  onItemClick: ( type:String,dlg:BottomSheetDialog) -> Unit) {
       val dialog= BottomSheetDialog(context, R.style.CustomBottomSheetStyle)
        val dialogBinding = DialogAllowNotificationsBinding.inflate(LayoutInflater.from(context), null, false)
        val sheetView = dialogBinding.root
        dialog.setContentView(sheetView)
        dialog.setCancelable(false)

        val screenHeight = context.resources.displayMetrics.heightPixels
        val layoutParams = sheetView.layoutParams
        layoutParams.height = screenHeight
        sheetView.layoutParams = layoutParams

        // Set the bottom sheet to be fullscreen
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED


        dialogBinding.imgBack.visibility = View.VISIBLE
        dialogBinding.imgBack.setOnClickListener {
            // Delete code here;
            dialog.dismiss()
        }
        dialogBinding.btnAllow.setOnClickListener {

            onItemClick("allow",dialog)

        }
        dialogBinding.tvCancel.setOnClickListener {
            onItemClick("cancel",dialog)

        }
        dialog.show()

    }
      fun showLocationBottomSheet(context: Context,  onItemClick: ( type:String,dlg:BottomSheetDialog) -> Unit) {
       val dialog= BottomSheetDialog(context, R.style.CustomBottomSheetStyle)
        val dialogBinding = DialogAllowLocationsBinding.inflate(LayoutInflater.from(context), null, false)
        val sheetView = dialogBinding.root
        dialog.setContentView(sheetView)
        dialog.setCancelable(false)

        val screenHeight = context.resources.displayMetrics.heightPixels
        val layoutParams = sheetView.layoutParams
        layoutParams.height = screenHeight
        sheetView.layoutParams = layoutParams

        // Set the bottom sheet to be fullscreen
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED


        dialogBinding.imgBack.visibility = View.VISIBLE
        dialogBinding.imgBack.setOnClickListener {
            // Delete code here;
            dialog.dismiss()
        }
        dialogBinding.btnAllow.setOnClickListener {

            onItemClick("allow",dialog)

        }
        dialogBinding.tvCancel.setOnClickListener {

            onItemClick("manually",dialog)

        }
        dialog.show()

    }
      fun showManualLocationDialog(context: Context,  onItemClick: ( type:String,dlg:BottomSheetDialog) -> Unit) {
       val dialog= BottomSheetDialog(context, R.style.CustomBottomSheetStyle)
        val dialogBinding = DialogBottomlocationSearchBinding.inflate(LayoutInflater.from(context), null, false)
        val sheetView = dialogBinding.root
        dialog.setContentView(sheetView)
        dialog.setCancelable(false)

        val screenHeight = context.resources.displayMetrics.heightPixels
        val layoutParams = sheetView.layoutParams
        layoutParams.height = screenHeight
        sheetView.layoutParams = layoutParams

        // Set the bottom sheet to be fullscreen
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED


        dialogBinding.imgClose.visibility = View.VISIBLE
        dialogBinding.imgClose.setOnClickListener {
            // Delete code here;
            dialog.dismiss()
        }
        dialogBinding.btnSubmit.setOnClickListener {

            onItemClick("allow",dialog)

        }

        dialog.show()

    }

    fun showInvalidTokenError(activity: Activity) {
        val builder =
            AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle(Constants.ERROR_ALERT_INVALID_TOKEN)
        builder.setMessage(Constants.ERROR_MESSAGE_INVALID_TOKEN)
        builder.setPositiveButton("Ok") { dialogInterface, i ->
            dialogInterface.dismiss()
            logoutFromApp(activity)
        }
        builder.show()
    }

    private fun logoutFromApp(activity: Activity) {
        val preferenceManager =
            PreferenceManager(context)
        preferenceManager.saveBoolean(
            Constants.KEY_CHECK_LOGIN,
            false
        )
        val intent = Intent(activity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
        activity.finish() // call this to finish the current activity
    }


    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }



}