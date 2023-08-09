package com.leafwise.medapp.presentation.ui.medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leafwise.medapp.R
import com.leafwise.medapp.domain.model.Medication
import com.leafwise.medapp.domain.model.TypeMedication
import com.leafwise.medapp.presentation.components.SelectDateItem
import com.leafwise.medapp.presentation.components.SelectorItem
import com.leafwise.medapp.presentation.components.TextItem
import com.leafwise.medapp.util.extensions.getQuantityList
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("MagicNumber")
@Composable
fun MedicationSheet(
    showBottomSheet: MutableState<Boolean>,
    addMedication: (medication: Medication) -> Unit
) {
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { showBottomSheet.value = false },
        sheetState = sheetState,

        ) {

        var medName by rememberSaveable { mutableStateOf("") }
        var medType by rememberSaveable { mutableStateOf(TypeMedication.NONE) }
        var medQuantity by rememberSaveable { mutableIntStateOf(0) }

        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Sheet content
            Text(
                text = stringResource(id = R.string.medsheet_title),
                style = MaterialTheme.typography.headlineMedium,
            )

            InfoContent(
                medName, { medName = it },
                medType, { medType = it },
                medQuantity, { medQuantity = it },
            )

            Button(
                modifier = Modifier.padding(16.dp),
                enabled = verifyFields(medName),
                onClick = {
                    addMedication(
                        Medication(
                            name = medName,
                            type = medType,
                            quantity = medQuantity
                        )
                    )

                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet.value = false
                        }
                    }
                },
                contentPadding = ButtonDefaults.ContentPadding
            ) {

                Icon(
                    Icons.Filled.Done,
                    contentDescription = stringResource(id = R.string.save)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(id = R.string.save))
            }


        }

    }

}

fun verifyFields(medName: String): Boolean {
    return when {
        medName.isEmpty() -> false
        else -> true
    }
}

@Suppress("MagicNumber")
@Composable
fun InfoContent(
    medName: String,
    medNameChange: (name: String) -> Unit,
    medType: TypeMedication,
    medTypeChange: (type: TypeMedication) -> Unit,
    medQuantity: Int,
    medQuantityChange: (quantity: Int) -> Unit,
) {
    Column {
        MainInfo(medName,medNameChange, medType, medTypeChange, medQuantity, medQuantityChange)

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        DateInfo()

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        DoseDateDetail()
    }
}

@Composable
private fun MainInfo(
    medName: String,
    medNameChange: (name: String) -> Unit,
    medType: TypeMedication,
    medTypeChange: (type: TypeMedication) -> Unit,
    medQuantity: Int,
    medQuantityChange: (quantity: Int) -> Unit,
) {

    TextItem(
        label = stringResource(id = R.string.medsheet_medication),
        value = medName,
        onChange = { medNameChange(it) }
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SelectorItem(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.medsheet_type),
            options = TypeMedication.values().map { stringResource(id = it.label )}.toTypedArray(),
            selectedIndex = medType.ordinal,
            onSelect = { medTypeChange(TypeMedication.values()[it]) }
        )

        SelectorItem(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.medsheet_quantity),
            options = getQuantityList(),
            selectedIndex = medQuantity,
            onSelect = { medQuantityChange(it) }
        )
    }
}

@Composable
private fun DateInfo() {
    var medFrequency by rememberSaveable { mutableStateOf("") }
    var medHowManyTimes by rememberSaveable { mutableStateOf("") }
    var medHowManyDays by rememberSaveable { mutableStateOf("") }

    SelectorItem(
        label = stringResource(id = R.string.medsheet_frequency),
        options = arrayOf("every 12 hours", "daily", "weekly"),
        selectedIndex = 0,
        onSelect = { medFrequency = it.toString() }
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SelectorItem(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.medsheet_how_many_times),
            options = arrayOf("1", "2", "3", "4"),
            selectedIndex = 0,
            onSelect = { medHowManyTimes = it.toString() }
        )

        SelectorItem(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.medsheet_how_many_days),
            options = arrayOf("1", "2", "3", "4"),
            selectedIndex = 0,
            onSelect = { medHowManyDays = it.toString() }
        )
    }
}

@Composable
private fun DoseDateDetail() {
    SelectDateItem(
        label = "Dose 1",
        value = Calendar.getInstance(),
        onValueChange = {

        }
    )

    SelectDateItem(
        label = "Dose 2",
        value = Calendar.getInstance(),
        onValueChange = {}
    )


}

@Preview(showBackground = true)
@Composable
fun InfoContentPreview(){
//    InfoContent(medName, medType, medQuantity)
}

