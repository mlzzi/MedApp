package com.leafwise.medapp.domain.usecase

import com.leafwise.medapp.data.repository.MedicationRepository
import com.leafwise.medapp.domain.model.meds.Medication
import com.leafwise.medapp.domain.model.meds.TypeMedication
import com.leafwise.medapp.domain.usecase.base.CoroutinesDispatchers
import com.leafwise.medapp.domain.usecase.base.ResultStatus
import com.leafwise.medapp.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AddMedicationUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(
        val uid: Int? = null,
        val name: String,
        val type: TypeMedication,
        val quantity: Int,
    )
}

class AddMedicationUseCaseImpl @Inject constructor(
    private val repository: MedicationRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<AddMedicationUseCase.Params, Unit>(), AddMedicationUseCase {

    override suspend fun doWork(params: AddMedicationUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.insertItem(
                Medication(params.name, params.type, params.quantity)
            )
            ResultStatus.Success(Unit)
        }
    }
}