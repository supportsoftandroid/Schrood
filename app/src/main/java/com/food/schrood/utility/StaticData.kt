package com.food.schrood.utility


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.*
import android.location.Address
import android.location.Geocoder
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.food.schrood.BuildConfig
import com.food.schrood.R
import com.food.schrood.ui.activities.WelcomeActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class StaticData {

    companion object   {
        const val MILLIS_PER_DAY = 24 * 60 * 60 * 1000L
        val msgDateFormat="dd-MMM-yyyy HH:mm:ss a"
        const val FCM_BROADCAST: String = "FCM_BROADCAST"
        const val KEY_IS_CHAT = "is_chat"
        const val CHANNEL_ID  ="schrood"
        const  val CHANNEL_NAME = "schrood Channel"
        const  val CHANNEL_DESCRIPTION = "schrood Channel Description"
        const val PERMISSION_REQUEST_CODE = 133
        const val CAMERA_REQUEST_CODE = 201
        const val GALLARY_REQUEST_CODE = 202
        const val IMAGE_CROP_REQUEST_CODE = 277
        const val AUTOCOMPLETE_REQUEST_CODE = 653
        const val LOCATION_MAP_ADD_REQUEST_CODE = 9883
        const val MANAGE_ALL_FILES_ACCESS_REQUEST_CODE: Int = 2296
        const val REQUEST_CODE_ASK_CAMERA_STORAGE_PERMISSIONS = 1324
        const val PERMISSION_ALL = 2296
        val PERMISSIONSList = getPermission()
        val PERMISSIONS_CAMERA_STORAGE_LIST = getCameraStoragePermission()
      /*  val  regex =("^[+]{1}(?:[0-9\\-\\(\\)\\/"
        "\\.]\\s?){6, 15}[0-9]{1}$");*/
        fun getTimeZoneId(): String {
            val tz = TimeZone.getDefault()
            return tz.id

        }

        fun getPermission(): Array<String> {
            var permissionList = arrayListOf<String>()
            permissionList.add(Manifest.permission.INTERNET)

            permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE)
            permissionList.add(Manifest.permission.CAMERA)
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
            if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionList.add(Manifest.permission.READ_MEDIA_IMAGES)

            } else if (SDK_INT >= Build.VERSION_CODES.R) {
                permissionList.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            } else {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            // val array: Array<String> = permissionList.toTypedArray()
            return permissionList.toTypedArray()
        }

        fun getCameraStoragePermission(): Array<String> {
            var permissionList = arrayListOf<String>()

            permissionList.add(Manifest.permission.CAMERA)
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionList.add(Manifest.permission.READ_MEDIA_IMAGES)

            }
           /* else if (SDK_INT >= Build.VERSION_CODES.R) {
                permissionList.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            } else {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }*/
            // val array: Array<String> = permissionList.toTypedArray()
            return permissionList.toTypedArray()
        }


        fun checkStorageAndCameraPermission(mContext: Context?): Boolean {
            val isGranted = hasPermissions(mContext, PERMISSIONS_CAMERA_STORAGE_LIST.toString())
            if (!isGranted) {
                requestStorageAndCameraPermission(mContext)
            }
            return isGranted
        }

        fun checkFileAccessPermission(context: Context): Boolean {
            var isGranted = true
            checkStorageAndCameraPermission(context)
           /* if (SDK_INT >= Build.VERSION_CODES.R) {
                isGranted = Environment.isExternalStorageManager()
                if (isGranted) {
                    checkStorageAndCameraPermission(context)
                }
            } else {
                isGranted = checkStorageAndCameraPermission(context)
            }*/
            return isGranted
        }

       fun changeStatusBarColor(context: Context,from:String){
           // Set the status bar color
           val window=(context as Activity).window
           var colorValue=ContextCompat.getColor(context, R.color.app_color)
           when (from){
               "home"->{
                   colorValue=ContextCompat.getColor(context, R.color.app_color)
               }
               "message"->{
                   colorValue=ContextCompat.getColor(context, R.color.colorLightGray)
               }
           }
         //  window.decorView(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

           window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            window.statusBarColor =colorValue


           // Set the SYSTEM_UI_FLAG_LAYOUT_STABLE and SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN flags
        //   window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN



       }



        fun requestFileAccessPermission(activity: Activity) {
            if (!checkFileAccessPermission(activity)) {
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    try {
                        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        intent.addCategory("android.intent.category.DEFAULT")
                        intent.data =
                            Uri.parse(String.format("package:%s", activity.getPackageName()))
                        activity.startActivityForResult(
                            intent,
                            MANAGE_ALL_FILES_ACCESS_REQUEST_CODE
                        )

                    } catch (e: java.lang.Exception) {
                        requestStorageAndCameraPermission(activity)
                        val intent = Intent()
                        intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                        activity.startActivityForResult(
                            intent, MANAGE_ALL_FILES_ACCESS_REQUEST_CODE
                        )
                    }
                } else {
                    //below android 11
                    requestStorageAndCameraPermission(activity)
                }
            } else {
                requestStorageAndCameraPermission(activity)
            }
        }

        fun requestStorageAndCameraPermission(mContext: Context?) {
            ActivityCompat.requestPermissions(
                (mContext as Activity?)!!, PERMISSIONS_CAMERA_STORAGE_LIST,
                REQUEST_CODE_ASK_CAMERA_STORAGE_PERMISSIONS
            )
        }

        fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
            if (SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (permission in permissions) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            permission!!
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return false
                    }
                }
            }
            return true
        }
      /* fun getChatMessageDate(): StartupTime {
          val date = now()
           return date
       }*/


        fun getCurrentTimestamp(): String{
            return SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a",Locale.US).format(Calendar.getInstance().time)
        }
        fun getTimeFromDate(inpDate: String): String{
            if (TextUtils.isEmpty(inpDate)) {
                return ""
            }

            val inputFormat = SimpleDateFormat(msgDateFormat,Locale.US)
            var output = SimpleDateFormat("HH:mm a",Locale.US)

            val sdf = SimpleDateFormat(msgDateFormat,Locale.US)
            val old = sdf.parse(inpDate)
            val current = sdf.parse(SimpleDateFormat(msgDateFormat,Locale.US).format(Calendar.getInstance().time).toString())

            val moreThanDay: Boolean = Math.abs(current.time - old.time) > MILLIS_PER_DAY
            if (moreThanDay){
                output = SimpleDateFormat("dd-MMM-yyyy",Locale.US)
            }

            println(moreThanDay)

            var d: Date? = inputFormat.parse(inpDate)
            val formatted: String = output.format(d)
            return formatted

        }
        fun getMessageCurrentDate(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.US)
            val currentDate = sdf.format(Date())
            return currentDate

        } fun changeLastChatDate(inputDate:String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.US)
            val output = SimpleDateFormat("HH:mm a")
            var d: Date? = inputFormat.parse(inputDate)
            val formatted: String = output.format(d)
            /*  Log.i("DATE", "" + formatted)*/
            return formatted

        }
       /* fun searchGoogleAddress(context: Context) {
            val fields = Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS_COMPONENTS
            )
            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .setTypeFilter(TypeFilter.ADDRESS)
                 .setCountry(Constants.COUNTRY_NAME)
                .build(context)
            (context as Activity).startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }
*/
        fun getURLForResource(resourceId: Int): String {
            //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
            return Uri.parse("android.resource://" + R::class.java.getPackage().name + "/" + resourceId)
                .toString()
        }

        fun takeNewPicture(activity: Activity): Uri {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val values = ContentValues(3)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            val cameraImagePath = activity.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )!!
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImagePath)
            activity.startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
            return cameraImagePath
        }
        fun createNotificationChannel(context: Context) {
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
                channel.description = CHANNEL_DESCRIPTION
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)

            }
        }
        fun pickImageFromGallary(activity: Activity) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activity.startActivityForResult(intent, GALLARY_REQUEST_CODE)

        }

        fun getCurrentDayOfWeek(): Int {
            val calendar = Calendar.getInstance()
            var day = calendar[Calendar.DAY_OF_WEEK] - 1

            /*  when (day) {
                  Calendar.SUNDAY -> {}
                  Calendar.MONDAY -> {}
                  Calendar.TUESDAY -> {}
              }*/
            return day

        }
        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("dd-MM-yyyy",Locale.US)
            val currentDate = sdf.format(Date())
          printLog("currentDate",currentDate)
            return currentDate

        }
        fun toCamelCase(str: String): String {
            val parts = str.split("_", " ", "-")
            val camelCaseString = StringBuilder()
            for (part in parts) {
                if (part.isNotEmpty()) {
                    camelCaseString.append(part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase())
                }
            }
            return camelCaseString.toString()
        }
        fun getCamelCaseString(inputText:String): String {
            var text=inputText
            if (!TextUtils.isEmpty(text.trim())) {
                val parts = inputText.split("_","-")
                val camelCaseString = StringBuilder()
                for (part in parts) {
                    if (part.isNotEmpty()) {
                        camelCaseString.append(part.substring(0, 1).toUpperCase() + part.substring(1))
                    }
                }
                text = camelCaseString.toString()

              return  text
                }else{

                return  ""
                }

        }
        fun getGreetingDate(context: Context): String {
            val date = Date()
            val cal = Calendar.getInstance()
            cal.time = date
            val hour = cal[Calendar.HOUR_OF_DAY]
            //Set greeting
            var greeting: String =  context.getString(R.string.good_morning)
            if (hour >= 6 && hour < 12) {
                greeting = context.getString(R.string.good_morning)
            } else if (hour >= 12 && hour < 17) {
                greeting = context.getString(R.string.good_afternoon)
            } else if (hour >= 17 && hour < 21) {
                greeting = context.getString(R.string.good_evening)
            } else if (hour >= 21 && hour < 24) {
                greeting =  context.getString(R.string.good_night)
            }
            return greeting

        }

        fun getCurrentTimeZone(): String {
            val tz = TimeZone.getDefault()
            val timezoneId = tz.id
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault())
            val currentLocalTime = calendar.time
            val date: DateFormat = SimpleDateFormat("Z", Locale.getDefault())
            return date.format(currentLocalTime)
        }

        fun prepareFilePartFromUri(partName: String, filePath: String): MultipartBody.Part? {
            var imgFileBody: MultipartBody.Part? = null

            if (filePath != "") {
                val file = File(filePath)
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                imgFileBody = MultipartBody.Part.createFormData(partName, file.name, requestFile)
            }
            return imgFileBody
        }

        fun createFileMultiPart(
            file: File,
            fileName: String,
            fileType: String
        ): MultipartBody.Part {
            val reqFile: RequestBody = file.asRequestBody(fileType.toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(
                fileName, file.getName(), reqFile
            )
        }

        fun createRequestBody(parameter: String): RequestBody {
            return parameter.toRequestBody("text/plain".toMediaTypeOrNull())
        }
        // defining our own password pattern

        fun getAndroidDeviceId(context: Context): String {
            val android_id =
                Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
            return android_id
        }// defining our own password pattern


        private val PASSWORD_PATTERN = Pattern.compile(
            "^" +
                    "(?=.*[@#$%^&+=])" +  // at least 1 special character
                    "(?=\\S+$)" +  // no white spaces
                    "(?=\\S+$)" +  // no white spaces
                    ".{8,}" +  // at least 8 characters
                    "$"
        )

        fun printLog(str: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.e(str + "=====>", message)
            }
        }

        fun askIsNotificationPermission(context: Context): Boolean {
            // This is only necessary for API level >= 33 (TIRAMISU)
            var isGranted = false
            if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    // FCM SDK (and your app) can post notifications.
                    isGranted = true
                } else if (shouldShowRequestPermissionRationale(
                        context as Activity,
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                ) {
                    isGranted = true
                    /*   TODO: display an educational UI explaining to the user the features that will be enabled
                             by them granting the POST_NOTIFICATION permission. This UI should provide the user
                             "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                             If the user selects "No thanks," allow the user to continue without notifications.*/
                } else {
                    // Directly ask for the permission


                }

            }
            return isGranted;
        }

        fun loadImage(imageView: ImageView, imageURL: String) {
            /*    Glide.with(imageView.getContext())
                    .setDefaultRequestOptions(  RequestOptions()
                        .circleCrop())
                    .load(imageURL)
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.ic_logo)
                    .into(imageView)*/
        }


        fun passWordEditText(context: Context, isVisiblePassword: Boolean, edPassword: EditText) {
            var isVisible = isVisiblePassword
            edPassword.setOnTouchListener(View.OnTouchListener { v, event ->
                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= edPassword.getRight() - edPassword.getCompoundDrawables()
                            .get(DRAWABLE_RIGHT).getBounds().width()
                    ) {
                        //  edPassword.clearFocus()
                        if (isVisible) {
                            isVisible = false
                            edPassword.transformationMethod = PasswordTransformationMethod()
                            edPassword.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                R.drawable.ic_password_visibility,
                                0
                            )
                        } else {
                            isVisible = true
                            edPassword.transformationMethod = null
                            edPassword.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                R.drawable.ic_password_visibility_off,
                                0
                            )
                        }
                        // your action here

                        return@OnTouchListener true
                    }
                }
                false
            })

        }

        fun isValidPassword(password: String): Boolean {
            if (password.length < 8) return false
            if (password.filter { it.isDigit() }.firstOrNull() == null) return false
            if (password.filter { it.isLetter() }.filter { it.isUpperCase() }
                    .firstOrNull() == null) return false
            if (password.filter { it.isLetter() }.filter { it.isLowerCase() }
                    .firstOrNull() == null) return false
            if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

            return true
        }

        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun fragmentSetResult(context: Context, newFragment: Fragment?) {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.replace(R.id.frame, newFragment!!, newFragment.tag)
            fragmentTransaction.commit()
        }

        fun replaceFragment(context: Context, newFragment: Fragment?) {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.replace(R.id.frame, newFragment!!, newFragment.tag)
            fragmentTransaction.commit()
        }

        fun addFragment(context: Context, newFragment: Fragment?) {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.add(R.id.frame, newFragment!!, newFragment.tag)
            fragmentTransaction.commit()
        }

        fun backStackReplaceFragment(context: Context, newFragment: Fragment?) {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.replace(R.id.frame, newFragment!!, newFragment.tag)
                .addToBackStack(newFragment.tag)
            fragmentTransaction.commitAllowingStateLoss()
        }

        fun backStackAddFragment(context: Context, newFragment: Fragment?) {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.add(R.id.frame, newFragment!!, newFragment.tag)
                .addToBackStack(newFragment.tag)
            fragmentTransaction.commitAllowingStateLoss()
        }

        fun <T : Serializable?> getSerializable(
            activity: Activity,
            name: String,
            clazz: Class<T>
        ): T {
            return if (SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                activity.intent.getSerializableExtra(name, clazz)!!
            else
                activity.intent.getSerializableExtra(name) as T
        }

        @SuppressLint("SimpleDateFormat")
        public fun getWeekDayMonthTime(date: String?): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")//2022-11-25 05:26:45
            var testDate: Date? = null
            try {
                testDate = date?.let { sdf.parse(it) }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            val formatter = SimpleDateFormat("EEEE dd MMM | HH:mm", Locale.getDefault())
            return testDate?.let { formatter.format(it) }.toString()
        }

        public fun getTimeOpeningHrs(date: String?): String {
            val sdf = SimpleDateFormat("hh:mm aaa", Locale.getDefault())
            var testDate: Date? = null
            try {
                testDate = date?.let { sdf.parse(it) }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            return testDate?.let { formatter.format(it) }.toString()
        }

        fun logoutFromApp(activity: Activity) {
         //  FirebaseMessaging.getInstance().deleteToken()
            val preferenceManager = PreferenceManager(activity)
            preferenceManager.saveBoolean(
                Constants.KEY_CHECK_LOGIN,
                false
            )
            val intent = Intent(activity, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)
            activity.finish()
        }
        fun isStrongPassword(password: String): Boolean {
           /* val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
            return password.matches(regex)*/
           /* val PASSWORD_PATTERN = Pattern.compile(
                "^" +
                        "(?=.*[@#$%^&+=])" +  // at least 1 special character
                        "(?=\\S+$)" +  // no white spaces
                        ".{8,}" +  // at least 8 characters
                        "$"
            )*/
            if (password.length < 8) return false
            if (password.filter { it.isDigit() }.firstOrNull() == null) return false
            if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
            if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
            if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

            return true
        }
        fun getBusinessHrsTimeOpeningHrs(date: String?): String {
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            var testDate: Date? = null
            try {
                testDate = date?.let { sdf.parse(it) }
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
            val formatter = SimpleDateFormat("hh:mm aaa", Locale.getDefault())
            return testDate?.let { formatter.format(it) }.toString()
        }


        fun dateChangeRattingInDDMMYYYY(inpuStrDate: String): String {
            val inputPattern = "yyyy-mm-dd hh:mm:ss"
            val outputPattern = "dd MMM yyyy"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
            val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())

            val date: Date?
            var str: String? = ""

            try {
                date = inputFormat.parse(inpuStrDate)
                str = date?.let { outputFormat.format(it) }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str!!
        }
        fun dateChangeInDDMMYYYY(inpuStrDate: String): String {
         //   val inputPattern = "dd-MM-yyyy"
            val inputPattern = "yyyy-MM-dd"
            val outputPattern = "EEE,dd MMM yyyy"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
            val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())

            val date: Date?
            var str: String? = ""

            try {
                date = inputFormat.parse(inpuStrDate)
                str = date?.let { outputFormat.format(it) }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str!!
        }

        fun dateChangeInWebFormat(inpuStrDate: String): String {


            val inputPattern = "dd-MM-yyyy"
            val outputPattern = "yyyy-MM-dd"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
            val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())

            var date: Date? = null
            var str: String? = ""

            try {
                date = inputFormat.parse(inpuStrDate)
                str = date?.let { outputFormat.format(it) }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            printLog("web formatter", str.toString())
            return str.toString()

        }
        fun dateChangeDateDay_Date(inpuStrDate: String): String {

            printLog("inpuStrDate", inpuStrDate )
            val inputPattern = "dd-MM-yyyy"
            val outputPattern = "EEE,dd-MMM-yyyy"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
            val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())

            var date: Date? = null
            var str: String? = ""

            try {
                date = inputFormat.parse(inpuStrDate)
                str = date?.let { outputFormat.format(it) }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            printLog("day of week formatter", str.toString())
            return str.toString()

        }

        fun getAge(dobString: String): String {
            var date: Date? = null
            val sdf = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
            try {
                date = sdf.parse(dobString)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            if (date == null) return ""
            val dob = Calendar.getInstance()
            val today = Calendar.getInstance()
            dob.time = date
            val year = dob[Calendar.YEAR]
            val month = dob[Calendar.MONTH]
            val day = dob[Calendar.DAY_OF_MONTH]
            dob[year, month + 1] = day
            var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
            if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
                age--
            }
            return age.toString()
        }

        @SuppressLint("SuspiciousIndentation")
        fun getAddressFromLatLan(
            context: Context,
            latitude: Double,
            longitude: Double
        ): MutableList<Address>? {
            var addresses = mutableListOf<Address>()
            val geocoder = Geocoder(context, Locale.getDefault())

            if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1,object : Geocoder.GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            setAddressFrom(context,addresses)
                        }
                    }
                )
            } else {
                addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1,
                ) as MutableList<Address>
                setAddressFrom(context,addresses)
            }



            return addresses
        }

        fun setAddressFrom(context: Context, addresses: MutableList<Address>) {
            if (addresses.size > 0) {
              val  address = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                val houseNo = addresses[0].featureName
                val thoroughfare = addresses[0].thoroughfare
                val landmark = addresses[0].subLocality
                val city = addresses[0].locality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val zipcodeFromMap = addresses[0].postalCode

                var locality = houseNo + "," + landmark + "," + city

                printLog("setAddressFrom latitude", addresses[0].latitude.toString())
                printLog("setAddressFrom longitude", addresses[0].longitude.toString())
                printLog("setAddressFrom knownName", houseNo.toString())
                printLog("setAddressFrom thoroughfare", thoroughfare + "")
                printLog("setAddressFrom landmark", landmark + "")
                printLog("setAddressFrom city", city + "")
                printLog("setAddressFrom state", state + "")
                printLog("setAddressFrom locality", locality + "")
                printLog("setAddressFrom address", address + "")

                val intent = Intent()
                intent.putExtra("landmark", landmark)
                intent.putExtra("state", state)
                intent.putExtra("country", country)
                intent.putExtra("address", address)
                intent.putExtra("suburb", city)
                intent.putExtra("zipcode", zipcodeFromMap)
                intent.putExtra("latitude", addresses[0].latitude)
                intent.putExtra("longitude", addresses[0].longitude)
                (context as Activity).setResult(LOCATION_MAP_ADD_REQUEST_CODE, intent)

            }
        }

        fun compressImage(context: Context, imageUri: String): String {
            val filePath = getRealPathFromURI(context, imageUri)
            var scaledBitmap: Bitmap? = null
            val options = BitmapFactory.Options()
            //      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
            options.inJustDecodeBounds = true
            var bmp = BitmapFactory.decodeFile(filePath, options)
            var actualHeight = options.outHeight
            var actualWidth = options.outWidth
            //      max Height and width values of the compressed image is taken as 816x612
            val maxHeight = 816.0f
            val maxWidth = 612.0f
            var imgRatio = actualWidth / actualHeight.toFloat()
            val maxRatio = maxWidth / maxHeight
            //      width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight
                    actualWidth = (imgRatio * actualWidth).toInt()
                    actualHeight = maxHeight.toInt()
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth
                    actualHeight = (imgRatio * actualHeight).toInt()
                    actualWidth = maxWidth.toInt()
                } else {
                    actualHeight = maxHeight.toInt()
                    actualWidth = maxWidth.toInt()
                }
            }
            //      setting inSampleSize value allows to load a scaled down version of the original image
            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
            //      inJustDecodeBounds set to false to load the actual bitmap
            options.inJustDecodeBounds = false
            //      this options allow android to claim the bitmap memory if it runs low on memory
            options.inPurgeable = true
            options.inInputShareable = true
            options.inTempStorage = ByteArray(16 * 1024)
            try { //          load the bitmap from its path
                bmp = BitmapFactory.decodeFile(filePath, options)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            try {
                scaledBitmap =
                    Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            val ratioX = actualWidth / options.outWidth.toFloat()
            val ratioY = actualHeight / options.outHeight.toFloat()
            val middleX = actualWidth / 2.0f
            val middleY = actualHeight / 2.0f
            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
            val canvas = Canvas(scaledBitmap!!)
            canvas.setMatrix(scaleMatrix)
            canvas.drawBitmap(
                bmp,
                middleX - bmp.width / 2,
                middleY - bmp.height / 2,
                Paint(Paint.FILTER_BITMAP_FLAG)
            )
            //      check the rotation of the image and display it properly
            val exif: ExifInterface
            try {
                exif = ExifInterface(filePath!!)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0
                )
                printLog("EXIF", "Exif: $orientation")
                val matrix = Matrix()
                if (orientation == 6) {
                    matrix.postRotate(90f)
                    printLog("EXIF", "Exif: $orientation")
                } else if (orientation == 3) {
                    matrix.postRotate(180f)
                    printLog("EXIF", "Exif: $orientation")
                } else if (orientation == 8) {
                    matrix.postRotate(270f)
                    printLog("EXIF", "Exif: $orientation")
                }
                scaledBitmap = Bitmap.createBitmap(
                    scaledBitmap, 0, 0,
                    scaledBitmap.width, scaledBitmap.height, matrix,
                    true
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(imageUri)
                //          write the compressed bitmap at the destination specified by filename.
                scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return imageUri
        }


        private fun getRealPathFromURI(context: Context, contentURI: String): String? {
            val contentUri = Uri.parse(contentURI)
            val cursor: Cursor? =
                context.getContentResolver().query(contentUri, null, null, null, null)
            return if (cursor == null) {
                contentUri.path
            } else {
                cursor.moveToFirst()
                val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                cursor.getString(index)
            }
        }

        fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val heightRatio =
                    Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio =
                    Math.round(width.toFloat() / reqWidth.toFloat())
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            val totalPixels = width * height.toFloat()
            val totalReqPixelsCap = reqWidth * reqHeight * 2.toFloat()
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }
            return inSampleSize
        }

    /*    fun searchPlace(activity: Activity) {
            val fields = Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS_COMPONENTS
            )
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .setTypeFilter(TypeFilter.ADDRESS)
                 .setCountry(Constants.COUNTRY_NAME)
                .build(activity)
            activity.startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }*/



        fun InvalidSession(context: Context, message: String) {
            showToast(context,message)
            logoutFromApp(context as Activity)

        }

        fun setDateAndMonth(inputDate: String, tvDate: TextView, tvMonth: TextView) {
           // printLog("inputDate",inputDate)
            val inputPattern = "dd-MM-yyyy"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
            val date: Date? = inputFormat.parse(inputDate)
            val day_of_month = SimpleDateFormat("dd").format(date)
            val month = SimpleDateFormat("MMM").format(date)
            tvDate.setText(day_of_month)
            tvMonth.setText(month)

        }



    }
}