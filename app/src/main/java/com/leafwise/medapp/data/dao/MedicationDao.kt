package com.leafwise.medapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.leafwise.medapp.data.entity.MedicationEntity

@Dao
interface MedicationDao {
    @Query("SELECT * FROM medication")
    fun getAll(): List<MedicationEntity>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg medications: MedicationEntity)

    @Delete
    fun delete(medication: MedicationEntity)
}