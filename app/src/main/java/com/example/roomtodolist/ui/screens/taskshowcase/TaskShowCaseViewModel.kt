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
import com.example.roomtodolist.domain.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class TaskShowCaseViewModel(val mainViewModel: MainViewModel) : ViewModel() {
    var uiState by mutableStateOf(TaskShowCaseUiState())
        private set
    private var originalTask : TaskTable? = null

    fun start() {
        originalTask = mainViewModel.uiState.taskToUpdate
        viewModelScope.launch(Dispatchers.IO) {
            if (originalTask != null) {
                val folder = mainViewModel.uiState.folders[originalTask!!.folder]
                uiState.taskTitle = originalTask!!.title
                uiState.taskPriority = originalTask!!.priority
                uiState.taskDate = originalTask!!.date
                uiState.taskTime = originalTask!!.time
                uiState.taskFolder = folder
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

    private fun getTaskTable() = TaskTable(
        id = originalTask!!.id,
        title = uiState.taskTitle,
        date = uiState.taskDate!!,
        time = uiState.taskTime!!,
        priority = uiState.taskPriority,
        folder = uiState.taskFolder!!.id!!
    )

    fun updateTaskTitle(title: String) {
        uiState = uiState.copy(taskTitle = title)
    }

    fun updatePriority(priority: TaskPriority) {
        uiState = uiState.copy(taskPriority = priority)
    }

    fun updateDate(date: LocalDate) {
        uiState = uiState.copy(taskDate = date)
    }

    fun updateTime(time: LocalTime) {
        uiState = uiState.copy(taskTime = time)
    }

    fun updateFolder(folder: FolderTable) {
        uiState = uiState.copy(taskFolder = folder)
    }

    fun getFolders() = mainViewModel.uiState.folders.values.toList()

    fun isReadyToSave() = uiState.taskTitle != "" &&
            uiState.taskPriority != TaskPriority.UNSPECIFIED &&
            uiState.taskDate != null &&
            uiState.taskTime != null &&
            uiState.taskFolder != null

    fun showErrorMessage(context: Context) {
        if (uiState.taskTitle == "")
            Toast.makeText(context, "insert title please", Toast.LENGTH_SHORT).show()

        else if (uiState.taskPriority == TaskPriority.UNSPECIFIED)
            Toast.makeText(context, "choose priority please", Toast.LENGTH_SHORT).show()

        else if (uiState.taskDate == null)
            Toast.makeText(context, "choose date please", Toast.LENGTH_SHORT).show()

        else if (uiState.taskTime == null)
            Toast.makeText(context, "choose time please", Toast.LENGTH_SHORT).show()

        else if (uiState.taskFolder == null)
            Toast.makeText(context, "choose folder please", Toast.LENGTH_SHORT).show()

        else
            Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()

    }

    fun showSuccessMessage(context: Context) {
        Toast.makeText(context, "the task ${uiState.taskTitle} has been saved successfully", Toast.LENGTH_SHORT).show()
    }

    fun save() {
        mainViewModel.updateTask(getTaskTable())
    }

    fun delete() {
        mainViewModel.deleteTask(getTaskTable())
    }

    fun clear() {
        uiState = TaskShowCaseUiState()
        mainViewModel.clearTaskToUpdate()
    }
}