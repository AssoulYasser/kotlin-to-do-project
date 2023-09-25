package com.example.roomtodolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.ui.theme.StateColors

@Composable
fun UpdatingValidationButtons(
    onUpdate: () -> Unit,
    onDelete: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onCancel,
                shape = defaultButtonShape(),
                colors = defaultOutlinedButtonColors(),
                border = defaultButtonStroke(MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Button(
                onClick = onDelete,
                shape = defaultButtonShape(),
                colors = defaultFilledButtonColors(StateColors.Negative),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                Text(text = "Delete", style = MaterialTheme.typography.headlineMedium)
            }
        }
        Button(
            onClick = onUpdate,
            shape = defaultButtonShape(),
            colors = defaultFilledButtonColors(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Update",
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    }
}