package com.leafwise.medapp.presentation.extensions

import android.content.Context
import androidx.compose.runtime.Composable
import java.util.Locale

@Composable
fun Context.getCurrentLocale(): Locale =
    resources.configuration.locales.get(0) ?: Locale.getDefault()
