package com.leafwise.medapp.domain.model

import android.app.AlarmManager
import com.leafwise.medapp.R
import java.util.Calendar

data class AlarmInfo(
    val key: Int,
    val time: String,
    val title: String,
    val description : String,
    val interval: AlarmInterval,
    val firstOccurrence: Calendar
    ){
    companion object {
        const val ALARM_KEY = "ALARM_KEY"
        const val ALARM_ACTION = "com.leafwise.medapp.domain.alarm.AlarmReceiver"

        val TEST_ALARM = AlarmInfo(
            key = 1,
            time = "12:00",
            title = "Dorflex",
            description = "Take your medication",
            interval = AlarmInterval.EVERY_HOUR,
            firstOccurrence = Calendar.getInstance().apply {
                add(Calendar.SECOND, 20)
            }
        )
    }
}


@Suppress("MagicNumber")
enum class AlarmInterval(private val id: Int, private val intervalMillis: Long, private val resId: Int) {
    ERROR_INTERVAL(-1, AlarmManager.INTERVAL_DAY, R.string.some_unknown_error),
    EVERY_MINUTE(0, AlarmManager.INTERVAL_FIFTEEN_MINUTES / 15, R.string.every_minute),
    EVERY_HOUR(1, AlarmManager.INTERVAL_HOUR, R.string.every_hour),
    EVERY_2_HOURS(2, AlarmManager.INTERVAL_HOUR * 2, R.string.every_2_hours),
    EVERY_3_HOURS(3, AlarmManager.INTERVAL_HOUR * 3, R.string.every_3_hours),
    EVERY_4_HOURS(4, AlarmManager.INTERVAL_HOUR * 4, R.string.every_4_hours),
    EVERY_6_HOURS(5, AlarmManager.INTERVAL_HOUR * 6, R.string.every_6_hours),
    EVERY_12_HOURS(6, AlarmManager.INTERVAL_HALF_DAY, R.string.every_12_hours),
    DAILY(7, AlarmManager.INTERVAL_DAY, R.string.daily),
    EVERY_2_DAYS(8, AlarmManager.INTERVAL_DAY * 2, R.string.every_2_days),
    WEEKLY(9, AlarmManager.INTERVAL_DAY * 7, R.string.weekly);

    companion object {
        private val map = values().associateBy(AlarmInterval::id)
        fun fromId(typeId: Int): AlarmInterval = map[typeId] ?: ERROR_INTERVAL
    }

    fun getId(): Int = id

    fun getIntervalMillis(): Long = intervalMillis

    fun getStringRes(): Int = resId
}