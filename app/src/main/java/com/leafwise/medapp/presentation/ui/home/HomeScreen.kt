package com.leafwise.medapp.presentation.ui.home

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.leafwise.medapp.R
import com.leafwise.medapp.domain.model.meds.EditMedication
import com.leafwise.medapp.domain.model.meds.Medication
import com.leafwise.medapp.domain.model.meds.toEditMedication
import com.leafwise.medapp.presentation.components.FloatingButton
import com.leafwise.medapp.presentation.components.LoadingIndicator
import com.leafwise.medapp.presentation.components.MedItem
import com.leafwise.medapp.presentation.theme.Dimens
import com.leafwise.medapp.presentation.ui.medication.AddEditMedicationScreen
import com.leafwise.medapp.presentation.ui.medication.AddEditMedicationViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Suppress("UnusedParameter")
@Composable
fun HomeScreen(
    uiState: HomeViewModel.HomeUiState,
    onAddClick: () -> Unit,
    verifyPermissions: () -> Unit,
) {

    val showBottomSheet = remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val dismissSnackBarState = rememberSwipeToDismissBoxState()
    val scope = rememberCoroutineScope()

    val addEditMedicationViewModel: AddEditMedicationViewModel = hiltViewModel()
    val sheetUiState by remember { addEditMedicationViewModel.modifyMedState }
        .collectAsStateWithLifecycle()

    if (showBottomSheet.value) {
        AddEditMedicationScreen(
            uiState = sheetUiState,
            isEdit = true,
            showBottomSheet = showBottomSheet,
            onUpdateMed = addEditMedicationViewModel::updateCurrentMed,
            onSaveMed = addEditMedicationViewModel::saveMedication,
        ) {
            scope.launch {
                snackBarHostState.showSnackbar(message = it)
            }
        }
    }

    // permission state
    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(
            android.Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        rememberPermissionState(
            android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
        )
    }

//    //Verify permission
//    LaunchedEffect(Unit) {
//        verifyPermissions()
//    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        verifyPermissions()
    }


    Scaffold(
        floatingActionButton = {
            FloatingButton(
                icon = Icons.Default.Add,
                onClick = {
                    if (!notificationPermissionState.hasPermission) {
                        notificationPermissionState.launchPermissionRequest()
                    } else {
                        addEditMedicationViewModel.updateCurrentMed(EditMedication())
                        onAddClick()
                        showBottomSheet.value = true
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                SwipeToDismiss(
                    state = dismissSnackBarState,
                    background = {},
                    dismissContent = { Snackbar(snackbarData = data) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                HomeHeader()

                when (uiState) {
                    is HomeViewModel.HomeUiState.Loading ->
                        LoadingIndicator(modifier = Modifier.fillMaxSize())

                    is HomeViewModel.HomeUiState.Success -> {
                        MedItems(
                            meds = uiState.data,
                            onEditClick = addEditMedicationViewModel::updateCurrentMed,
                            onSaveMed = addEditMedicationViewModel::saveMedication,
                            showBottomSheet = showBottomSheet,
                        )
                    }

                    is HomeViewModel.HomeUiState.Empty ->
                        EmptyView()

                    is HomeViewModel.HomeUiState.Error ->
                        ErrorView(stringResource(id = uiState.message), launcher)
                }
            }
        }
    }
}

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .padding(vertical = Dimens.MediumPadding.size),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = R.drawable.ic_pill),
            contentDescription = "appIcon",
        )

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MedItems(
    meds: List<Medication>,
    onEditClick: KFunction1<EditMedication, Unit>,
    onSaveMed: KFunction1<EditMedication, Job>,
    showBottomSheet: MutableState<Boolean>,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(meds) { med ->
            MedItem(
                item = med,
                onRemove = {},
                onEdit = {
                    onEditClick(med.toEditMedication())
                    showBottomSheet.value = true
                },
                onSwitchChange = {
                    med.toEditMedication().copy(isActive = !med.isActive).apply {
                        onSaveMed(this)
                        onEditClick(this)
                    }
                }
            )
        }

    }
}


@Composable
fun EmptyView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Text(
            text = stringResource(id = R.string.home_empty_view),
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun ErrorView(
    message: String,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Rounded.Warning,
            tint = MaterialTheme.colorScheme.error,
            contentDescription = "alertIcon",
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            TextButton(
                onClick = {
                    launcher.launch(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.home_error_button_text),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

    }
}

@Preview
@Composable
fun HomePreview(
    @PreviewParameter(HomeUiStatePreviewParameterProvider::class)
    uiState: HomeViewModel.HomeUiState,
) {
    HomeScreen(
        uiState = uiState,
        onAddClick = {},
    ) { true }
}

class HomeUiStatePreviewParameterProvider :
    PreviewParameterProvider<HomeViewModel.HomeUiState> {
    override val values: Sequence<HomeViewModel.HomeUiState>
        get() = sequenceOf(
            HomeViewModel.HomeUiState.Empty,
            HomeViewModel.HomeUiState.Loading,
//            HomeViewModel.HomeUiState.Success(
//                listOf(
//                    Medication("Name", TypeMedication.CREAM, 2),
//                    Medication("Name2", TypeMedication.AEROSOL_INHALER, 1)
//                )
//            ),
            HomeViewModel.HomeUiState.Error(R.string.home_error_button_text)
        )
}
//
//@Preview(uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun DarkHome() {
//    HomeScreen(
//        uiState = HomeViewModel.HomeUiState.Empty,
//        onAddClick = { },
//        verifyPermissions = { true }
//    )
//}
//
//@Preview
//@Composable
//fun HomeSuccess() {
//    HomeScreen(
//        HomeViewModel.HomeUiState.Success(
//            listOf(
//                Medication("Name", TypeMedication.CREAM, 2),
//                Medication("Name2", TypeMedication.AEROSOL_INHALER, 1)
//            )
//        ),
//        onAddClick = { },
//        verifyPermissions = { true }
//    )
//}
//
//@Preview
//@Composable
//fun HomeLoading() {
//    HomeScreen(
//        uiState = HomeViewModel.HomeUiState.Loading,
//        onAddClick = { },
//        verifyPermissions = { true }
//    )
//}
//
//@Preview
//@Composable
//fun HomeError() {
//    HomeScreen(
//        uiState = HomeViewModel.HomeUiState.Error(R.string.home_empty_view),
//        onAddClick = { },
//        verifyPermissions = { true }
//    )
//}