package com.leafwise.medapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leafwise.medapp.R

@Composable
fun MedAddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() }) {
        Icon(
            modifier = Modifier
                .size(ButtonDefaults.IconSize),
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = stringResource(id = R.string.add),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }

}
