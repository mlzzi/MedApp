@file:OptIn(ExperimentalMaterial3Api::class)

package com.leafwise.medapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.leafwise.medapp.R
import java.time.LocalDate

@Composable
fun SelectorItem (
    modifier: Modifier = Modifier,
    label: String,
    options: Array<out Any?>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = { Text(label) },
            value = options[selectedIndex].toString(),
            onValueChange = {},
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option.toString()) },
                    onClick = {
                        onSelect(index)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TextItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onChange: (String) -> Unit,
){
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true
    )
}

@Suppress("MagicNumber")
@Composable
fun SelectDataItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {

    val datePickerDialog = remember { mutableStateOf(false) }
    val timePicker = remember { mutableStateOf("") }

    val date =
        LocalDate.now()

    val year = date.year
    val monthValue = date.monthValue
    val dayOfMonth = date.dayOfMonth


    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
            datePickerDialog.value = it.isFocused
        },
        value = value,
        onValueChange = {
            onValueChange(LocalDate.of(year, monthValue, dayOfMonth).toString())
        },
        label = { Text(text = label) },
        readOnly = true,
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_hour_picker),
                contentDescription = "Hour icon",
                modifier = Modifier.clickable {
                    datePickerDialog.value = true
                }
            )
        },
    )

    if (datePickerDialog.value)
        TimePickerDialog(
            onDismissRequest = { datePickerDialog.value = false },
            onTimeChanged = {
                timePicker.value = it.toString()
            },
            selectedTime = TimeSelection(5, 55),
            confirmLabel = "Agendar",
            title = "Dose 1"
        )


}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeChanged: (TimeSelection) -> Unit,
    selectedTime: TimeSelection,
    confirmLabel: String,
    title: String,
) {
    val timePickerState = rememberTimePickerState(selectedTime.hour, selectedTime.minute)

    LaunchedEffect(timePickerState) {
        val timeSelection = TimeSelection(timePickerState.hour, timePickerState.minute)
        snapshotFlow { timeSelection }
            .collect { onTimeChanged(it) }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(text = confirmLabel)
            }
        },
        title = { Text(text = title) },
        text = {
            TimePicker(state = timePickerState)
        },
    )
}

@Preview(showBackground = true)
@Composable
fun SelectorItemPreview(){
    SelectorItem(
        label = "Quantity",
        options = arrayOf("1", "2", "3", "4"),
        selectedIndex = 0,
        onSelect = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TextItemPreview(){
    TextItem(
        label = "Text",
        onChange = {},
        value = "Hello World"
    )
}

@Preview(
    showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun SelectDataItemPreview(){
    SelectDataItem(
        label = "Dose 1",
        value = "4:00 PM",
        onValueChange = {}
    )
}