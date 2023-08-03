package com.leafwise.medapp.presentation.ui.home

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leafwise.medapp.R
import com.leafwise.medapp.framework.db.entity.MedicationEntity
import com.leafwise.medapp.presentation.components.LoadingIndicator
import com.leafwise.medapp.presentation.components.MedItem
import com.leafwise.medapp.presentation.components.MedAddButton
import com.leafwise.medapp.presentation.ui.medication.MedicationSheet


@Suppress("UnusedParameter")
@Composable
fun HomeScreen(
    uiState: HomeViewModel.HomeUiState,
    onAddClick: () -> Unit,
    verifyPermissions: () -> Unit,
    ) {

    val showBottomSheet = remember { mutableStateOf(false) }

    //Verify permission
    LaunchedEffect(Unit){
        verifyPermissions()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()) {
        verifyPermissions()
    }


    Scaffold(
        floatingActionButton = {
            MedAddButton {
                showBottomSheet.value = true
            }
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {

                HomeHeader()
                HomeSheet(showBottomSheet)
                when (uiState) {
                    is HomeViewModel.HomeUiState.Loading -> {
                        LoadingIndicator(modifier = Modifier.fillMaxSize())
                    }

                    is HomeViewModel.HomeUiState.Success -> {
                        HomeContent(listOf())
                    }

                    is HomeViewModel.HomeUiState.Empty -> {
                        EmptyView()
                    }

                    is HomeViewModel.HomeUiState.Error -> {
                        ErrorView(stringResource(id = uiState.message), launcher)
                    }
                }

            }

        }
    }
}

@Composable
fun HomeHeader() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
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
fun HomeSheet(showBottomSheet: MutableState<Boolean>) {
    if(showBottomSheet.value) MedicationSheet(showBottomSheet)
}

@Composable
fun HomeContent(content: List<MedicationEntity>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ){
        items(content.size){
            MedItem()
        }
    }
}


@Composable
fun EmptyView() {
    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

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
fun HomeEmpty() {
    HomeScreen(
        uiState = HomeViewModel.HomeUiState.Empty,
        onAddClick = {  },
        verifyPermissions = { true }
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkHome() {
    HomeScreen(
        uiState = HomeViewModel.HomeUiState.Empty,
        onAddClick = { },
        verifyPermissions = { true }
    )
}

@Preview
@Composable
fun HomeSuccess() {
    HomeScreen(
        uiState = HomeViewModel.HomeUiState.Success(
            listOf(MedicationEntity(1), MedicationEntity(2))
        ),
        onAddClick = { },
        verifyPermissions = { true }
    )
}

@Preview
@Composable
fun HomeLoading() {
    HomeScreen(
        uiState = HomeViewModel.HomeUiState.Loading,
        onAddClick = { },
        verifyPermissions = { true }
    )
}

@Preview
@Composable
fun HomeError() {
    HomeScreen(
        uiState = HomeViewModel.HomeUiState.Error(R.string.home_empty_view),
        onAddClick = { },
        verifyPermissions = { true }
    )
}