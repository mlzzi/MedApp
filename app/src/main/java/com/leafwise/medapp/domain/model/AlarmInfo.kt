package com.leafwise.medapp.domain.model

import android.app.AlarmManager
import android.content.Context
import com.leafwise.medapp.R
import java.util.Calendar

data class AlarmInfo(
    val key: Int,
    val time: String,
    val title: String,
    val interval: AlarmInterval,
    val firstOccurrence: Calendar
    ){
    companion object {
        const val ALARM_KEY = "ALARM_KEY"
    }
}

enum class AlarmInterval(private val intervalMillis: Long, private val displayNameResId: Int) {
    EVERY_MINUTES(AlarmManager.INTERVAL_FIFTEEN_MINUTES / 15, R.string.every_12_hours),
    EVERY_HOUR(AlarmManager.INTERVAL_HOUR, R.string.every_hour),
    EVERY_2_HOURS(AlarmManager.INTERVAL_HOUR * 2, R.string.every_2_hours),
    EVERY_3_HOURS(AlarmManager.INTERVAL_HOUR * 3, R.string.every_3_hours),
    EVERY_4_HOURS(AlarmManager.INTERVAL_HOUR * 4, R.string.every_4_hours),
    EVERY_6_HOURS(AlarmManager.INTERVAL_HOUR * 6, R.string.every_6_hours),
    EVERY_12_HOURS(AlarmManager.INTERVAL_HALF_DAY, R.string.every_12_hours),
    DAILY(AlarmManager.INTERVAL_DAY, R.string.daily),
    EVERY_2_DAYS(AlarmManager.INTERVAL_DAY * 2, R.string.every_2_days),
    WEEKLY(AlarmManager.INTERVAL_DAY * 7, R.string.weekly);

    fun getIntervalMillis(): Long = intervalMillis

    fun getDisplayName(context: Context): String = context.getString(displayNameResId)
}
