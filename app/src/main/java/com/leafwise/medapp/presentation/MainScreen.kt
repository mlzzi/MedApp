package com.leafwise.medapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.leafwise.medapp.presentation.navigation.AppNavigation
import com.leafwise.medapp.presentation.theme.MedAppTheme

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