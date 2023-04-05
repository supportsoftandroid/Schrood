package com.education.geno.utility


/*
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.education.geno.R
import com.education.geno.model.MessageItem
import com.education.geno.ui.activities.MainActivity
import com.education.geno.utility.StaticData.Companion.CHANNEL_ID
import com.education.geno.utility.StaticData.Companion.KEY_IS_CHAT
import com.education.geno.utility.StaticData.Companion.createNotificationChannel
import com.education.geno.utility.StaticData.Companion.printLog
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import org.json.JSONObject


class MyFirebaseMessaging : FirebaseMessagingService() {
    private lateinit var preferenceManager: PreferenceManager

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        preferenceManager = PreferenceManager(this)
        printLog("firebase message", remoteMessage.data.toString())
        if (remoteMessage.data.isNotEmpty()) {
            var type=remoteMessage.data.get("type")
           val  data:Map<String, String> = remoteMessage.data
            var json=JSONObject(data)
            printLog("firebase  ","json data==="+ json.toString())

            if (type==null){
                json=JSONObject(remoteMessage.data.get("message"))
                type=json.getString("type")
            }
            if (!type.equals("chat")) {
                json.put("message", json.getString("body"))
                json.put("title", json.getString("title"))

            }
            printLog("final json after  ","json data==="+ json.toString())
            createLocalNotification(this, json)

        }


    }

    private fun createLocalNotification(context: Context, json: JSONObject) {

        val type: String = json.getString( "type")
        setLocalBroadCast(json, type)
       if (!preferenceManager.getBoolean(KEY_IS_CHAT)!! || !type.equals("chat")) {
            val title =  json.getString("title")
            val message =  json.getString("message")
            // Create the NotificationChannel, but only on API 26+ because
           createNotificationChannel(context)
            val intent: Intent = getIntent(json,type)
           intent.putExtra("type",type)
           printLog("final intent",intent.extras.toString())
           printLog("msg type",intent.getStringExtra("type").toString())

            intent.action = "showmessage"

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
           val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
            } else {
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            val mBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_logo)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setNumber(1)

            mBuilder.setSound(uri).priority =  NotificationCompat.PRIORITY_MAX or NotificationManagerCompat.IMPORTANCE_HIGH

            val notification = mBuilder.build()
            val id = System.currentTimeMillis().toInt()
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(id, notification)
        }
    }



    fun setLocalBroadCast(data: JSONObject, type: String) {
        val intent = Intent(StaticData.FCM_BROADCAST)
        intent.putExtra("from", StaticData.FCM_BROADCAST)
        intent.putExtra("type", type)
        if (type.equals("chat")) {
            val gson = Gson()
            val chatDetails: MessageItem = gson.fromJson(data.get("body").toString(), MessageItem::class.java)
            intent.putExtra("data", chatDetails)

        }else{
            intent.putExtra("booking_id", data.get("id").toString())
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun getIntent(json: JSONObject, type: String): Intent {
        printLog("final json",json.toString())
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtra("from", StaticData.FCM_BROADCAST)
        intent.putExtra("type", type)

        if (type.equals("chat",true)){
            intent.putExtra("data", json.getString("body"))
        }else{
            intent.putExtra("booking_id", json.getString("id"))
        }

        return intent
    }



    override fun onNewToken(s: String) {
        super.onNewToken(s)
    }
}*/
