package com.example.roomtodolist.ui.screens.folder_show_case

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.FolderAssetPicker
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
            folderName = { folderShowCaseViewModel.folderNameState },
            onFolderNameChange = { folderShowCaseViewModel.updateName(it) }
        )
        FolderColorPicker(
            colors = folderShowCaseViewModel.getFolderColors(),
            selectedColor = { folderShowCaseViewModel.folderColorState?.toArgb() ?: 0 },
            setFolderColor = { folderShowCaseViewModel.updateColor(it) },
        )
        FolderAssetPicker(
            assets = folderShowCaseViewModel.getFolderAssets(),
            selectedAsset = { folderShowCaseViewModel.folderAssetState },
            setFolderAsset = {
                folderShowCaseViewModel.updateAsset(it)
            }
        )
        UpdatingValidationButtons(
            onUpdate = {
                if (folderShowCaseViewModel.isReadyToSave()){
                    folderShowCaseViewModel.save(context)
                    folderShowCaseViewModel.navigateBack()
                    folderShowCaseViewModel.showSuccessMessage(context = context)
                    folderShowCaseViewModel.clear()
                }
                else
                    folderShowCaseViewModel.showErrorMessage(context = context)
            },
            onDelete = {
                folderShowCaseViewModel.delete(context)
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