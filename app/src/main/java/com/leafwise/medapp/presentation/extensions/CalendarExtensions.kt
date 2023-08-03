package com.leafwise.medapp.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun Calendar.toHourFormat(): String {
    val formato12h = SimpleDateFormat("h:mm a", Locale.US)
    val formato24h = SimpleDateFormat("HH:mm", Locale("pt", "BR"))

    val currentLocale = LocalContext.current.resources.configuration.locales.get(0)

    return when(currentLocale.country){
        "BR" -> formato24h.format(time)
        else -> formato12h.format(time)
    }
}