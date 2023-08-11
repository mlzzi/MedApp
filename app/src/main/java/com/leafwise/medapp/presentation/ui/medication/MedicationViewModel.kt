package com.leafwise.medapp.presentation.ui.medication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leafwise.medapp.domain.model.Medication
import com.leafwise.medapp.domain.usecase.AddMedicationUseCase
import com.leafwise.medapp.util.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val addMedicationUseCase: AddMedicationUseCase
) : ViewModel() {

    private val _medicationUiState = MutableStateFlow<MedicationUiState>(MedicationUiState.Initial)
    val medicationUiState: StateFlow<MedicationUiState> = _medicationUiState.asStateFlow()

    fun addMedication(medication: Medication) = viewModelScope.launch {
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
                _medicationUiState.value = MedicationUiState.Loading
            },
            success = {
                _medicationUiState.value = MedicationUiState.Success
            },
            error = {
                _medicationUiState.value = MedicationUiState.Error(it)
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