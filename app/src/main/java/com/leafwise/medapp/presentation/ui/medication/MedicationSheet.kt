package com.leafwise.medapp.presentation.ui.medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leafwise.medapp.R
import com.leafwise.medapp.domain.model.meds.EditMedication
import com.leafwise.medapp.domain.model.meds.TypeMedication
import com.leafwise.medapp.presentation.components.SelectDateItem
import com.leafwise.medapp.presentation.components.SelectorItem
import com.leafwise.medapp.presentation.components.TextItem
import com.leafwise.medapp.presentation.theme.Dimens
import com.leafwise.medapp.util.extensions.ListGenerator.generateQuantityList
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationSheet(
    med: EditMedication,
    showBottomSheet: MutableState<Boolean>,
    onUpdateMed: (medication: EditMedication) -> Unit,
    onSaveMed: (medication: EditMedication) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { showBottomSheet.value = false },
        sheetState = sheetState,

        ) {

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = Dimens.MediumPadding.size)
                .fillMaxSize(),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(Dimens.SmallPadding.size),
        ) {
            // Sheet content
            item {
                Text(
                    text = stringResource(id = R.string.medsheet_title),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }

            item {
                MainInfo(med, onUpdateMed)
                Divider(modifier = Modifier.padding(vertical = Dimens.MediumPadding.size))
            }

            item {
                DateInfo()
                Divider(modifier = Modifier.padding(vertical = Dimens.MediumPadding.size))
            }

            item {
                DoseDateDetail()
            }

            item {

                Button(
                    modifier = Modifier.padding(Dimens.MediumPadding.size),
                    enabled = verifyFields(med.name),
                    onClick = {
                        onSaveMed(med)

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

}

fun verifyFields(medName: String): Boolean {
    return when {
        medName.isEmpty() -> false
        else -> true
    }
}


@Composable
private fun MainInfo(
    med: EditMedication,
    onUpdateMed: (medication: EditMedication) -> Unit,
) {
    val medName by remember(med.name) { mutableStateOf(med.name) }
    TextItem(
        label = stringResource(id = R.string.medsheet_medication),
        value = medName,
        onChange = { onUpdateMed(med.copy(name = it)) }
    )

    Spacer(modifier = Modifier.size(Dimens.SmallPadding.size))

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val medType by remember(med.type.ordinal) { derivedStateOf { med.type.ordinal } }
        SelectorItem(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.medsheet_type),
            options = TypeMedication.values().map { stringResource(id = it.label )}.toTypedArray(),
            selectedIndex = medType,
            onSelect = { onUpdateMed(med.copy(type = TypeMedication.values()[it])) }
        )

        val medQuantity by remember(med.quantity) { derivedStateOf { med.quantity } }
        SelectorItem(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.medsheet_quantity),
            options = generateQuantityList(50),
            selectedIndex = medQuantity,
            onSelect = { onUpdateMed(med.copy(quantity = it)) }
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

    Spacer(modifier = Modifier.size(Dimens.SmallPadding.size))

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

    Spacer(modifier = Modifier.size(Dimens.SmallPadding.size))

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

