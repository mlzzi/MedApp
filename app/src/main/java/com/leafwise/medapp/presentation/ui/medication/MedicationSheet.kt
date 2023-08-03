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
import com.leafwise.medapp.presentation.components.SelectDateItem
import com.leafwise.medapp.presentation.components.SelectorItem
import com.leafwise.medapp.presentation.components.TextItem
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("MagicNumber")
@Composable
fun MedicationSheet(showBottomSheet: MutableState<Boolean>) {
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { showBottomSheet.value = false },
        sheetState = sheetState,

    ) {
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

                InfoContent()

                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
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

@Suppress("MagicNumber")
@Composable
fun InfoContent() {
    Column {
        MainInfo()

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        DateInfo()

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        DoseDateDetail()
    }
}

@Composable
private fun MainInfo() {
    var medName by rememberSaveable { mutableStateOf("") }
    var medType by rememberSaveable { mutableStateOf("Tipo") }
    var medQuantity by rememberSaveable { mutableStateOf("") }

    TextItem(
        label = stringResource(id = R.string.medsheet_medication),
        value = medName,
        onChange = { medName = it }
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SelectorItem(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.medsheet_type),
            options = arrayOf("Pill", "Drops", "Syringe"),
            selectedIndex = 0,
            onSelect = { medType = it.toString() }
        )

        SelectorItem(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.medsheet_quantity),
            options = arrayOf("1", "2", "3"),
            selectedIndex = 0,
            onSelect = { medQuantity = it.toString() }
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
    InfoContent()
}

