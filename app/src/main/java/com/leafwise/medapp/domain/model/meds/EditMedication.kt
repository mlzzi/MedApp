package com.leafwise.medapp.domain.model.meds

import com.leafwise.medapp.domain.model.AlarmInterval
import java.util.Calendar

data class EditMedication(
    val isActive: Boolean = true,
    val name: String = "",
    val type: TypeMedication = TypeMedication.NONE,
    val quantity: Int = 1,
    val frequency: AlarmInterval = AlarmInterval.DAILY,
    val howManyTimes: Int = 1,
    val firstOccurrence: Calendar = Calendar.getInstance(),
    val finalTriggerDate: Calendar = Calendar.getInstance(),
    val doses: List<Calendar> = listOf(Calendar.getInstance())
)
