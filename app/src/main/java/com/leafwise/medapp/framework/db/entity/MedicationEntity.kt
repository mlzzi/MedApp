package com.leafwise.medapp.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leafwise.medapp.domain.model.meds.Medication
import com.leafwise.medapp.domain.model.meds.TypeMedication

@Entity(tableName = "medication")
data class MedicationEntity(
    @ColumnInfo("uid")
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("type")
    val type: Int,
    @ColumnInfo("quantity")
    val quantity: Int,
)

fun List<MedicationEntity>.toMedicationModel() = map {
    Medication(it.name, TypeMedication.values()[it.type], it.quantity)
}