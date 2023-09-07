package com.example.roomtodolist.ui.screens.foldershowcase

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.FolderColorPicker
import com.example.roomtodolist.ui.components.FolderNameTextField
import com.example.roomtodolist.ui.components.UpdatingValidationButtons

@Composable
fun FolderShowCaseScreen(
    folderShowCaseViewModel: FolderShowCaseViewModel
) {
    folderShowCaseViewModel.start()
    val context = LocalContext.current
    Container(actionBar = {
        ActionBar(title = "Update folder") {
            folderShowCaseViewModel.navigateBack()
        }
    }) {
        FolderNameTextField(
            folderName = folderShowCaseViewModel.uiState.folderName,
            onFolderNameChange = { folderShowCaseViewModel.updateName(it) }
        )
        FolderColorPicker(
            colors = folderShowCaseViewModel.getFolderColors(),
            setFolderColor = { folderShowCaseViewModel.updateColor(it) },
            selectedColor = folderShowCaseViewModel.uiState.folderColor
        )
        UpdatingValidationButtons(
            onUpdate = {
                if (folderShowCaseViewModel.isReadyToSave()){
                    folderShowCaseViewModel.save()
                    folderShowCaseViewModel.navigateBack()
                    folderShowCaseViewModel.showSuccessMessage(context = context)
                    folderShowCaseViewModel.clear()
                }
                else
                    folderShowCaseViewModel.showErrorMessage(context = context)
            },
            onDelete = {
                folderShowCaseViewModel.delete()
                folderShowCaseViewModel.clear()
                folderShowCaseViewModel.navigateBack()
            },
            onCancel = {
                folderShowCaseViewModel.clear()
                folderShowCaseViewModel.navigateBack()
            }
        )
    }
}