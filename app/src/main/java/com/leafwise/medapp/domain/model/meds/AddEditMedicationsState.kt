package com.leafwise.medapp.domain.model.meds

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


    object Saved : ModifyMedState()

    object Loading : ModifyMedState()

    data class Error(val error: String) : ModifyMedState()
}
