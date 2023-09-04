package com.example.roomtodolist.ui.screens.addtask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskPriority
import com.example.roomtodolist.ui.navigation.MainRoutes
import com.example.roomtodolist.ui.navigation.NestedRoutes
import com.example.roomtodolist.ui.screens.MainViewModel
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
        uiState = uiState.copy(date = date)
    }

    fun setTime(time: LocalTime) {
        uiState = uiState.copy(time = time)
    }

    fun getFolders() : List<FolderTable> = mainViewModel.uiState.folders

    fun navigateToAddFolderScreen() {
        mainViewModel.navigateTo(NestedRoutes.ADD_FOLDER.name)
    }

    fun navigateToHomeScreen() {
        mainViewModel.navigateTo(MainRoutes.HOME.name)
    }
}