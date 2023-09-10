package com.example.roomtodolist.ui.screens.addtask

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskPriority
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.ui.navigation.NestedRoutes
import com.example.roomtodolist.domain.MainViewModel
import java.time.LocalDate
import java.time.LocalTime

class AddTaskViewModel(private val mainViewModel: MainViewModel) : ViewModel() {
    var uiState by mutableStateOf(AddTaskUiState())
        private set

    fun setTaskTitle(title: String) {
        uiState = uiState.copy(taskTitle = title)
    }

    fun setPriority(priority: TaskPriority) {
        uiState = uiState.copy(taskPriority = priority)
    }

    fun setDate(date: LocalDate) {
        uiState = uiState.copy(taskDate = date)
    }

    fun setTime(time: LocalTime) {
        uiState = uiState.copy(taskTime = time)
    }

    fun setFolder(folder: FolderTable) {
        uiState = uiState.copy(taskFolder = folder)
    }

    fun getFolders() : List<FolderTable> = mainViewModel.uiState.folders.values.toList()

    fun navigateToAddFolderScreen() {
        mainViewModel.navigateTo(NestedRoutes.ADD_FOLDER.name)
    }

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

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
        mainViewModel.addTask(
            TaskTable(
                title = uiState.taskTitle,
                date = uiState.taskDate!!,
                time = uiState.taskTime!!,
                priority = uiState.taskPriority,
                folder = uiState.taskFolder!!.id!!
            )
        )
    }

    fun clear() {
        uiState = AddTaskUiState()
    }
}