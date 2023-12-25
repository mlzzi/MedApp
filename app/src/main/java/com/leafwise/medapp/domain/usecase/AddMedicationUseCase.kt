package com.leafwise.medapp.domain.usecase

import com.leafwise.medapp.data.repository.MedicationRepository
import com.leafwise.medapp.domain.model.AlarmInterval
import com.leafwise.medapp.domain.model.meds.Medication
import com.leafwise.medapp.domain.model.meds.TypeMedication
import com.leafwise.medapp.domain.usecase.base.CoroutinesDispatchers
import com.leafwise.medapp.domain.usecase.base.ResultStatus
import com.leafwise.medapp.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

interface AddMedicationUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(
        val uid: Int? = null,
        val isActive: Boolean,
        val name: String,
        val type: TypeMedication,
        val quantity: Int,
        val frequency: AlarmInterval,
        val howManyTimes: Int,
        val firstOccurrence: Calendar,
        val doses: List<Calendar>,
    )
}

class AddMedicationUseCaseImpl @Inject constructor(
    private val repository: MedicationRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<AddMedicationUseCase.Params, Unit>(), AddMedicationUseCase {

    override suspend fun doWork(params: AddMedicationUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.insertItem(
                Medication(
                    uid = 0, //TODO needs to be removed
                    isActive = params.isActive,
                    name = params.name,
                    type = params.type,
                    quantity = params.quantity,
                    frequency = params.frequency,
                    howManyTimes = params.howManyTimes,
                    firstOccurrence = params.firstOccurrence,
                    doses = params.doses,
                )
            )
            ResultStatus.Success(Unit)
        }
    }
}