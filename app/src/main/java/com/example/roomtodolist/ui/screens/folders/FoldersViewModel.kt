package com.example.roomtodolist.ui.screens.folders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.domain.main_activity.MainViewModel
import com.example.roomtodolist.ui.navigation.NestedRoutes

class FoldersViewModel(val mainViewModel: MainViewModel) : ViewModel() {

    val uiState by mutableStateOf(FoldersUiState())

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

    fun navigateToFolderShowCase() {
        mainViewModel.navigateTo(NestedRoutes.FOLDER_SHOW_CASE.name)
    }

    fun navigateToAddFolderScreen() {
        mainViewModel.navigateTo(NestedRoutes.ADD_FOLDER.name)
    }

    fun setFolderToUpdate(folder: FolderTable) {
        mainViewModel.setFolderToUpdate(folder)
    }

    fun getFolders() = mainViewModel.folders.values.toList()

}