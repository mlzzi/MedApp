package com.leafwise.medapp.domain.model.meds

data class EditMedication(
    val name: String = "",
    val type: TypeMedication = TypeMedication.NONE,
    val quantity: Int = 0,
)
