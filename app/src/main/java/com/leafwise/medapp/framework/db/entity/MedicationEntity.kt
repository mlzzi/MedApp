package com.leafwise.medapp.framework.db.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.leafwise.medapp.R
import kotlinx.parcelize.Parcelize

@Entity(tableName = "medication")
@Parcelize
data class MedicationEntity(
    @ColumnInfo("uid")
    @PrimaryKey val uid: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("type")
    val type: TypeMedication,
    @ColumnInfo("quantity")
    val quantity: Int,
) : Parcelable

@Stable
@Parcelize
enum class TypeMedication(val index: Int, @StringRes val label: Int) : Parcelable {
    @SerializedName("1")
    PILL(1, R.string.medsheet_type_pill),
    @SerializedName("2")
    INJECTION(2, R.string.medsheet_type_injection),
    @SerializedName("3")
    SYRUP(3, R.string.medsheet_type_syrup),
    @SerializedName("4")
    SUPPOSITORY(4, R.string.medsheet_type_suppository),
    @SerializedName("5")
    CREAM(5, R.string.medsheet_type_cream),
    @SerializedName("6")
    TRANSDERMAL_PATCH(6, R.string.medsheet_type_transdermal_patch),
    @SerializedName("7")
    OPHTHALMIC_DROPS(7, R.string.medsheet_type_ophthalmic_drops),
    @SerializedName("8")
    AEROSOL_INHALER(8, R.string.medsheet_type_aerosol_inhaler),
    @SerializedName("9")
    LONG_ACTING_INJECTABLE(9, R.string.medsheet_type_long_acting_injectable),
}