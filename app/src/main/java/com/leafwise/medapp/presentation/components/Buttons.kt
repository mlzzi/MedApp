package com.leafwise.medapp.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.leafwise.medapp.presentation.theme.Dimens

@Composable
fun TextIconButton(
    enable: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
){
    Button(
        modifier = Modifier.padding(Dimens.MediumPadding.size),
        enabled = enable,
        onClick = { onClick() },
        contentPadding = ButtonDefaults.ContentPadding
    ) {

        Icon(
            icon,
            contentDescription = "textIcon"
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text)
    }


}

@Composable
fun FloatingButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onClick() }) {
        Icon(
            modifier = Modifier
                .size(ButtonDefaults.IconSize),
            imageVector = icon,
            contentDescription = "floatingButton",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }

}