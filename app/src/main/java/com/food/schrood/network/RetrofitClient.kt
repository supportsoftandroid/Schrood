package com.food.schrood.network


import com.food.schrood.BuildConfig
import com.food.schrood.utility.Constants
import com.food.schrood.utility.StaticData.Companion.printLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import okhttp3.logging.HttpLoggingInterceptor.Logger
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val RETROFIT_LOGGER = "Result"
    private const val INVALID_SESSION = "{" + "message" + ":" + "Unauthenticated." + "}"
    val result = "{\"message\":\"Unauthenticated.\"}"
    private const val connectionTimeOUT: Long = 60

    val retrofitClient: Retrofit.Builder by lazy {
        val levelType: Level
        if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            levelType = Level.BODY else levelType = Level.NONE
        val logging = HttpLoggingInterceptor(object : Logger {

            override fun log(message: String) {
                if (BuildConfig.DEBUG) {
                    printLog(RETROFIT_LOGGER, "---->>>" + message)
                }
                if (message.equals(INVALID_SESSION)) {
                    printLog("INVALID_SESSION", "---->>>" + true)

                }
            }
        })
        logging.setLevel(levelType)
        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(logging)
        okhttpClient.callTimeout(connectionTimeOUT, TimeUnit.SECONDS)
        okhttpClient.readTimeout(connectionTimeOUT, TimeUnit.SECONDS)
        okhttpClient.writeTimeout(connectionTimeOUT, TimeUnit.SECONDS)
        Retrofit.Builder()
            .baseUrl(Constants.LIVE_URL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }
}
