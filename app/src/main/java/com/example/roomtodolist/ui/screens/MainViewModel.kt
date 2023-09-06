package com.example.roomtodolist.ui.screens

import android.icu.text.CaseMap.Fold
import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.roomtodolist.data.Repository
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    val repository: Repository,
) : ViewModel() {

    var uiState by mutableStateOf(MainUiState())
        private set

    lateinit var navHostController: NavHostController
        private set

    lateinit var windowSizeClass: WindowSizeClass
        private set

    fun start() {
        viewModelScope.launch(Dispatchers.IO) {
            val folders = repository.folderDao.getFolders()
            val tasks = repository.taskDao.getTasks()
            val tasksPerFolder = hashMapOf<FolderTable, List<TaskTable>>()
            for (folder in folders) {
                tasksPerFolder[folder] = repository.taskDao.getTasksFromFolder(folder.name)
            }
            uiState = uiState.copy(
                folders = folders,
                tasks = tasks,
                tasksPerFolder = tasksPerFolder
            )
        }
    }

    fun setNavHostController(navHostController: NavHostController) {
        this.navHostController = navHostController
    }

    fun setWindowSizeClass(windowSizeClass: WindowSizeClass) {
        this.windowSizeClass = windowSizeClass
    }

    fun navigateTo(destination: String) {
        navHostController.navigate(destination) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateBack() {
        navHostController.popBackStack()
    }

    fun addFolder(folder: FolderTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.folderDao.addFolder(folder)
            updateFolderState(folder)
            updateTasksPerFolderState(folder)
        }
    }

    private fun updateFolderState(newFolder: FolderTable){
        uiState = uiState.copy(folders = uiState.folders + newFolder)
    }

    fun addTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.taskDao.addTask(task)
            updateTaskState(task)
            updateTasksPerFolderState(task.folder, newTask = task)
        }
    }

    private fun updateTaskState(newTask: TaskTable){
        uiState = uiState.copy(tasks = uiState.tasks + newTask)
    }

    private fun updateTasksPerFolderState(newFolder: FolderTable) {
        uiState.tasksPerFolder[newFolder] = listOf()
    }

    private fun updateTasksPerFolderState(folderName: String, newTask: TaskTable) {
        for (folder in uiState.tasksPerFolder) {
            if (folder.key.name == folderName)
                uiState.tasksPerFolder[folder.key] = uiState.tasksPerFolder[folder.key]!!
                    .plus(newTask)
        }
    }

}