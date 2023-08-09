package com.leafwise.medapp.presentation.ui.medication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leafwise.medapp.R
import com.leafwise.medapp.presentation.components.LoadingIndicator

@Composable
fun AddMedicationScreen(
    showBottomSheet: MutableState<Boolean>,
    onSaveMedication: (message: String) -> Unit
) {
    val viewModel: MedicationViewModel = hiltViewModel()
    val stateAddMedication by remember { viewModel.medicationUiState }.
    collectAsStateWithLifecycle()


    when (val state = stateAddMedication) {
        MedicationViewModel.MedicationUiState.Initial -> {
            MedicationSheet(
                showBottomSheet = showBottomSheet,
                addMedication = {
                    viewModel.addMedication(it)
                }
            )
        }
        MedicationViewModel.MedicationUiState.Loading -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }
        MedicationViewModel.MedicationUiState.Success -> {
            onSaveMedication(stringResource(id = R.string.medication_added))
        }
        is MedicationViewModel.MedicationUiState.Error -> {
            onSaveMedication(
                state.throwable.message ?: stringResource(id = R.string.some_unknown_error)
            )
        }
    }
}