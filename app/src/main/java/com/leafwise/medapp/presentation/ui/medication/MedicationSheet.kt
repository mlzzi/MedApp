package com.leafwise.medapp.presentation.ui.medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leafwise.medapp.R
import com.leafwise.medapp.presentation.components.Selector
import kotlinx.coroutines.launch

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
                Text(stringResource(id = R.string.dummy_text))
                // Sheet content
                InfoContent()

                Button(
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

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("MagicNumber")
@Composable
fun InfoContent() {
    var medName by remember{ mutableStateOf("") }
    var medType by remember{ mutableStateOf("Tipo") }
    //var medQuantity by remember{ mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = medName,
        onValueChange = { medName = it },
        label = { Text(stringResource(id = R.string.medsheet_medication)) },
    )


    Selector(
        label = stringResource(id = R.string.medsheet_type),
        options = arrayOf("Tipo", "Comprimido", "Gotas", "Seringa"),
        selectedIndex = 0,
        onSelect = { medType = it.toString() }
    )

    Selector(
        label = stringResource(id = R.string.medsheet_quantity),
        options = arrayOf("Quantidade", "1", "2", "3"),
        selectedIndex = 0,
        onSelect = { medType = it.toString() }
    )



}
