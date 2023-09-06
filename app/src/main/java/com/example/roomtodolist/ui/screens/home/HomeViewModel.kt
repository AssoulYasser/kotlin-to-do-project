package com.example.roomtodolist.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.ui.navigation.MainRoutes
import com.example.roomtodolist.ui.navigation.NestedRoutes
import com.example.roomtodolist.ui.screens.MainViewModel

class HomeViewModel(private val mainViewModel: MainViewModel) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    fun navigateToAddFolderScreen() {
        mainViewModel.navigateTo(NestedRoutes.ADD_FOLDER.name)
    }

    fun navigateToAddTaskScreen() {
        mainViewModel.navigateTo(MainRoutes.ADD_TASK.name)
    }

    fun setSearch(value: String) {
        uiState = uiState.copy(search = value)
    }

    fun getFolders() : List<FolderTable> = mainViewModel.uiState.folders
    fun getTasksPerFolder(): HashMap<FolderTable, List<TaskTable>> = mainViewModel.uiState.tasksPerFolder

}