package com.example.roomtodolist.ui.screens

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
            val tasksPerFolder = hashMapOf<FolderTable, MutableList<TaskTable>>()
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
            updateTasksPerFolderStateByAddingNewFolder(folder)
        }
    }

    suspend fun getFolderByName(folderName: String) : FolderTable =
        repository.folderDao.getFolderByName(folderName)

    private fun updateFolderState(newFolder: FolderTable){
        uiState.folders += newFolder
    }

    fun addTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = repository.taskDao.addTask(task)
            val newTask = task.copy(id = id)
            updateTaskStateByAddingTask(newTask)
            updateTasksPerFolderStateByAddingNewTask(task = newTask)
        }
    }

    fun setTaskToUpdate(task: TaskTable) {
        uiState = uiState.copy(taskToUpdate = task)
    }

    fun updateTask(task: TaskTable, oldFolderName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.taskDao.updateTask(task)
            updateTaskStateByUpdatingTask(task)
            if (oldFolderName == task.folder)
                updateTasksPerFolderStateByUpdatingTaskData(task.folder, task = task)
            else
                updateTasksPerFolderStateByChangingTasksFolder(oldFolderName, task)
        }
    }

    fun deleteTask(task: TaskTable) {
        viewModelScope.launch {
            repository.taskDao.deleteTask(taskTable = task)
            updateTaskStateByDeletingTask(task)
            updateTasksPerFolderStateByDeletingTaskFromFolder(task.folder, task)
        }
    }

    fun clearTaskToUpdate() {
        uiState = uiState.copy(taskToUpdate = null)
    }

    private fun updateTaskStateByAddingTask(newTask: TaskTable){
        uiState.tasks += newTask
    }

    private fun updateTaskStateByUpdatingTask(task: TaskTable) {
        for(taskIndex in uiState.tasks.indices) {
            if (uiState.tasks[taskIndex].id == task.id) {
                uiState.tasks[taskIndex] = task
                break
            }
        }
    }

    private fun updateTaskStateByDeletingTask(task: TaskTable) {
        for(taskIndex in uiState.tasks.indices) {
            if (uiState.tasks[taskIndex].id == task.id) {
                uiState.tasks.removeAt(taskIndex)
                break
            }
        }
    }

    private fun updateTasksPerFolderStateByAddingNewFolder(newFolder: FolderTable) {
        uiState.tasksPerFolder[newFolder] = mutableListOf()
    }

    private fun updateTasksPerFolderStateByAddingNewTask(task: TaskTable) {
        for (folder in uiState.tasksPerFolder) {
            if (folder.key.name == task.folder) {
                uiState.tasksPerFolder[folder.key]!! += task
                break
            }
        }
    }

    private fun updateTasksPerFolderStateByUpdatingTaskData(folderName: String, task: TaskTable){
        for (folder in uiState.tasksPerFolder) {
            if (folderName == folder.key.name)
                for (taskIndex in uiState.tasksPerFolder[folder.key]!!.indices) {
                    if (uiState.tasksPerFolder[folder.key]!![taskIndex].id == task.id) {
                        uiState.tasksPerFolder[folder.key]!![taskIndex] = task
                        break
                    }
                }
        }
    }

    private fun updateTasksPerFolderStateByChangingTasksFolder(folderName: String, task: TaskTable) {
        updateTasksPerFolderStateByDeletingTaskFromFolder(folderName, task)
        updateTasksPerFolderStateByAddingNewTask(task)
    }

    private fun updateTasksPerFolderStateByDeletingTaskFromFolder(folderName: String, task: TaskTable) {
        for (folder in uiState.tasksPerFolder) {
            if (folderName == folder.key.name)
                for (taskIndex in uiState.tasksPerFolder[folder.key]!!.indices) {
                    if (uiState.tasksPerFolder[folder.key]!![taskIndex].id == task.id) {
                        uiState.tasksPerFolder[folder.key]!!.removeAt(taskIndex)
                        break
                    }
                }
        }
    }

}