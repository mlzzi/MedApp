package com.leafwise.medapp.presentation.ui.medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leafwise.medapp.R
import com.leafwise.medapp.domain.model.AlarmInterval
import com.leafwise.medapp.domain.model.meds.EditMedication
import com.leafwise.medapp.domain.model.meds.TypeMedication
import com.leafwise.medapp.presentation.components.SelectDateItem
import com.leafwise.medapp.presentation.components.SelectorItem
import com.leafwise.medapp.presentation.components.TextItem
import com.leafwise.medapp.presentation.theme.Dimens
import com.leafwise.medapp.util.extensions.ListGenerator.generateQuantityList
import com.leafwise.medapp.util.extensions.ListGenerator.generateWeekdaysList
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

        val medDoses by remember(med.doses) { mutableStateOf(med.doses) }
        val firstOccurrence by remember(med.firstOccurrence) { mutableStateOf(med.firstOccurrence) }

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
                DateInfo(med, medDoses, firstOccurrence, onUpdateMed)
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
private fun DateInfo(
    med: EditMedication,
    medDoses: List<Calendar>,
    firstOccurrence: Calendar,
    onUpdateMed: (medication: EditMedication) -> Unit,
) {


    val medFrequency by remember(med.frequency) { mutableStateOf(med.frequency) }
    SelectorItem(
        label = stringResource(id = R.string.medsheet_frequency),
        options = AlarmInterval.values().map { stringResource(id = it.getStringRes()) }.toTypedArray(),
        selectedIndex = medFrequency.ordinal,
        onSelect = { onUpdateMed(med.copy(frequency = AlarmInterval.values()[it])) }
    )

    Spacer(modifier = Modifier.size(Dimens.SmallPadding.size))



    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val medHowManyTimes by remember(med.howManyTimes) { mutableIntStateOf(med.howManyTimes) }

        if(medFrequency < AlarmInterval.DAILY){
            SelectDateItem(
                label = stringResource(id = R.string.medsheet_starting_at),
                value = if(medDoses.isNotEmpty()) medDoses.first() else Calendar.getInstance(),
                onValueChange = {
                    onUpdateMed(med.copy(doses = listOf(it)))
                }
            )
        } else if (medFrequency == AlarmInterval.WEEKLY){
            var dayOfWeek by rememberSaveable { mutableIntStateOf(0) }

            SelectorItem(
                label = stringResource(id = R.string.medsheet_day_of_week),
                options = LocalContext.current.generateWeekdaysList().toTypedArray(),
                selectedIndex = dayOfWeek,
                onSelect = {
                    dayOfWeek = it
                    val updatedDate = firstOccurrence.clone() as Calendar
                    updatedDate.set(Calendar.DAY_OF_WEEK, dayOfWeek)
                    onUpdateMed(med.copy(firstOccurrence = updatedDate))
                }
            )
        } else {
            SelectorItem(
                label = stringResource(id = R.string.medsheet_how_many_times),
                options = generateQuantityList(7),
                selectedIndex = medHowManyTimes,
                onSelect = { onUpdateMed(med.copy(howManyTimes = it)) }
            )
        }


//        val medFinalTriggerDate by remember(med.finalTriggerDate) { mutableStateOf(med.finalTriggerDate) }
//        SelectorItem(
//            modifier = Modifier.weight(1f),
//            label = stringResource(id = R.string.medsheet_how_many_days),
//            options = arrayOf("1", "2", "3", "4"),
//            selectedIndex = medFinalTriggerDate,
//            onSelect = { onUpdateMed(med.copy(finalTriggerDate = it)) }
//        )
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

