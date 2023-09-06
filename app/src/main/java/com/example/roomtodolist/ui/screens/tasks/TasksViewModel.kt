package com.example.roomtodolist.ui.screens.tasks

import androidx.lifecycle.ViewModel
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.ui.navigation.MainRoutes
import com.example.roomtodolist.ui.screens.MainViewModel

class TasksViewModel(private val mainViewModel: MainViewModel) : ViewModel() {

    var uiState = TasksUiState()
        private set

    fun navigateToAddTaskScreen() {
        mainViewModel.navigateTo(MainRoutes.ADD_TASK.name)
    }

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

    fun getFolders() : List<FolderTable> = mainViewModel.uiState.folders

    fun getTasksPerFolder(): HashMap<FolderTable, List<TaskTable>> = mainViewModel.uiState.tasksPerFolder

}