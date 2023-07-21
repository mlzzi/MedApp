package com.leafwise.medapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
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
            val homeScreenUiState = HomeViewModel.HomeUiState.Empty

            HomeScreen(
                uiState = homeScreenUiState,
                onNavigateClick = { source ->
                    navController.navigate(TopLevelDestination.Detail.withArgs(source))
                }
            )
        }
    }
}
