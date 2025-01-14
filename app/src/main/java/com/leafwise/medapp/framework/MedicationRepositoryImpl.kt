package com.leafwise.medapp.framework

import com.leafwise.medapp.data.repository.MedicationLocalDataSource
import com.leafwise.medapp.data.repository.MedicationRepository
import com.leafwise.medapp.domain.model.meds.Medication
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MedicationRepositoryImpl @Inject constructor(
    private val medicationLocalDataSource: MedicationLocalDataSource
): MedicationRepository {
    override fun getItem(uid: Int): Flow<Medication> {
        return medicationLocalDataSource.getItem(uid)
    }

    override fun getAll(): Flow<List<Medication>> {
        return medicationLocalDataSource.getAll()
    }

    override suspend fun insertAll(medications: List<Medication>) {
        return medicationLocalDataSource.insertAll(medications)
    }

    override suspend fun insertItem(medication: Medication) {
        return medicationLocalDataSource.insertItem(medication)
    }

    override suspend fun update(medication: Medication) {
        return medicationLocalDataSource.update(medication)
    }

    override suspend fun delete(medication: Medication) {
        return medicationLocalDataSource.delete(medication)
    }
}