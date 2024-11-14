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

fun MedicationEntity.toMedicationModel(): Medication {
    return Medication(
        uid = this.uid,
        isActive = this.isActive,
        name = this.name,
        type = TypeMedication.values()[this.type],
        quantity = this.quantity,
        frequency = AlarmInterval.fromId(this.frequency),
        howManyTimes = this.howManyTimes,
        lastOccurrence = this.firstOccurrence,
        doses = this.doses
    )
}

fun List<MedicationEntity>.toMedicationModelList() = map { it.toMedicationModel() }