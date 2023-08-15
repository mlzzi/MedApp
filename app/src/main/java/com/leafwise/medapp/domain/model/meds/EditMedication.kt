package com.leafwise.medapp.domain.model.meds

import com.leafwise.medapp.domain.model.AlarmInterval

data class EditMedication(
    val name: String = "",
    val type: TypeMedication = TypeMedication.NONE,
    val quantity: Int = 0,
    val frequency: AlarmInterval = AlarmInterval.DAILY,
    val howManyTimes: Int = 1,
    val howManyDays: Int = 1,
)
