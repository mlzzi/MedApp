package com.leafwise.medapp.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leafwise.medapp.domain.model.AlarmInterval
import com.leafwise.medapp.domain.model.meds.Medication
import com.leafwise.medapp.domain.model.meds.TypeMedication
import java.util.Calendar

@Entity(tableName = "medication")
data class MedicationEntity(
    @ColumnInfo("uid")
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo("isActive")
    val isActive: Boolean,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("type")
    val type: Int,
    @ColumnInfo("quantity")
    val quantity: Int,
    @ColumnInfo("frequency")
    val frequency: Int,
    @ColumnInfo("howManyTimes")
    val howManyTimes: Int,
    @ColumnInfo("firstOccurrence")
    val firstOccurrence: Calendar,
    @ColumnInfo("doses")
    val doses: List<Calendar>,
)

fun List<MedicationEntity>.toMedicationModel() = map {
    Medication(
        uid = it.uid,
        isActive = it.isActive,
        name = it.name,
        type = TypeMedication.values()[it.type],
        quantity = it.quantity,
        frequency = AlarmInterval.values()[it.frequency],
        howManyTimes = it.howManyTimes,
        firstOccurrence = it.firstOccurrence,
        doses = it.doses
    )
}