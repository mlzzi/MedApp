package com.leafwise.medapp.domain.usecase

import com.leafwise.medapp.data.repository.MedicationRepository
import com.leafwise.medapp.domain.model.meds.Medication
import com.leafwise.medapp.domain.usecase.base.CoroutinesDispatchers
import com.leafwise.medapp.domain.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetMedicationsUseCase {
    suspend operator fun invoke(params: Unit = Unit): Flow<List<Medication>>

}

class GetMedicationsUseCaseImpl @Inject constructor(
    private val repository: MedicationRepository,
    private val dispatchers: CoroutinesDispatchers
) : FlowUseCase<Unit, List<Medication>>(), GetMedicationsUseCase {


    override suspend fun createFlowObservable(params: Unit): Flow<List<Medication>> {
        return withContext(dispatchers.io()) {
            repository.getAll()
        }
    }

}