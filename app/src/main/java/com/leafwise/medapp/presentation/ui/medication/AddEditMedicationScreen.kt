package com.leafwise.medapp.presentation.ui.medication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.leafwise.medapp.R
import com.leafwise.medapp.domain.model.meds.EditMedication
import com.leafwise.medapp.presentation.components.LoadingIndicator
import kotlinx.coroutines.Job
import kotlin.reflect.KFunction1

@Suppress("UnusedParameter")
@Composable
fun AddEditMedicationScreen(
    uiState: ModifyMedState,
    isEdit: Boolean,
    //TODO Rethink this name and action method
    showBottomSheet: MutableState<Boolean>,
    onUpdateMed: KFunction1<EditMedication, Unit>,
    onSaveMed: KFunction1<EditMedication, Job>,
    onSaveMedication: (message: String) -> Unit
) {

    when (uiState) {
        is ModifyMedState.Data -> {
            MedicationSheet(
                med = uiState.med,
                canSave = uiState.canSave,
                showBottomSheet = showBottomSheet,
                onUpdateMed = onUpdateMed,
                onSaveMed = onSaveMed,
            )

        }
        ModifyMedState.Loading -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }
        ModifyMedState.Saved -> {
            onSaveMedication(stringResource(id = R.string.medication_added))
        }
        is ModifyMedState.Error -> {
            onSaveMedication(uiState.error)
        }

        else -> {}
    }


}