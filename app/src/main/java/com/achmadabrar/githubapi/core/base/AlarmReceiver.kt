package com.achmadabrar.githubapi.core.base

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
import com.achmadabrar.githubapi.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TYPE_ONE_TIME = "OneTimeAlarm"
        const val TYPE_REPEATING = "RepeatingAlarm"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE ="type"

        private const val ID_ONE_TIME = 100
        private const val ID_REPEATING = 101

        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val TIME_FORMAT = "HH:mm"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        val title = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) TYPE_ONE_TIME else TYPE_REPEATING
        val notifId = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) ID_ONE_TIME else ID_REPEATING

        showToast(context, title, message)

        showAlarmNotification(context, message, title, notifId)
    }

    private fun showToast(context: Context, title: String, message: String?) {
        Toast.makeText(context, "$title: $message", Toast.LENGTH_LONG).show()
    }

    fun setOneTimeAlarm(context: Context, type: String, date: String, time: String, message: String?) {
        if (isDateInvalid(date, DATE_FORMAT) || isDateInvalid(time, TIME_FORMAT)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        Log.e("One Time", "$date $time")
        val dateArray = date.split("-").toTypedArray()
        val timeArray = time.split(":").toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[0]))
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) -1)
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]))
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, Integer.parseInt(timeArray[0]))

        val pendingIntent = PendingIntent.getBroadcast(context, ID_ONE_TIME, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(context, "One Time Alarm Set Up!", Toast.LENGTH_SHORT).show()
    }


    //validasi date and time
    private fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) ID_ONE_TIME else ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, "Repeating Alarm Dibatalkan", Toast.LENGTH_SHORT).show()
    }

    //untuk cek alarm sudah terdaftar di alarm manager atau belum
    fun isAlarmSet(context: Context, type: String): Boolean{
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) ID_ONE_TIME else ID_REPEATING

        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null
    }

    private fun showAlarmNotification(context: Context, message: String?, title: String, notifId: Int) {

        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "AlarmManager channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_access_time_black)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, R.color.toolbar_color))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    fun setRepeatingAlarm(context: Context, type: String, time: String, message: String?) {

        if (isDateInvalid(time, TIME_FORMAT)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        val putExtra = intent.putExtra(EXTRA_TYPE, type)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, "Repeating Alarm Set Up", Toast.LENGTH_SHORT).show()

    }

}
