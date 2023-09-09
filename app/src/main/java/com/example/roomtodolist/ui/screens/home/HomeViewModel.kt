package com.example.roomtodolist.ui.screens.home

import android.annotation.SuppressLint
import android.util.Log
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

    fun navigateToTaskShowCaseScreen() {
        mainViewModel.navigateTo(NestedRoutes.TASK_SHOW_CASE.name)
    }

    fun navigateToFolderShowCase() {
        mainViewModel.navigateTo(NestedRoutes.FOLDER_SHOW_CASE.name)
    }

    fun navigateToTasksScreen() {
        mainViewModel.navigateTo(MainRoutes.TASKS.name)
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
    fun getSelectedDate() : LocalDate = LocalDate.of(
        mainViewModel.getCurrentDate().currentYear,
        mainViewModel.getCurrentDate().currentMonth,
        uiState.selectedDayInCurrentDate
    )
    fun noTasksInCurrentDayExists() = getTasksPerFolderInSelectedDay().isEmpty()

    fun getTasksPerFolderInSelectedDay(): HashMap<FolderTable, MutableList<TaskTable>> {
        Log.d("DEBUGGING : ", "getTasksPerFolderInSelectedDay: ${getTasksPerFolder()}")
        val date = getSelectedDate()
        val list = getTasksPerFolder()

        val returnValue = HashMap<FolderTable, MutableList<TaskTable>>()
        for ((folder, tasks) in list) {
            returnValue[folder] = ArrayList(tasks)
        }

        for (folder in list.keys) {
            val tasksToRemove = mutableListOf<TaskTable>()

            for (task in list[folder]!!)
                if (!task.date.isEqual(date))
                    tasksToRemove.add(task)

            returnValue[folder]!!.removeAll(tasksToRemove)
        }

        return returnValue
    }


}