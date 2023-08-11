package com.leafwise.medapp.framework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.leafwise.medapp.framework.db.entity.MedicationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {
    @Query("SELECT * FROM medication")
    fun getAll(): Flow<List<MedicationEntity>>

    @Insert
    fun insertAll(medications: List<MedicationEntity>)

    @Insert
    fun insertItem(medication: MedicationEntity)

    @Delete
    fun delete(medication: MedicationEntity)
}