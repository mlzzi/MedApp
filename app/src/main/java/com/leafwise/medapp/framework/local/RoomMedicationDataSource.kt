package com.leafwise.medapp.framework.local

import com.leafwise.medapp.data.repository.MedicationLocalDataSource
import com.leafwise.medapp.domain.model.Medication
import com.leafwise.medapp.framework.db.dao.MedicationDao
import com.leafwise.medapp.framework.db.entity.MedicationEntity
import com.leafwise.medapp.framework.db.entity.toMedicationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomMedicationDataSource @Inject constructor(
    private val medicationDao: MedicationDao
): MedicationLocalDataSource {
    override fun getAll(): Flow<List<Medication>> {
        return medicationDao.getAll().map {
            it.toMedicationModel()
        }
    }

    override suspend fun insertAll(medication: List<Medication>) {
        medicationDao.insertAll(
            medication.map {
                it.toMedicationEntity()
            }
        )
    }

    override suspend fun insertItem(medication: Medication) {
        medicationDao.insertItem(medication.toMedicationEntity())
    }

    override suspend fun delete(medication: Medication) {
        medicationDao.delete(medication.toMedicationEntity())
    }

    private fun Medication.toMedicationEntity() = MedicationEntity(name = name, type = type.ordinal, quantity = quantity)
}