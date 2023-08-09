package com.leafwise.medapp.domain.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.leafwise.medapp.R
import com.leafwise.medapp.domain.model.NotificationData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val MAX_NUM_NOTIFICATIONS = 5
private const val TARGET_ACTIVITY_NAME = "com.leafwise.medapp.MainActivity"
private const val ALARM_NOTIFICATION_REQUEST_CODE = 0
private const val ALARM_NOTIFICATION_SUMMARY_ID = 1
private const val ALARM_NOTIFICATION_CHANNEL_ID = ""
private const val ALARM_NOTIFICATION_GROUP = "ALARM_NOTIFICATIONS"
private const val DEEP_LINK_SCHEME_AND_HOST = "https://medapp.com"

@Singleton
class AppNotifier @Inject constructor(
    @ApplicationContext private val context: Context,
) : Notifier {

        override fun postAlarmNotification(notificationData: NotificationData) = with(context) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            val alarmNotification = createAlarmNotification {
                setSmallIcon(R.drawable.ic_pill)
                    .setContentTitle(notificationData.title)
                    .setContentText(notificationData.content)
                    .setContentIntent(alarmPendingIntent(notificationData))
                    .setGroup(ALARM_NOTIFICATION_GROUP)
                    .setAutoCancel(true)
            }

            // Send the notification
            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.notify(notificationData.id, alarmNotification)
        }

//    /**
//     * Creates an inbox style summary notification for alarm updates
//     */
//    private fun alarmNotificationStyle(
//        alarmResource: List<NotificationData>,
//        title: String,
//    ): NotificationCompat.InboxStyle = alarmResource
//        .fold(NotificationCompat.InboxStyle()) { inboxStyle, alarmResource ->
//            inboxStyle.addLine(newsResource.title)
//        }
//        .setBigContentTitle(title)
//        .setSummaryText(title)

}

/**
 * Creates a notification for configured for alarm updates
 */
private fun Context.createAlarmNotification(
    block: NotificationCompat.Builder.() -> Unit,
): Notification {
    ensureNotificationChannelExists()
    return NotificationCompat.Builder(
        this,
        ALARM_NOTIFICATION_CHANNEL_ID,
    )
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .apply(block)
        .build()
}

/**
 * Ensures the a notification channel is is present if applicable
 */
private fun Context.ensureNotificationChannelExists() {

    val channel = NotificationChannel(
        ALARM_NOTIFICATION_CHANNEL_ID,
        getString(R.string.alarm_notification_channel_name),
        NotificationManager.IMPORTANCE_DEFAULT,
    ).apply {
        description = getString(R.string.alarm_notification_channel_description)
    }
    // Register the channel with the system
    NotificationManagerCompat.from(this).createNotificationChannel(channel)
}

private fun Context.alarmPendingIntent(
    notificationData: NotificationData,
): PendingIntent? = PendingIntent.getActivity(
    this,
    ALARM_NOTIFICATION_REQUEST_CODE,
    Intent().apply {
        action = Intent.ACTION_VIEW
        //TODO add deep link to Med?
        data = DEEP_LINK_SCHEME_AND_HOST.toUri()
        component = ComponentName(
            packageName,
            TARGET_ACTIVITY_NAME,
        )
    },
    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
)