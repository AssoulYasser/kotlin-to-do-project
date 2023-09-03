package com.example.roomtodolist.ui.screens.addfolder

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.ui.screens.MainViewModel

class AddFolderViewModel(private val mainViewModel: MainViewModel) : ViewModel() {
    var uiState by mutableStateOf(AddFolderUiState())
        private set

    fun setFolderName(name: String) {
        uiState = uiState.copy(folderName = name)
    }

    fun setFolderColor(color: Color) {
        uiState = uiState.copy(folderColor = color)
    }

    fun isReadyToSave() = uiState.folderColor != null && uiState.folderName != ""

    fun showErrorToast(context: Context) {
        if (uiState.folderName == "")
            Toast.makeText(context, "insert name please", Toast.LENGTH_SHORT).show()

        else if (uiState.folderColor == null)
            Toast.makeText(context, "choose color please", Toast.LENGTH_SHORT).show()

    }

    fun showSuccessToast(context: Context) {
        Toast.makeText(context, "the folder ${uiState.folderName} has been saved successfully", Toast.LENGTH_SHORT).show()
    }

    fun save() {
        mainViewModel.addFolder(
            FolderTable(
                name = uiState.folderName,
                color = uiState.folderColor!!.toArgb()
            )
        )
    }

    fun clear() {
        uiState = AddFolderUiState()
    }

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

}