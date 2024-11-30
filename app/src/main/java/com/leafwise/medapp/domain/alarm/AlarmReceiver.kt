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
import com.leafwise.medapp.util.AlarmManagement
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
    lateinit var alarmManagement: AlarmManagement

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra(ALARM_KEY,  0)
        Log.d("AlarmReceiver", "onReceive")

        //TODO get the notification data from the database
        val test = TEST_ALARM

        CoroutineScope(coroutinesDispatchers.io()).launch {
            repository.getItem(taskId).firstOrNull()?.let {

                val now = Calendar.getInstance()

                //If the first occurrence is before now, it means that we need to update the doses
                // and the last occurrence
                if (it.lastOccurrence.before(now)) {
                    Log.d("AlarmReceiver", "firstOccurrence is before now")
                    val updatedMed = it.copy(
                        lastOccurrence = now,
                        doses = it.updateDosesByFrequency()
                    )
                    repository.update(updatedMed)

                } else {
                    //I Want to threat the notification intent here
                    AppNotifier(context).postAlarmNotification(
                        NotificationData(
                            id = test.key,
                            title = test.title,
                            content = test.description,
                            url = "content",
                        )
                    )
                    val nextTriggerDate = it.lastOccurrence.clone() as Calendar
                    nextTriggerDate.add(Calendar.MILLISECOND, it.frequency.getIntervalMillis().toInt())
                    val updatedMed = it.copy(
                        lastOccurrence = nextTriggerDate,
                        doses = it.updateDosesByFrequency()
                    )
                    repository.update(updatedMed)
                    alarmManagement.scheduleExactAlarm(updatedMed.toAlarmInfo())
                    Log.d("AlarmReceiver", "updatedMed: $updatedMed")
                    updatedMed.doses.forEach { dose ->
                        dose.formatToHumanReadable().also { doseFormatted ->
                            Log.d("AlarmReceiver", doseFormatted)
                        }
                    }
                }

            }
        }


    }
}