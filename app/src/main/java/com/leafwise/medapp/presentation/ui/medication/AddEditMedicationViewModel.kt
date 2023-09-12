package com.leafwise.medapp.presentation.ui.medication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leafwise.medapp.domain.model.meds.AddEditMedicationState
import com.leafwise.medapp.domain.model.meds.EditMedication
import com.leafwise.medapp.domain.model.meds.Medication
import com.leafwise.medapp.domain.model.meds.ModifyMedState
import com.leafwise.medapp.domain.model.meds.TypeMedication
import com.leafwise.medapp.domain.usecase.AddMedicationUseCase
import com.leafwise.medapp.util.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditMedicationViewModel @Inject constructor(
    private val addMedicationUseCase: AddMedicationUseCase
) : ViewModel() {

    private val _modifyMedState : MutableStateFlow<ModifyMedState> =
        MutableStateFlow(ModifyMedState.Data(
            med = EditMedication(),
            isEdit = false,
            canSave = false
        ))
    val modifyMedState: StateFlow<ModifyMedState> = _modifyMedState.asStateFlow()

    fun updateCurrentMed(med: EditMedication) {
        _modifyMedState.update {
            ModifyMedState.Data(
                isEdit = false,
                med = med,
                canSave = med.name.isNotEmpty()
            )
        }
    }


    fun addMedication(medication: EditMedication) = viewModelScope.launch {
        medication.run {
            addMedicationUseCase.invoke(
                AddMedicationUseCase.Params(
                    name = name,
                    type = type,
                    quantity = quantity
                )
            )
        }.watchStatus(
            loading = {
                _modifyMedState.update {
                    ModifyMedState.Loading
                }
            },
            success = {
                _modifyMedState.update {
                    ModifyMedState.Saved
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