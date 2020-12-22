package com.example.my2ndsubmission.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.example.my2ndsubmission.R
import java.util.*

class AlarmReceiver: BroadcastReceiver() {

    private val alarmID = 1
    
    
    override fun onReceive(context: Context, intent: Intent?) {
        showAlarmNotification(context)
    }

    private fun showAlarmNotification(context: Context){
        val channelID = "Channel_1"
        val channelName = "AlarmManager Channel"
        Log.d("Alarm", "OK")
        val notificationManagerCompat = context.getSystemService<NotificationManager>()
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.logo_notification)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text))
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000)
            builder.setChannelId(channelID)
            notificationManagerCompat?.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat?.notify(alarmID, notification)
    }

    fun setRepeatingAlarm(context: Context){
        val alarmManager = context.getSystemService<AlarmManager>()
        val alarmIntent = Intent(context, AlarmReceiver::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, alarmID, alarmIntent, 0)
        alarmManager?.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, context.getString(R.string.reminder_success), Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService<AlarmManager>()
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = alarmID
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager?.cancel(pendingIntent)

        Toast.makeText(context, context.getString(R.string.reminder_canceled), Toast.LENGTH_SHORT).show()
    }
}