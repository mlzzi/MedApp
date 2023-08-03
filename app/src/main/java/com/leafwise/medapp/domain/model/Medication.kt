package com.leafwise.medapp.domain.model

import com.leafwise.medapp.framework.db.entity.TypeMedication

data class Medication(
    val uid: Int,
    val name: String,
    val type: TypeMedication,
    val quantity: Int,
)
