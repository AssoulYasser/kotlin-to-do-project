package com.example.roomtodolist.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TaskTitle(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = defaultTextFieldColors(),
        shape = defaultTextFieldShape(),
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            Text(text = "Task title")
        }
    )
}

