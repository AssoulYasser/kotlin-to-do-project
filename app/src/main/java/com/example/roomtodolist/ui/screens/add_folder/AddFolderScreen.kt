package com.example.roomtodolist.ui.screens.add_folder

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.FolderAssetPicker
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
            addFolderViewModel.clear()
        }
    }) {
        FolderNameTextField(
            folderName = { addFolderViewModel.folderNameState },
            onFolderNameChange = { addFolderViewModel.setFolderName(it) }
        )
        Log.d(TAG, "AddFolderScreen: RECOMPOSED")

        FolderColorPicker(
            colors = addFolderViewModel.getFolderColors(),
            selectedColor = { addFolderViewModel.folderColorState?.toArgb() ?: 0 },
            setFolderColor = {
                addFolderViewModel.setFolderColor(it)
            }
        )

        FolderAssetPicker(
            assets = addFolderViewModel.getFolderAssets(),
            selectedAsset = { addFolderViewModel.folderAssetState },
            setFolderAsset = {
                addFolderViewModel.setFolderAsset(it)
            }
        )

        SavingValidationButtons(
            onSave = {
                if (addFolderViewModel.isReadyToSave()) {
                    addFolderViewModel.save(context)
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