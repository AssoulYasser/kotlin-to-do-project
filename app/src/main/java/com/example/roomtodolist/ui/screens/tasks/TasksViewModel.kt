package com.example.roomtodolist.ui.screens.tasks

import androidx.lifecycle.ViewModel
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.ui.navigation.MainRoutes
import com.example.roomtodolist.ui.navigation.NestedRoutes
import com.example.roomtodolist.domain.main_activity.MainViewModel

class TasksViewModel(private val mainViewModel: MainViewModel) : ViewModel() {

    var uiState = TasksUiState()
        private set

    fun navigateToAddTaskScreen() {
        mainViewModel.navigateTo(MainRoutes.ADD_TASK.name)
    }

    fun navigateToTaskShowCase() {
        mainViewModel.navigateTo(NestedRoutes.TASK_SHOW_CASE.name)
    }

    fun navigateToFolderShowCase() {
        mainViewModel.navigateTo(NestedRoutes.FOLDER_SHOW_CASE.name)
    }

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

    fun setTaskToUpdate(taskTable: TaskTable) {
        mainViewModel.setTaskToUpdate(taskTable)
    }

    fun setFolderToUpdate(folderTable: FolderTable) {
        mainViewModel.setFolderToUpdate(folderTable)
    }

    fun isDarkMode() = mainViewModel.isDarkTheme

    fun getFolders() : List<FolderTable> = mainViewModel.folders.values.toList()

    fun noTaskExists() : Boolean = mainViewModel.tasks.isEmpty()

    //TODO
    fun getTasksPerFolder(): HashMap<FolderTable, MutableList<TaskTable>> = hashMapOf()

}