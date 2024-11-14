package com.leafwise.medapp.presentation.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leafwise.medapp.R
import com.leafwise.medapp.domain.model.AlarmInterval
import com.leafwise.medapp.domain.model.meds.Medication
import com.leafwise.medapp.domain.model.meds.TypeMedication
import com.leafwise.medapp.presentation.theme.Dimens
import com.leafwise.medapp.presentation.theme.MedAppTheme
import com.leafwise.medapp.util.extensions.calculateNextOccurrence
import com.leafwise.medapp.util.extensions.formatToHumanReadable
import kotlinx.coroutines.delay
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedItem(
    item: Medication,
    onRemove: () -> Unit,
    onEdit: () -> Unit,
    onSwitchChange: () -> Unit,
){
    val context = LocalContext.current
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(item)
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                show = false
                true
            } else false
        }, positionalThreshold = { 150.dp.toPx() }
    )
    AnimatedVisibility(
        show,exit = fadeOut(spring())
    ) {
        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier,
            background = {
                DismissBackground(dismissState)
            },
            dismissContent = {

                currentItem.run {
                    val prefixFooter = if (isActive) stringResource(id = R.string.home_next_dose)
                    else stringResource(id = R.string.home_last_dose)
                    val nextDose = prefixFooter.plus(" ") +
                            doses.first().calculateNextOccurrence(frequency).formatToHumanReadable()
                    CardItem(
                        colorStatus = Color.Gray,
                        title = name,
                        chips = listOf(
                            stringResource(id = frequency.getStringRes()),
                            "$quantity ${stringResource(id = type.label)}",
                        ),
                        cardCheck = isActive,
                        footer = nextDose,
                        onIconClick = onEdit,
                        onSwitchChange = onSwitchChange,
                    )
                }

            }
        )
    }

    LaunchedEffect(show){
        if(!show){
            delay(800)
            //TODO Remove affects room also
            onRemove()
            Toast.makeText(context, "IT", Toast.LENGTH_LONG).show()
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardItem(
    colorStatus: Color,
    title: String,
    chips: List<String>?,
    cardCheck: Boolean,
    footer: String,
    onIconClick: () -> Unit = {},
    onSwitchChange: () -> Unit = {},
) {
    ElevatedCard(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth(),
        enabled = cardCheck,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.elevatedCardColors(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.MediumPadding.size),
            horizontalArrangement = Arrangement.spacedBy(Dimens.MediumPadding.size)
        ) {
            Box(
                modifier = Modifier
                    .width(12.dp)
                    .height(30.dp)
                    .clip(CircleShape)
                    .background(colorStatus),
            )

            // Column containing text and chips
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                chips?.let {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Display chips
                        for (chip in chips.take(3)) {
                            ElevatedAssistChip(
                                enabled = cardCheck,
                                label = { Text(chip) },
                                colors = AssistChipDefaults.elevatedAssistChipColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                ),
                                shape = ShapeDefaults.ExtraLarge,
                                onClick = {}
                            )
                        }
                    }
                }

                Text(
                    text = footer,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

            }

            var isActive by rememberSaveable { mutableStateOf(cardCheck) }

            Column (
                horizontalAlignment = Alignment.End,

            ){
                IconButton(
                    modifier = Modifier.size(30.dp),
                    onClick = { onIconClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "",
                    )
                }
                Switch(
                    checked = isActive,
                    onCheckedChange = { newCheckedState ->
                        isActive = newCheckedState
                        onSwitchChange()
                    }
                )
            }

        }
    }
}


@Preview
@Composable
fun PreviewMedItem(){
    MedAppTheme {
        MedItem(
            Medication(
                uid = 1,
                isActive = true,
                name = "Dorflex",
                type = TypeMedication.CREAM,
                quantity = 2,
                frequency = AlarmInterval.WEEKLY,
                howManyTimes = 2,
                lastOccurrence = Calendar.getInstance(),
                doses = listOf(),
            ), onRemove = {}, onEdit = {}, onSwitchChange = {},

        )
    }

}