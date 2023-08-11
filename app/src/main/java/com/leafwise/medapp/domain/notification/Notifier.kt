package com.leafwise.medapp.domain.notification

import com.leafwise.medapp.domain.model.NotificationData

/**
 * Interface for creating notifications in the app
 */
interface Notifier {
    fun postAlarmNotification(notificationData: NotificationData)
}