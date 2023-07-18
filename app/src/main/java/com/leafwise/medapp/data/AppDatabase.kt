package com.leafwise.medapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leafwise.medapp.data.dao.MedicationDao
import com.leafwise.medapp.data.entity.MedicationEntity

@Database(entities = [MedicationEntity::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao
}