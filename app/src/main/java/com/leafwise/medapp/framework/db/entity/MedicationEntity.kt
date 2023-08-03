package com.leafwise.medapp.framework.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "medication")
@Parcelize
data class MedicationEntity(
    @ColumnInfo("uid")
    @PrimaryKey val uid: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("type")
    val type: Int,
    @ColumnInfo("quantity")
    val quantity: Int,
) : Parcelable