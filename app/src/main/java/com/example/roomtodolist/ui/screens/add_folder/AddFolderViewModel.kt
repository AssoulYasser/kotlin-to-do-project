package com.example.roomtodolist.ui.screens.add_folder

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.domain.main_activity.MainViewModel

class AddFolderViewModel(private val mainViewModel: MainViewModel) : ViewModel() {
    var folderNameState by mutableStateOf("")
        private set

    var folderColorState by mutableStateOf<Color?>(null)
        private set

    var folderAssetState by mutableStateOf(0)
        private set

    fun getFolderColors() = mainViewModel.getFolderColors()

    fun getFolderAssets() = mainViewModel.getFolderAssets().keys.toList()

    fun setFolderName(name: String) {
        folderNameState = name
    }

    fun setFolderColor(color: Color) {
        folderColorState = color
    }

    fun setFolderAsset(asset: Int) {
        folderAssetState = asset
    }

    fun isReadyToSave() =
        folderColorState != null &&
        folderNameState != "" &&
        folderAssetState != 0

    fun showErrorMessage(context: Context) {
        if (folderNameState == "")
            Toast.makeText(context, "insert name please", Toast.LENGTH_SHORT).show()

        else if (folderColorState == null)
            Toast.makeText(context, "choose color please", Toast.LENGTH_SHORT).show()

        else if (folderAssetState == 0)
            Toast.makeText(context, "choose asset please", Toast.LENGTH_SHORT).show()
    }

    fun showSuccessMessage(context: Context) {
        Toast.makeText(context, "the folder $folderNameState has been saved successfully", Toast.LENGTH_SHORT).show()
    }

    fun save(context: Context) {
        mainViewModel.addFolder(
            FolderTable(
                name = folderNameState,
                color = folderColorState!!.toArgb(),
                asset = mainViewModel.getBitmap(context, folderAssetState, folderColorState!!.toArgb())
            )
        )
    }

    fun clear() {
        folderNameState = ""
        folderColorState = null
        folderAssetState = 0
    }

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

}