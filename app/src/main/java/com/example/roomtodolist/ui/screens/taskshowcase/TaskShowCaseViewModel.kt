package com.example.roomtodolist.ui.screens.taskshowcase

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskPriority
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.ui.navigation.MainRoutes
import com.example.roomtodolist.ui.navigation.NestedRoutes
import com.example.roomtodolist.ui.screens.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class TaskShowCaseViewModel(val mainViewModel: MainViewModel) : ViewModel() {
    var uiState by mutableStateOf(TaskShowCaseUiState())
        private set

    fun start() {
        val oldTask = mainViewModel.uiState.taskToUpdate
        viewModelScope.launch(Dispatchers.IO) {
            if (oldTask != null) {
                val folder = mainViewModel.getFolderByName(oldTask.folder)
                uiState.oldFolderName = folder.name
                uiState.id = oldTask.id
                uiState.taskTitle = oldTask.title
                uiState.taskPriority = oldTask.priority
                uiState.date = oldTask.date
                uiState.time = oldTask.time
                uiState.folder = folder
            }
        }
    }

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

    fun navigateToHomeScreen() {
        mainViewModel.navigateTo(MainRoutes.HOME.name)
    }

    fun navigateToAddFolderScreen() {
        mainViewModel.navigateTo(NestedRoutes.ADD_FOLDER.name)
    }

    fun updateTaskTitle(title: String) {
        uiState = uiState.copy(taskTitle = title)
    }

    fun updatePriority(priority: TaskPriority) {
        uiState = uiState.copy(taskPriority = priority)
    }

    fun updateDate(date: LocalDate) {
        uiState = uiState.copy(date = date)
    }

    fun updateTime(time: LocalTime) {
        uiState = uiState.copy(time = time)
    }

    fun updateFolder(folder: FolderTable) {
        uiState = uiState.copy(folder = folder)
    }

    fun getFolders() = mainViewModel.uiState.folders

    fun isReadyToSave() = uiState.taskTitle != "" &&
            uiState.taskPriority != TaskPriority.UNSPECIFIED &&
            uiState.date != null &&
            uiState.time != null &&
            uiState.folder != null

    fun showErrorMessage(context: Context) {
        if (uiState.taskTitle == "")
            Toast.makeText(context, "insert title please", Toast.LENGTH_SHORT).show()

        else if (uiState.taskPriority == TaskPriority.UNSPECIFIED)
            Toast.makeText(context, "choose priority please", Toast.LENGTH_SHORT).show()

        else if (uiState.date == null)
            Toast.makeText(context, "choose date please", Toast.LENGTH_SHORT).show()

        else if (uiState.time == null)
            Toast.makeText(context, "choose time please", Toast.LENGTH_SHORT).show()

        else if (uiState.folder == null)
            Toast.makeText(context, "choose folder please", Toast.LENGTH_SHORT).show()

        else
            Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()

    }

    fun showSuccessMessage(context: Context) {
        Toast.makeText(context, "the task ${uiState.taskTitle} has been saved successfully", Toast.LENGTH_SHORT).show()
    }

    fun save() {
        mainViewModel.updateTask(
            TaskTable(
                id = uiState.id!!,
                title = uiState.taskTitle,
                date = uiState.date!!,
                time = uiState.time!!,
                priority = uiState.taskPriority,
                folder = uiState.folder!!.name
            ),
            uiState.oldFolderName
        )
    }

    fun delete() {
        mainViewModel.deleteTask(
            TaskTable(
                id = uiState.id!!,
                title = uiState.taskTitle,
                date = uiState.date!!,
                time = uiState.time!!,
                priority = uiState.taskPriority,
                folder = uiState.folder!!.name
            )
        )
    }

    fun clear() {
        uiState = TaskShowCaseUiState()
        mainViewModel.clearTaskToUpdate()
    }
}