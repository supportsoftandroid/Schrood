package com.food.schrood.utility

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class PermissionUtilityUpdated {
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE : Int = 9883
        const val MANAGE_ALL_FILES_ACCESS_REQUEST_CODE: Int = 2296
        const val REQUEST_CODE_ASK_CAMERA_STORAGE_PERMISSIONS: Int  = 1324
        const val PERMISSION_ALL:Int = 2296
        val PERMISSIONS_ALL_LIST = getPermission()
        val LOCATION_PERMISSIONS_LIST = getLocationPermission()
        val PERMISSIONS_CAMERA_STORAGE_LIST = getCameraStoragePermission()
        fun getLocationPermission(): Array<String> {
            val permissionList = arrayListOf<String>()
            permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE)
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
            return permissionList.toTypedArray()
        }

        fun getPermission(): Array<String> {
            val permissionList = arrayListOf<String>()
            permissionList.add(Manifest.permission.INTERNET)

            permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE)
            permissionList.add(Manifest.permission.CAMERA)
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionList.add(Manifest.permission.READ_MEDIA_IMAGES)

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                permissionList.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            } else {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            // val array: Array<String> = permissionList.toTypedArray()
            return permissionList.toTypedArray()
        }

        fun getCameraStoragePermission(): Array<String> {
            val permissionList = arrayListOf<String>()

            permissionList.add(Manifest.permission.CAMERA)
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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
            val isGranted =
                hasPermissions(mContext, PERMISSIONS_CAMERA_STORAGE_LIST.toString())
            if (!isGranted) {
                requestStorageAndCameraPermission(mContext)
            }
            return isGranted
        }

        fun requestFileAccessPermission(activity: Activity) {
            if (!checkFileAccessPermission(activity)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    try {
                        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        intent.addCategory("android.intent.category.DEFAULT")
                        intent.data = Uri.parse(String.format("package:%s", activity.getPackageName()))
                        activity.startActivityForResult(intent, MANAGE_ALL_FILES_ACCESS_REQUEST_CODE)

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

        fun checkFileAccessPermission(context: Context): Boolean {
            val isGranted = true
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

        fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
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

        fun checkPermission(
            context: Context, vararg permissionsRequired: String,request_code:Int): Boolean {
            val permissionsNeeded = ArrayList<String>()

            val currentAPIVersion = Build.VERSION.SDK_INT
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
                val permissionsList = ArrayList<String>()
                for (entry in permissionsRequired) {

                    if (addPermission(context, permissionsList, entry))
                        permissionsNeeded.add(entry)
                }

                if (permissionsList.size > 0) {
                    if (permissionsNeeded.size > 0) {
                        // Need Rationale
                        val message =
                            StringBuilder("You need to grant access to " + permissionsNeeded.get(0))
                        for (i in 1 until permissionsNeeded.size)
                            message.append(", ").append(permissionsNeeded.get(i))
                        showMessageOKCancel(
                            context,
                            message.toString()
                        ) { dialog, which ->
                            ActivityCompat.requestPermissions(
                                context as Activity, permissionsList.toTypedArray(),
                                request_code
                            )
                        }
                        return false
                    }
                    ActivityCompat.requestPermissions(
                        context as Activity, permissionsList.toTypedArray(),
                        request_code
                    )
                    return false
                }

                //     insertDummyContact();
                //        Toast.makeText(context, "All Permissions are Granted", Toast.LENGTH_LONG).show();
                return true
            }
            return true
        }

        private fun addPermission(context: Context, permissionsList: MutableList<String>, permission: String): Boolean {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsList.add(permission)
                // Check for Rationale Option
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (context as Activity?)!!,
                        permission
                    )
                )
                    return true
            }
            return false
        }

        private fun showMessageOKCancel(
            context: Context,
            message: String,
            okListener: DialogInterface.OnClickListener
        ) {
            context.let {
                AlertDialog.Builder(it)
                    .setMessage(message)
                    .setPositiveButton("OK", okListener)
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show()
            }
        }


    }

}