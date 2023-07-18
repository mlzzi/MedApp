package com.leafwise.medapp.presentation.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.leafwise.medapp.R
import com.leafwise.medapp.presentation.ui.theme.Red

@Composable
fun MedicationAddButton() {
    FloatingActionButton(
        containerColor = Red,
        onClick = { /*TODO*/ }) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .padding(4.dp),
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = null,
            tint = Color.White
        )
    }

}
