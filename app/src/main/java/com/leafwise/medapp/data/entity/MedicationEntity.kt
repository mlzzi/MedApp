package com.leafwise.medapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medication")
data class MedicationEntity(
    @PrimaryKey val uid: Int,
)