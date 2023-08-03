package com.leafwise.medapp.util.extensions

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