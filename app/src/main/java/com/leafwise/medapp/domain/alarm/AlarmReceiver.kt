package com.leafwise.medapp.domain.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.leafwise.medapp.data.repository.MedicationRepository
import com.leafwise.medapp.domain.model.AlarmInfo.Companion.ALARM_KEY
import com.leafwise.medapp.domain.model.AlarmInfo.Companion.TEST_ALARM
import com.leafwise.medapp.domain.model.NotificationData
import com.leafwise.medapp.domain.model.meds.Medication.Companion.toAlarmInfo
import com.leafwise.medapp.domain.model.meds.Medication.Companion.updateDosesByFrequency
import com.leafwise.medapp.domain.notification.AppNotifier
import com.leafwise.medapp.domain.usecase.base.CoroutinesDispatchers
import com.leafwise.medapp.util.AlarmUtil
import com.leafwise.medapp.util.extensions.formatToHumanReadable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var repository: MedicationRepository

    @Inject
    lateinit var coroutinesDispatchers: CoroutinesDispatchers

    @Inject
    lateinit var alarmUtil: AlarmUtil

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra(ALARM_KEY,  0)
        Log.d("AlarmReceiver", "onReceive")

        //TODO get the notification data from the database
        val test = TEST_ALARM

        CoroutineScope(coroutinesDispatchers.io()).launch {
            repository.getItem(taskId).firstOrNull()?.let {
                val nextTriggerDate = it.firstOccurrence.clone() as Calendar
                nextTriggerDate.add(Calendar.MILLISECOND, it.frequency.getIntervalMillis().toInt())
                val updatedMed = it.copy(
                    firstOccurrence = nextTriggerDate,
                    doses = it.updateDosesByFrequency()
                )
                repository.update(updatedMed)
                alarmUtil.scheduleExactAlarm(updatedMed.toAlarmInfo())
                Log.d("AlarmReceiver", "updatedMed: $updatedMed")
                updatedMed.doses.forEach { dose ->
                    dose.formatToHumanReadable().also { doseFormatted ->
                        Log.d("AlarmReceiver", doseFormatted)
                    }
                }
            }
        }

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