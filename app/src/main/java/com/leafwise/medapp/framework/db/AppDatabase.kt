package com.leafwise.medapp.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leafwise.medapp.framework.db.dao.MedicationDao
import com.leafwise.medapp.framework.db.entity.MedicationEntity
import com.leafwise.medapp.framework.db.utils.Converters

@Database(
    entities = [MedicationEntity::class],
    version = 1,
    //When first migration is needed, set exportSchema to true
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao
}