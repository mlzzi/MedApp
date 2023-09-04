package com.leafwise.medapp.presentation.ui.medication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leafwise.medapp.R
import com.leafwise.medapp.domain.model.meds.ModifyMedState
import com.leafwise.medapp.presentation.components.LoadingIndicator
@Suppress("UnusedParameter")
@Composable
fun AddEditMedicationScreen(
    isEdit: Boolean,
    showBottomSheet: MutableState<Boolean>,
    //TODO Rethink this name and action method
    onSaveMedication: (message: String) -> Unit
) {
    val viewModel: AddEditMedicationViewModel = hiltViewModel()
    val uiState = viewModel.modifyMedState.collectAsStateWithLifecycle()

    val modifyMedState by remember { derivedStateOf { uiState.value } }

    when (val state = modifyMedState) {
        is ModifyMedState.Data -> {
            MedicationSheet(
                med = state.med,
                canSave = state.canSave,
                showBottomSheet = showBottomSheet,
                onUpdateMed = viewModel::updateCurrentMed,
                onSaveMed = viewModel::addMedication,
            )

        }
        ModifyMedState.Loading -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }
        ModifyMedState.Saved -> {
            onSaveMedication(stringResource(id = R.string.medication_added))
        }
        is ModifyMedState.Error -> {
            onSaveMedication(state.error)
        }
    }


}