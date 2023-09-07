package com.example.roomtodolist.ui.screens.addfolder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.data.folder.folderColors
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.SavingValidationButtons
import com.example.roomtodolist.ui.components.defaultTextFieldColors
import com.example.roomtodolist.ui.components.defaultTextFieldShape


const val TAG = "DEBUGGING : "
@Composable
fun AddFolderScreen(
    addFolderViewModel: AddFolderViewModel
) {
    val context = LocalContext.current
    Container(actionBar = {
        ActionBar(title = "Add Folder") {
            addFolderViewModel.navigateBack()
        }
    }) {
        FolderNameTextField(
            folderName = addFolderViewModel.uiState.folderName,
            onFolderNameChange = { addFolderViewModel.setFolderName(it) }
        )

        FolderColorPicker(
            colors = folderColors,
            selectedColor = addFolderViewModel.uiState.folderColor,
            setFolderColor = {
                addFolderViewModel.setFolderColor(it)
            }
        )

        SavingValidationButtons(
            onSave = {
                if (addFolderViewModel.isReadyToSave()) {
                    addFolderViewModel.save()
                    addFolderViewModel.navigateBack()
                    addFolderViewModel.showSuccessMessage(context = context)
                    addFolderViewModel.clear()
                }
                else {
                    addFolderViewModel.showErrorMessage(context = context)
                }
            },
            onCancel = {
                addFolderViewModel.clear()
                addFolderViewModel.navigateBack()
            }
        )
    }
}

@Composable
fun FolderColorPicker(
    colors: MutableList<Color>,
    setFolderColor: (Color) -> Unit,
    selectedColor: Color?
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        colors.forEach { color ->
            Surface(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable {
                        setFolderColor(color)
                    },
                shape = CircleShape,
                color =
                if (selectedColor == color)
                    color
                else
                    Color.Transparent,
                border = BorderStroke(2.dp, color),
                content = {}
            )
        }
    }
}

@Composable
fun FolderNameTextField(
    folderName: String,
    onFolderNameChange: (String) -> Unit
) {
    TextField(
        value = folderName,
        onValueChange = onFolderNameChange,
        shape = defaultTextFieldShape(),
        colors = defaultTextFieldColors(),
        placeholder = {
            Text(text = "Folder name")
        },
        modifier = Modifier.fillMaxWidth()
    )
}
