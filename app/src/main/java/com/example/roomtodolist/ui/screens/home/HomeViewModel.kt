package com.example.roomtodolist.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.ui.navigation.MainRoutes
import com.example.roomtodolist.ui.navigation.NestedRoutes
import com.example.roomtodolist.ui.screens.MainViewModel
import java.time.DayOfWeek
import java.time.LocalDate

@SuppressLint("NewApi")
class HomeViewModel(private val mainViewModel: MainViewModel) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        uiState = uiState.copy(selectedDayInCurrentDate = mainViewModel.getCurrentDate().currentDayOfMonth)
    }

    fun navigateToAddFolderScreen() {
        mainViewModel.navigateTo(NestedRoutes.ADD_FOLDER.name)
    }

    fun navigateToAddTaskScreen() {
        mainViewModel.navigateTo(MainRoutes.ADD_TASK.name)
    }

    fun navigateToTaskShowCase() {
        mainViewModel.navigateTo(NestedRoutes.TASK_SHOW_CASE.name)
    }

    fun navigateToFolderShowCase() {
        mainViewModel.navigateTo(NestedRoutes.FOLDER_SHOW_CASE.name)
    }

    fun setFolderToUpdate(folderTable: FolderTable) {
        mainViewModel.setFolderToUpdate(folderTable)
    }

    fun setTaskToUpdate(taskTable: TaskTable) {
        mainViewModel.setTaskToUpdate(taskTable)
    }

    fun setSearch(value: String) {
        uiState = uiState.copy(search = value)
    }

    fun setSelectedDay(day: Int) {
        uiState = uiState.copy(selectedDayInCurrentDate = day)
    }


    fun isCompactWidth() = mainViewModel.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    fun getDaysOfWeek() = mainViewModel.getDaysOfTheWeek()
    fun getFolders() : List<FolderTable> = mainViewModel.uiState.folders.values.toList()
    fun noTaskExists() : Boolean = mainViewModel.uiState.tasks.isEmpty()
    fun getTasksPerFolder(): HashMap<FolderTable, MutableList<TaskTable>> = mainViewModel.uiState.tasksPerFolder
    fun noTasksInCurrentDayExists(date: LocalDate) = filterTasksByDay(date).isEmpty()

    private fun filterTasksByDay(date: LocalDate): HashMap<FolderTable, MutableList<TaskTable>> {
        val list = getTasksPerFolder()
        val returnValue = getTasksPerFolder()
        for (folder in list.keys) {
            for (task in list[folder]!!) {
                if (!task.date.isEqual(date))
                    returnValue[folder]!!.remove(task)
            }
        }
        return returnValue
    }

}