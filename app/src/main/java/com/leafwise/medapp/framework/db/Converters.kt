package com.leafwise.medapp.framework.db

import androidx.room.TypeConverter
import com.leafwise.medapp.framework.db.entity.TypeMedication

object Converters {

    @TypeConverter
    fun fromTypeMedicationToInt(medication: TypeMedication) = medication.index

}