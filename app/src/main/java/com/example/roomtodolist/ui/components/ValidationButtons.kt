package com.example.roomtodolist.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ValidationButtons(onSave: () -> Unit, onCancel: () -> Unit) {
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
            Text(text = "Cancel", color = MaterialTheme.colorScheme.onBackground)
        }
        Button(
            onClick = onSave,
            shape = defaultButtonShape(),
            colors = defaultFilledButtonColors(),
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Text(text = "Save")
        }
    }
}