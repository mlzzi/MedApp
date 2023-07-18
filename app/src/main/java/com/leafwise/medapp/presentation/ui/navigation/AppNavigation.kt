package com.leafwise.medapp.presentation.ui.navigation

import android.provider.SyncStateContract
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.leafwise.medapp.presentation.ui.home.HomeScreen

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
//            val homeViewModel: BookViewModel = hiltViewModel()
//            val homeScreenUiState by remember { homeViewModel.bookUiState }.collectAsState()

            HomeScreen(
//                uiState = homeScreenUiState,
                onNavigateClick = { source ->
                    navController.navigate(TopLevelDestination.Detail.withArgs(source))
                }
            )
        }
    }
}
