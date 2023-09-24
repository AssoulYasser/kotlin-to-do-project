package com.example.roomtodolist.ui.screens.folder_show_case

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.domain.main_activity.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FolderShowCaseViewModel(private val mainViewModel: MainViewModel): ViewModel() {

    private var originalFolder : FolderTable? = null

    var folderNameState by mutableStateOf("")
        private set
    var folderColorState by mutableStateOf<Color?>(null)
        private set
    var folderAssetState by mutableStateOf(0)
        private set

    fun start() {
        originalFolder = mainViewModel.folderToUpdate
        viewModelScope.launch(Dispatchers.IO) {
            if (originalFolder != null) {
                folderNameState = originalFolder!!.name
                folderColorState = Color(originalFolder!!.color)
                folderAssetState = 0
            }
        }
    }

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

    fun updateName(name: String) {
        folderNameState = name
    }

    fun updateColor(color: Color) {
        folderColorState = color
    }

    fun updateAsset(asset: Int) {
        folderAssetState = asset
    }

    private fun getFolderTable(context: Context) = FolderTable(
        id = originalFolder!!.id,
        name = folderNameState,
        color = folderColorState!!.toArgb(),
        asset = mainViewModel.getBitmap(context, folderAssetState, folderColorState!!.toArgb()),
    )

    fun getFolderColors() = mainViewModel.getFolderColors()

    fun getFolderAssets() = mainViewModel.getFolderAssets().keys.toList()

    fun isReadyToSave() = folderColorState != null && folderNameState != ""

    fun showErrorMessage(context: Context) {
        if (folderNameState == "")
            Toast.makeText(context, "insert name please", Toast.LENGTH_SHORT).show()

        else if (folderColorState == null)
            Toast.makeText(context, "choose color please", Toast.LENGTH_SHORT).show()

        else if (folderAssetState == 0)
            Toast.makeText(context, "choose asset please", Toast.LENGTH_SHORT).show()

    }

    fun showSuccessMessage(context: Context) {
        Toast.makeText(context, "the folder ${folderNameState} has been saved successfully", Toast.LENGTH_SHORT).show()
    }


    fun save(context: Context) {
        mainViewModel.updateFolder(getFolderTable(context))
    }

    fun delete(context: Context) {
        mainViewModel.deleteFolder(getFolderTable(context))
    }

    fun clear() {
        mainViewModel.clearFolderToUpdate()
    }

}