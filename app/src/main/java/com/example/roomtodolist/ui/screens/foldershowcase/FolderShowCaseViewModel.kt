package com.example.roomtodolist.ui.screens.foldershowcase

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
import com.example.roomtodolist.ui.screens.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FolderShowCaseViewModel(private val mainViewModel: MainViewModel): ViewModel() {
    var uiState by mutableStateOf(FolderShowCaseUiState())
        private set
    private var originalFolder : FolderTable? = null

    fun start() {
        originalFolder = mainViewModel.uiState.folderToUpdate
        viewModelScope.launch(Dispatchers.IO) {
            if (originalFolder != null) {
                uiState.folderName = originalFolder!!.name
                uiState.folderColor = Color(originalFolder!!.color)
            }
        }
    }

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

    fun updateName(name: String) {
        uiState = uiState.copy(folderName = name)
    }

    fun updateColor(color: Color) {
        uiState = uiState.copy(folderColor = color)
    }

    private fun getFolderTable() = FolderTable(
        id = originalFolder!!.id,
        name = uiState.folderName,
        color = uiState.folderColor!!.toArgb(),
    )

    fun getFolderColors() = mainViewModel.getFolderColors()

    fun isReadyToSave() = uiState.folderColor != null && uiState.folderName != ""

    fun showErrorMessage(context: Context) {
        if (uiState.folderName == "")
            Toast.makeText(context, "insert name please", Toast.LENGTH_SHORT).show()

        else if (uiState.folderColor == null)
            Toast.makeText(context, "choose color please", Toast.LENGTH_SHORT).show()

    }

    fun showSuccessMessage(context: Context) {
        Toast.makeText(context, "the folder ${uiState.folderName} has been saved successfully", Toast.LENGTH_SHORT).show()
    }


    fun save() {
        mainViewModel.updateFolder(getFolderTable())
    }

    fun delete() {
        mainViewModel.deleteFolder(getFolderTable())
    }

    fun clear() {
        uiState = FolderShowCaseUiState()
        mainViewModel.clearFolderToUpdate()
    }

}