package com.leafwise.medapp.domain.model.meds

import androidx.annotation.StringRes
import com.leafwise.medapp.R
import com.leafwise.medapp.domain.model.AlarmInfo
import com.leafwise.medapp.domain.model.AlarmInterval
import com.leafwise.medapp.util.extensions.ListGenerator
import java.util.Calendar

data class Medication(
    val uid: Int,
    val isActive: Boolean,
    val name: String,
    val type: TypeMedication,
    val quantity: Int,
    val frequency: AlarmInterval,
    val howManyTimes: Int,
    val firstOccurrence: Calendar,
    val doses: List<Calendar>,
) {
    companion object {
        fun Medication.toAlarmInfo(): AlarmInfo {
            return AlarmInfo(
                key = uid,
                time = firstOccurrence.time.toString(),
                title = name,
                description = name,
                interval = frequency,
                firstOccurrence = firstOccurrence,
            )
        }

        fun Medication.updateDosesByFrequency() : List<Calendar>{
            frequency.run {
                val updatedDoses = mutableListOf<Calendar>()
                for (dose in doses){
                    val nextTriggerDate = dose.clone() as Calendar
                    nextTriggerDate.add(Calendar.MILLISECOND, frequency.getIntervalMillis().toInt())
                    updatedDoses.add(nextTriggerDate)
                }

                return updatedDoses
            }
        }

    }

}

fun Medication.toEditMedication() = EditMedication(
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


enum class TypeMedication(@StringRes val label: Int) {
    NONE(R.string.medsheet_type_none),
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
