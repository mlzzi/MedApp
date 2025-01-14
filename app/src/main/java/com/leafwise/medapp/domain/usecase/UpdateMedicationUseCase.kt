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

interface UpdateMedicationUseCase {
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
class UpdateMedicationUseCaseImpl @Inject constructor(
    private val repository: MedicationRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<UpdateMedicationUseCase.Params, Unit>(), UpdateMedicationUseCase {

    override suspend fun doWork(params: UpdateMedicationUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.update(
                Medication(
                    uid = params.uid ?: 0,
                    isActive = params.isActive,
                    name = params.name,
                    type = params.type,
                    quantity = params.quantity,
                    frequency = params.frequency,
                    howManyTimes = params.howManyTimes,
                    lastOccurrence = params.firstOccurrence,
                    doses = params.doses,
                )
            )
            ResultStatus.Success(Unit)
        }
    }
}