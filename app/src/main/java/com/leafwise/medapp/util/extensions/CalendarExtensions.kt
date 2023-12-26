package com.leafwise.medapp.util.extensions

import com.leafwise.medapp.domain.model.AlarmInterval
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar.toHourFormat(locale: Locale): String {
    val usHourFormat = SimpleDateFormat("h:mm a", Locale.US)
    val brazilHourFormat = SimpleDateFormat("HH:mm", Locale("pt", "BR"))

    return when(locale.language){
        "pt" -> brazilHourFormat.format(time)
        else -> usHourFormat.format(time)
    }
}

fun Calendar.calculateNextOccurrence(frequency: AlarmInterval): Calendar {
    val currentTime = Calendar.getInstance()
    val nextOccurrence = this.clone() as Calendar

    // Check if the event time has already passed for today
    if (nextOccurrence.before(currentTime)) {
        // If the event time has passed, add the frequency interval to move to the next occurrence
        nextOccurrence.timeInMillis += frequency.getIntervalMillis()
    }
    // If the event time hasn't passed yet, it will keep the event scheduled for today

    return nextOccurrence
}

fun Calendar.formatToHumanReadable(): String {
    val currentTime = Calendar.getInstance()
    val tomorrow = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_MONTH, 1)
    }

    val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    val timeString = dateFormat.format(this.time)

    fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }

    return when {
        isSameDay(this, currentTime) -> "Today $timeString"
        isSameDay(this, tomorrow) -> "Tomorrow $timeString"
        else -> SimpleDateFormat("MMMM dd, yyyy h:mm a", Locale.getDefault()).format(this.time)
    }
}
