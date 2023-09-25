package com.example.roomtodolist.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FolderNameTextField(
    folderName: () -> String,
    onFolderNameChange: (String) -> Unit
) {

    TextField(
        value = folderName(),
        onValueChange = onFolderNameChange,
        shape = defaultTextFieldShape(),
        colors = defaultTextFieldColors(),
        placeholder = {
            Text(text = "Folder name")
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}
