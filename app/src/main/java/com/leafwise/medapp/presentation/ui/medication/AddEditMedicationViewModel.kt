package com.leafwise.medapp.presentation.ui.medication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leafwise.medapp.domain.model.AlarmInfo
import com.leafwise.medapp.domain.model.meds.EditMedication
import com.leafwise.medapp.domain.usecase.AddMedicationUseCase
import com.leafwise.medapp.domain.usecase.UpdateMedicationUseCase
import com.leafwise.medapp.util.AlarmUtil
import com.leafwise.medapp.util.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditMedicationViewModel @Inject constructor(
    private val alarmUtil: AlarmUtil,
    private val addMedicationUseCase: AddMedicationUseCase,
    private val updateMedicationUseCase: UpdateMedicationUseCase,
) : ViewModel() {

    init {
        Log.d("AddEditMedicationViewModel", "init")
    }

    private val _modifyMedState : MutableStateFlow<ModifyMedState> =
        MutableStateFlow(
            ModifyMedState.Data(
                med = EditMedication(),
                isEdit = false,
                canSave = false
            )
        )
    val modifyMedState: StateFlow<ModifyMedState> = _modifyMedState.asStateFlow()

    fun updateCurrentMed(med: EditMedication) {
        _modifyMedState.update {
            ModifyMedState.Data(
                isEdit = med.uid != 0,
                med = med,
                canSave = med.name.isNotEmpty()
            )
        }
    }


    fun saveMedication(medication: EditMedication) = viewModelScope.launch {
        medication.run {
            if (this.uid != 0) {
                updateMedicationUseCase.invoke(
                    UpdateMedicationUseCase.Params(
                        uid = uid,
                        isActive = isActive,
                        name = name,
                        type = type,
                        quantity = quantity,
                        frequency = frequency,
                        howManyTimes = howManyTimes,
                        firstOccurrence = firstOccurrence,
                        doses = doses,
                    )
                )
            } else {
                addMedicationUseCase.invoke(
                    AddMedicationUseCase.Params(
                        isActive = isActive,
                        name = name,
                        type = type,
                        quantity = quantity,
                        frequency = frequency,
                        howManyTimes = howManyTimes,
                        firstOccurrence = firstOccurrence,
                        doses = doses,
                    )
                )
            }
        }.watchStatus(
            loading = {
                _modifyMedState.update {
                    ModifyMedState.Loading
                }
            },
            success = {
                with(medication) {
                    if(isActive){
                        alarmUtil.scheduleExactAlarm(
                            AlarmInfo(
                                key = uid,
                                time = firstOccurrence.time.toString(),
                                title = name,
                                description = name,
                                interval = frequency,
                                firstOccurrence = firstOccurrence
                            )
                        )
                    } else {
                        alarmUtil.cancelAlarm(uid)
                    }

                    _modifyMedState.update {
                        ModifyMedState.Saved(uid != 0)
                    }
                }

            },
            error = {
                _modifyMedState.update {
                    ModifyMedState.Error(it.toString())
                }
            }
        )
    }

    sealed class MedicationUiState {
        object Initial : MedicationUiState()
        object Loading : MedicationUiState()
        object Success : MedicationUiState()
        class Error(val throwable: Throwable) : MedicationUiState()
    }

}