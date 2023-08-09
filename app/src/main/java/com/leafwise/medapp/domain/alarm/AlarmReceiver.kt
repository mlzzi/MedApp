package com.leafwise.medapp.domain.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.leafwise.medapp.domain.model.AlarmInfo.Companion.ALARM_KEY
import com.leafwise.medapp.domain.model.AlarmInfo.Companion.TEST_ALARM
import com.leafwise.medapp.domain.model.NotificationData
import com.leafwise.medapp.domain.notification.AppNotifier
import com.leafwise.medapp.domain.notification.Notifier

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
//        val taskId = intent.getIntExtra(ALARM_KEY,  0)

        //TODO get the notification data from the database
        val test = TEST_ALARM


        //I Want to threat the notification intent here
        AppNotifier(context).postAlarmNotification(
            NotificationData(
                id = test.key,
                title = test.title,
                content = test.description,
                url = "content",
            )
        )
    }
}