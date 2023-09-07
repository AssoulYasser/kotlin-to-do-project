package com.example.roomtodolist.ui.screens.addfolder

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.roomtodolist.data.folder.folderColors
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.FolderColorPicker
import com.example.roomtodolist.ui.components.FolderNameTextField
import com.example.roomtodolist.ui.components.SavingValidationButtons


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
            colors = addFolderViewModel.getFolderColors(),
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