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
    val nextOccurrence = this.clone() as Calendar

    // Add the frequency interval (in milliseconds) to the first occurrence
    nextOccurrence.timeInMillis += frequency.getIntervalMillis()

    return nextOccurrence
}

fun Calendar.formatToHumanReadable(): String {
    val currentTime = Calendar.getInstance()
    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DAY_OF_MONTH, 1)

    val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    val timeString = dateFormat.format(this.time)

    return when {
        this.before(currentTime) -> "Today $timeString"
        this.before(tomorrow) -> "Tomorrow $timeString"
        else -> SimpleDateFormat("MMMM dd, yyyy h:mm a", Locale.getDefault()).format(this.time)
    }
}
