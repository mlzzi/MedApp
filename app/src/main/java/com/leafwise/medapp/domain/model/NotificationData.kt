package com.leafwise.medapp.domain.model

import java.time.Instant

data class NotificationData(
    val id: Int,
    val title: String,
    val content: String,
    val url: String,
)