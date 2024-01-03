package com.leafwise.medapp.presentation.ui.medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.leafwise.medapp.presentation.components.TextIconButton
import com.leafwise.medapp.presentation.components.TextItem
import com.leafwise.medapp.presentation.theme.Dimens
import com.leafwise.medapp.util.extensions.ListGenerator
import com.leafwise.medapp.util.extensions.ListGenerator.generateCalendarList
import com.leafwise.medapp.util.extensions.ListGenerator.generateWeekdaysList
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationSheet(
    med: EditMedication,
    canSave: Boolean,
    showBottomSheet: MutableState<Boolean>,
    onUpdateMed: KFunction1<EditMedication, Unit>,
    onSaveMed: KFunction1<EditMedication, Job>,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { showBottomSheet.value = false },
        sheetState = sheetState,

        ) {

        val medDoses by remember(med.doses) { derivedStateOf { med.doses } }
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
                    text = if(med.uid != 0) stringResource(id = R.string.medsheet_edit_title)
                        else stringResource(id = R.string.medsheet_add_title),
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

            doseDateDetailItems(med, medDoses, onUpdateMed)

            item {
                TextIconButton(
                    enable = canSave,
                    onClick = {
                        onSaveMed(med)
                        //TODO Below should be controlled by the answer of SaveMed
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet.value = false
                            }
                        }
                    },
                    icon = Icons.Default.Done,
                    text = stringResource(id = R.string.save)
                )

            }

        }

    }

}

@Composable
private fun MainInfo(
    med: EditMedication,
    onUpdateMed: KFunction1<EditMedication, Unit>,
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
            options = ListGenerator.NumberListBuilder(50).generateList().addDecimals().build(),
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
    onUpdateMed: KFunction1<EditMedication, Unit>,
) {

    val medHowManyTimes by remember(med.howManyTimes) { mutableIntStateOf(med.howManyTimes) }
    val medFrequency by remember(med.frequency) { mutableStateOf(med.frequency) }
    LaunchedEffect(medFrequency){
        //TODO needs to use the new updateDosesByFrequencyChange
        medFrequency.run {
            val updatedDoses = when {
                this < AlarmInterval.DAILY -> {
                    listOf(medDoses.first())
                }
                this == AlarmInterval.WEEKLY -> {
                    listOf(firstOccurrence)
                }
                else -> {
                    generateCalendarList(medHowManyTimes, medDoses.first())
                }
            }

            onUpdateMed(med.copy(doses = updatedDoses))
        }
    }
    SelectorItem(
        label = stringResource(id = R.string.medsheet_frequency),
        options = AlarmInterval.values().map { stringResource(id = it.getStringRes()) }.toTypedArray(),
        selectedIndex = medFrequency.getId(),
        onSelect = {
            val frequencySelected = AlarmInterval.values()[it]
            onUpdateMed(med.copy(
                frequency = frequencySelected,
            ))
        }
    )

    Spacer(modifier = Modifier.size(Dimens.SmallPadding.size))

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        when {
            medFrequency < AlarmInterval.DAILY -> {
                SelectDateItem(
                    label = stringResource(id = R.string.medsheet_starting_at),
                    value = if(medDoses.isNotEmpty()) medDoses.first() else Calendar.getInstance(),
                    onValueChange = {
                        onUpdateMed(med.copy(
                            doses = listOf(it))
                        )
                    }
                )
            }
            medFrequency == AlarmInterval.WEEKLY -> {
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
            }
            else -> {
                val daysList by remember { mutableStateOf( ListGenerator.NumberListBuilder(7).generateList().build()) }
                SelectorItem(
                    label = stringResource(id = R.string.medsheet_how_many_times),
                    options = daysList,
                    selectedIndex = medHowManyTimes - 1,
                    onSelect = {
                        val daysCountSelected = daysList[it].toInt()
                        daysCountSelected.run {
                            onUpdateMed(med.copy(
                                howManyTimes = this,
                                doses = generateCalendarList(this, medDoses.first())
                            ))
                        }
                    }
                )
            }
        }
    }
}


private fun LazyListScope.doseDateDetailItems(
    med: EditMedication,
    medDoses: List<Calendar>,
    onUpdateMed: KFunction1<EditMedication, Unit>
) {
    return itemsIndexed(medDoses){ index, dose ->
        SelectDateItem(
            label = stringResource(id = R.string.medsheet_dose).plus(" ").plus(index + 1),
            value = dose,
            onValueChange = {
                val dosesUpdated = medDoses.toMutableList() // Convert to mutable list
                dosesUpdated[index] = it // Update the dose at the specific index
                onUpdateMed(med.copy(
                    doses = dosesUpdated,
                    firstOccurrence = dosesUpdated.first()
                )) // Convert back to immutable list
            }
        )

        Spacer(modifier = Modifier.size(Dimens.SmallPadding.size))
    }

}

@Preview(showBackground = true)
@Composable
fun InfoContentPreview(){
//    InfoContent(medName, medType, medQuantity)
}

