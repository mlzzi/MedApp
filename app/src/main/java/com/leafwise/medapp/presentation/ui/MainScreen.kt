package com.leafwise.medapp.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.leafwise.medapp.presentation.ui.navigation.AppNavigation
import com.leafwise.medapp.presentation.ui.theme.MedAppTheme

@Composable
fun MainScreen(){
    MedAppTheme {

        val navController = rememberNavController()

        Scaffold { innerPadding ->
            AppNavigation(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        }

    }
}