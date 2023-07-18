package com.leafwise.medapp.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.leafwise.medapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateClick: (source: String) -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
        floatingActionButton = { MedicationAddButton() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
//            when (uiState) {
//                is UiState.Loading -> {
//                    LoadingIndicator(modifier = Modifier.fillMaxSize())
//                }
//
//                is UiState.Success -> {
//                    HomeScreenContent(
//                        modifier = Modifier.fillMaxSize(),
//                        data = uiState.data,
//                        onNavigateClick = onNavigateClick
//                    )
//                }
//
//                is UiState.Error -> {}
//
//                else -> {}
//            }
        }
    }
}