package com.example.roomtodolist.ui.screens.home

import android.annotation.SuppressLint
import android.net.Uri
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
import com.example.roomtodolist.domain.main_activity.MainViewModel
import java.time.LocalDate

@SuppressLint("NewApi")
class HomeViewModel(private val mainViewModel: MainViewModel) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

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

    fun navigateToFoldersScreen() {
        mainViewModel.navigateTo(NestedRoutes.FOLDERS.name)
    }

    fun navigateToProfileScreen() {
        mainViewModel.navigateTo(NestedRoutes.PROFILE.name)
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

    fun isDarkMode() = mainViewModel.isDarkTheme
    fun isCompactWidth() = mainViewModel.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    fun getDaysOfWeek() = mainViewModel.getDaysOfTheWeek()
    fun getFolders() : List<FolderTable> = mainViewModel.folders.values.toList()
    private fun getTasksPerFolder(): Map<FolderTable, MutableList<TaskTable>> = mainViewModel.tasksPerFolder
    private fun getSelectedDate() : LocalDate = LocalDate.of(
        LocalDate.now().year,
        LocalDate.now().month,
        uiState.selectedDayInCurrentDate
    )

    fun getTasksPerFolderInSelectedDay(): HashMap<FolderTable, MutableList<TaskTable>> {
        val date = getSelectedDate()
        val list = getTasksPerFolder()

        val returnValue = HashMap<FolderTable, MutableList<TaskTable>>()
        for ((folder, tasks) in list) {
            returnValue[folder] = ArrayList()
            for (task in tasks) {
                if (task.date.isEqual(date))
                    returnValue[folder]!!.add(task)
            }
        }

        return returnValue
    }

    fun getProfilePicture() : Uri? = mainViewModel.profilePicture

    fun getUsername() : String? = mainViewModel.username
    fun selectTask(taskTable: TaskTable) {
        mainViewModel.selectTask(taskTable)
    }


}