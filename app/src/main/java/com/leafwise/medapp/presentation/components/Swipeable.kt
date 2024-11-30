package com.leafwise.medapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.leafwise.medapp.R

//TODO Deprecated in Material 3.0.0-alpha04
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DismissBackground(
//    dismissState: DismissState
//) {
//    val color = when (dismissState.dismissDirection) {
//        DismissDirection.StartToEnd -> MaterialTheme.colorScheme.onErrorContainer
//        DismissDirection.EndToStart -> Color(0xFF1DE9B6)
//        null -> Color.Transparent
//    }
//    val direction = dismissState.dismissDirection
//
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color)
//            .padding(12.dp, 8.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        if (direction == DismissDirection.StartToEnd) Icon(
//            Icons.Default.Delete,
//            contentDescription = "delete"
//        )
//
//        Spacer(modifier = Modifier)
//        if (direction == DismissDirection.EndToStart) Icon(
//            // make sure add baseline_archive_24 resource to drawable folder
//            painter = painterResource(R.drawable.ic_add),
//            contentDescription = "Archive"
//        )
//    }
//}