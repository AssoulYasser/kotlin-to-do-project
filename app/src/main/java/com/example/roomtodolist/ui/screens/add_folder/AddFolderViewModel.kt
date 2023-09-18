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
//    var uiState by mutableStateOf(AddFolderUiState())
//        private set

    var nameState by mutableStateOf("")

    var colorState by mutableStateOf<Color?>(null)

    var assetState by mutableStateOf(0)

    fun getFolderColors() = mainViewModel.getFolderColors()

    fun getFolderAssets() = mainViewModel.getFolderAssets().keys.toList()

    fun setFolderName(name: String) {
        nameState = name
    }

    fun setFolderColor(color: Color) {
        colorState = color
    }

    fun setFolderAsset(asset: Int) {
        assetState = asset
    }

    fun isReadyToSave() =
        colorState != null &&
        nameState != "" &&
        assetState != 0

    fun showErrorMessage(context: Context) {
        if (nameState == "")
            Toast.makeText(context, "insert name please", Toast.LENGTH_SHORT).show()

        else if (colorState == null)
            Toast.makeText(context, "choose color please", Toast.LENGTH_SHORT).show()

        else if (assetState == 0)
            Toast.makeText(context, "choose asset please", Toast.LENGTH_SHORT).show()
    }

    fun showSuccessMessage(context: Context) {
        Toast.makeText(context, "the folder $nameState has been saved successfully", Toast.LENGTH_SHORT).show()
    }

    fun save(context: Context) {
        mainViewModel.addFolder(
            FolderTable(
                name = nameState,
                color = colorState!!.toArgb(),
                asset = mainViewModel.getBitmap(context, assetState, colorState!!.toArgb())
            )
        )
    }

    fun clear() {

    }

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

}