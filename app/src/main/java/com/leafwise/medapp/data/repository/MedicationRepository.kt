package com.leafwise.medapp.data.repository

import com.leafwise.medapp.domain.model.Medication
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {

    fun getAll(): Flow<List<Medication>>

    suspend fun insertAll(medication: List<Medication>)

    suspend fun insertItem(medication: Medication)

    suspend fun delete(medication: Medication)

}