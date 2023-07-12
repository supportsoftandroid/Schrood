package com.food.schrood.utility

import android.content.Context
import android.content.SharedPreferences
import com.food.schrood.model.LocationData
import com.food.schrood.model.LoginResponse
import com.food.schrood.utility.Constants.KEY_ACCESS_TOKEN
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


import java.lang.reflect.Type

class PreferenceManager(context: Context) {
    private val editor: SharedPreferences.Editor


    private var sharedPreferences: SharedPreferences
    fun initPreferenceManager(context: Context): SharedPreferences {
        sharedPreferences = context.getSharedPreferences(
            Constants.SHARED_PREFERENCE_FILE_NAME,
            Context.MODE_PRIVATE
        )
        return sharedPreferences
    }

    fun getLoginData(): LoginResponse? {
        val json: String = sharedPreferences.getString(Constants.KEY_LOGIN_DATA, null).toString()
        val gson = Gson()
        val type: Type = object : TypeToken<LoginResponse>() {}.getType()
        val beans: LoginResponse = try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            return null
        }
        return beans
    }

    fun setLoginData(beans: LoginResponse?) {
        val gson = Gson()
        val json: String = gson.toJson(beans)
        if (beans?.data?.token!=null&&beans?.data?.type!=null){
            editor.putString(
                Constants.KEY_ACCESS_TOKEN,
                "${beans?.data?.type} ${beans?.data?.token}"
            )
        }
        editor.putString(Constants.KEY_LOGIN_DATA, json)
        editor.apply()
    }


    fun setLocationData(beans: LocationData) {
        val gson = Gson()
        val json: String = gson.toJson(beans)
        editor.putString(Constants.KEY_LOCATION_DATA, json)
        editor.apply()
    }
    fun getLocationData(): LocationData? {
        val json: String = sharedPreferences.getString(Constants.KEY_LOCATION_DATA, null).toString()
        val gson = Gson()
        val type: Type = object : TypeToken<LocationData>() {}.getType()
        val beans: LocationData = try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            return null
        }
        return beans
    }

    fun saveString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    fun saveAuthToken(value: String?) {
        editor.putString(KEY_ACCESS_TOKEN, value)
        editor.apply()
    }

    fun getAuthToken(): String {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, "").toString()
    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, "").toString()
    }

    fun saveBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearPreferences() {
        editor.clear()
        editor.apply()
    }

    fun saveInt(keyTrainerId: String?, trainerId: Int?) {
        editor.putInt(keyTrainerId, trainerId!!)
        editor.apply()
    }

    init {
        sharedPreferences = initPreferenceManager(context)
        editor = sharedPreferences.edit()
        editor.apply()


//
//        sharedPreferences = context.getSharedPreferences(
//            Constants.SHARED_PREFERENCE_FILE_NAME,
//            Context.MODE_PRIVATE
//        )
//
//
//        editor = sharedPreferences.edit()
//        editor.apply()
    }


}