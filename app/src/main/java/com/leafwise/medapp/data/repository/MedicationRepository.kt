package com.leafwise.medapp.data.repository

import com.leafwise.medapp.domain.model.meds.Medication
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {

    fun getAll(): Flow<List<Medication>>

    suspend fun insertAll(medications: List<Medication>)

    suspend fun insertItem(medication: Medication)

    suspend fun delete(medication: Medication)

    suspend fun update(medication: Medication)
}