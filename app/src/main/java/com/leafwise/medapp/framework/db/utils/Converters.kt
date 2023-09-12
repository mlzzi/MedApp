package com.leafwise.medapp.framework.db.utils

import androidx.compose.animation.scaleOut
import androidx.room.TypeConverter
import java.util.Calendar

//Should separate if adds different converters to readability
class Converters {
    @TypeConverter
    fun calendarToLong(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }

    @TypeConverter
    fun longToCalendar(value: Long?): Calendar? {
        return value?.let {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            calendar
        }
    }

    @TypeConverter
    fun calendarListToString(calendarList: List<Calendar>?): String? {
        return calendarList?.joinToString(separator = ",") { it.timeInMillis.toString() }
    }

    @TypeConverter
    fun stringToCalendarList(value: String?): List<Calendar>? {
        return value?.split(",")?.mapNotNull { timeInMillisString ->
            val timeInMillis = timeInMillisString.toLongOrNull()
            timeInMillis?.let { time ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = time
                calendar
            }
        }
    }
}