package com.example.roomtodolist.ui.screens.addfolder

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.example.roomtodolist.ui.components.defaultButtonShape
import com.example.roomtodolist.ui.components.defaultButtonStroke
import com.example.roomtodolist.ui.components.defaultFilledButtonColors
import com.example.roomtodolist.ui.components.defaultOutlinedButtonColors
import com.example.roomtodolist.ui.components.defaultTextFieldColors
import com.example.roomtodolist.ui.components.defaultTextFieldShape


const val TAG = "DEBUGGING : "
@Composable
fun AddFolderScreen(
    addFolderViewModel: AddFolderViewModel
) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ActionBar(title = "Add Folder") {
                addFolderViewModel.navigateBack()
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                ValidationButtons(
                    onSave = {
                        if (addFolderViewModel.isReadyToSave()) {
                            addFolderViewModel.save()
                            addFolderViewModel.navigateBack()
                            addFolderViewModel.showSuccessToast(context = context)
                            addFolderViewModel.clear()
                        }
                        else {
                            addFolderViewModel.showErrorToast(context = context)
                        }
                    },
                    onCancel = {
                        addFolderViewModel.clear()
                        addFolderViewModel.navigateBack()
                    }
                )
            }

        }
    }
}

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
            Text(text = "Cancel")
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
