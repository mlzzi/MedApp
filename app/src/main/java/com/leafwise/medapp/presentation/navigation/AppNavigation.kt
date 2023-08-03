package com.leafwise.medapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.leafwise.medapp.presentation.ui.home.HomeScreen
import com.leafwise.medapp.presentation.ui.home.HomeViewModel

@Suppress("UnusedPrivateProperty")
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TopLevelDestination.Home.route
    ) {
        composable(route = TopLevelDestination.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val homeScreenUiState by remember { homeViewModel.homeUiState }.collectAsState()

            HomeScreen(
                uiState = homeScreenUiState,
                onAddClick = { homeViewModel.addMedication() },
                verifyPermissions = { homeViewModel.verifyPermissions() }
            )
        }
    }
}
