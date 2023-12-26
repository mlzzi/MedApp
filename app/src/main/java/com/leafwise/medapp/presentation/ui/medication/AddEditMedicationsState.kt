package com.leafwise.medapp.presentation.ui.medication

import com.leafwise.medapp.domain.model.meds.EditMedication
import com.leafwise.medapp.domain.model.meds.Medication

// Testable state
//sealed class AddEditMedicationsState {
//    data class Data(
//        val med: EditMedication? = null
//    ) : AddEditMedicationsState()
//
//    object MedSaved : AddEditMedicationsState()
//
//    object Loading : AddEditMedicationsState()
//}

data class AddEditMedicationState(
    val modifyMedState: ModifyMedState = ModifyMedState.Data(
        med = EditMedication(),
        isEdit = false,
        canSave = false,
    ),
    val availableMeds: List<Medication> = emptyList()
)

sealed class ModifyMedState {

    data class Data(
        val med: EditMedication,
        val isEdit: Boolean,
        val canSave: Boolean
    ) : ModifyMedState()


    data class Saved(
        val isEdit: Boolean
    ) : ModifyMedState()

    object Loading : ModifyMedState()

    data class Error(val error: String) : ModifyMedState()
}
