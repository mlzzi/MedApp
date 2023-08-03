package com.leafwise.medapp.domain.model

import androidx.annotation.StringRes
import com.leafwise.medapp.R

data class Medication(
    val uid: Int,
    val name: String,
    val type: TypeMedication,
    val quantity: Int,
)


enum class TypeMedication(@StringRes val label: Int) {
    PILL(R.string.medsheet_type_pill),
    INJECTION(R.string.medsheet_type_injection),
    SYRUP(R.string.medsheet_type_syrup),
    SUPPOSITORY(R.string.medsheet_type_suppository),
    CREAM(R.string.medsheet_type_cream),
    TRANSDERMAL_PATCH(R.string.medsheet_type_transdermal_patch),
    OPHTHALMIC_DROPS(R.string.medsheet_type_ophthalmic_drops),
    AEROSOL_INHALER(R.string.medsheet_type_aerosol_inhaler),
    LONG_ACTING_INJECTABLE(R.string.medsheet_type_long_acting_injectable),
}
