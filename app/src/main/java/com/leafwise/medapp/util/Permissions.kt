package com.leafwise.medapp.util

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Permissions @Inject constructor(
    @ApplicationContext private val context: Context,
) {



    /**
     * Check if the app has notification permission.
     */
    fun hasNotificationPermission(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    /**
     * Request alarm permission.
     */
    @RequiresApi(Build.VERSION_CODES.S)
    fun requestAlarmRequestIntent() = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)

    /**
     * Check if the app has set alarm permission
     * (Manifest.permission.SCHEDULE_EXACT_ALARM)
     */
    fun hasSetAlarmPermission(): Boolean {
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarm.canScheduleExactAlarms()
        } else {
            true
        }
    }

    companion object {
        // Notification permission request code
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001
        // Alarm permission request code
        private const val ALARM_PERMISSION_REQUEST_CODE = 1002
    }

}
