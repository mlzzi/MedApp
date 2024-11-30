package com.leafwise.medapp.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.leafwise.medapp.domain.alarm.AlarmReceiver
import com.leafwise.medapp.domain.model.AlarmInfo
import com.leafwise.medapp.domain.model.AlarmInfo.Companion.ALARM_ACTION
import com.leafwise.medapp.domain.model.AlarmInfo.Companion.ALARM_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject



class AlarmManagement @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleExactAlarm(alarmInfo: AlarmInfo) {
        val calendar = alarmInfo.firstOccurrence

        Log.d("AlarmUtil", "setExactAndAllowIdle: ${calendar.timeInMillis}")
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            context.makeAlarmPendingIntent(alarmInfo.key)
        )
//        Log.d("AlarmUtil", "setRepeating: ${calendar.timeInMillis}")
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            alarmInfo.interval.getIntervalMillis(),
//            context.makeAlarmPendingIntent(alarmInfo.key)
//        )
    }

    //TODO
    fun cancelAlarm(alarmKey: Int){
        Log.d("AlarmUtil", "cancelAlarm: $alarmKey")
        alarmManager.cancel(context.makeAlarmPendingIntent(alarmKey))
    }

    //TODO
//    fun rescheduleAlarm(){
//
//    }

    private fun Context.makeAlarmPendingIntent(alarmKey: Int): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = ALARM_ACTION
            putExtra(ALARM_KEY, alarmKey)
        }
        return PendingIntent.getBroadcast(
            this,
            alarmKey,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    fun canScheduleExactAlarm() = Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()

}
