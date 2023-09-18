package com.example.roomtodolist.domain.main_activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.roomtodolist.data.DatabaseRepository
import com.example.roomtodolist.data.SharedPreferencesRepository
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.folder.folderAssets
import com.example.roomtodolist.data.folder.folderColors
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.domain.calendar.CalendarSystem
import com.example.roomtodolist.ui.navigation.MainRoutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

class MainViewModel(
    private val databaseRepository: DatabaseRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : ViewModel() {

    private var navState by mutableStateOf(MainNavState())

    var uiState by mutableStateOf(MainUiState())
        private set

    private val calendarSystem: CalendarSystem

    init {
        @RequiresApi(Build.VERSION_CODES.O)
        calendarSystem = CalendarSystem()

        uiState = uiState.copy(
            profilePicture = if (sharedPreferencesRepository.getProfilePicture() == null) null
                            else Uri.parse(sharedPreferencesRepository.getProfilePicture()),
            username = sharedPreferencesRepository.getUsername(),
            isDarkTheme = sharedPreferencesRepository.isDarkMode()
        )

    }

    lateinit var windowSizeClass: WindowSizeClass
        private set

    fun start() {
        val folders = hashMapOf<Long, FolderTable>()
        val tasks = hashMapOf<Long, TaskTable>()
        val tasksPerFolder = hashMapOf<FolderTable, MutableList<TaskTable>>()

        viewModelScope.launch(Dispatchers.IO) {
            for (folder in databaseRepository.folderDao.getFolders()) {
                val tasksInCurrentFolder =
                    databaseRepository.taskDao.getTasksFromFolder(folder.id!!)
                folder.taskCounts = tasksInCurrentFolder.count()
                folders[folder.id] = folder
                tasksPerFolder[folder] = mutableListOf()
                tasksPerFolder[folder]!!.addAll(tasksInCurrentFolder)
            }
            for (task in databaseRepository.taskDao.getTasks()) {
                tasks[task.id!!] = task
            }
            uiState = uiState.copy(
                folders = folders,
                tasks = tasks,
                tasksPerFolder = tasksPerFolder
            )
        }
    }

    fun setNavHostController(navHostController: NavHostController) {
        navState = navState.copy(navHostController = navHostController)
    }

    fun getNavHostController() = navState.navHostController

    fun navigateTo(destination: String) {
        if (navState.currentDestination == destination)
            return

        else if (destination == MainRoutes.HOME.name) {
            navState.navigationStack.clear()
            navState = navState.copy(currentDestination = destination)
        }

        else if (navState.navigationStack.contains(destination)) {
            while (navState.navigationStack.contains(destination)) {
                navState.navigationStack.pop()
            }
        }

        else {
            navState.navigationStack.push(navState.currentDestination)
            navState = navState.copy(currentDestination = destination)
        }

        navigateTo()
    }

    fun navigateBack() {
        if (navState.navigationStack.isNotEmpty())
            navState = navState.copy(currentDestination = navState.navigationStack.pop())
        navigateTo()
    }

    private fun navigateTo() {
        navState.navHostController?.navigate(navState.currentDestination) {
            popUpTo(navState.navHostController?.graph?.findStartDestination()!!.id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun setWindowSizeClass(windowSizeClass: WindowSizeClass) {
        this.windowSizeClass = windowSizeClass
    }

    fun setFolderToUpdate(folder: FolderTable) {
        uiState = uiState.copy(folderToUpdate = folder)
    }

    fun clearFolderToUpdate() {
        uiState = uiState.copy(folderToUpdate = null)
    }

    fun addFolder(folder: FolderTable) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = databaseRepository.folderDao.addFolder(folder)
            val newFolder = folder.copy(id = id)
            updateFolderState(newFolder, Operation.ADD)
            updateTasksPerFolderKeyState(id, Operation.ADD)
        }
    }

    fun getFolderColors() = folderColors

    fun getFolderAssets() = folderAssets

    fun updateFolder(folder: FolderTable) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.folderDao.updateFolder(folder)
            updateFolderState(folder, Operation.CHANGE)
            updateTasksPerFolderKeyState(folder.id!!, Operation.CHANGE)
        }
    }

    fun deleteFolder(folder: FolderTable) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.folderDao.deleteFolder(folder.id!!)
            updateTasksPerFolderKeyState(folder.id, Operation.DELETE)
            updateFolderState(folder, Operation.DELETE)
        }
    }

    fun setTaskToUpdate(task: TaskTable) {
        uiState = uiState.copy(taskToUpdate = task)
    }

    fun clearTaskToUpdate() {
        uiState = uiState.copy(taskToUpdate = null)
    }

    fun addTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = databaseRepository.taskDao.addTask(task)
            Log.d(TAG, "addTask: $id")
            val newTask = task.copy(id = id)
            updateTaskState(newTask, Operation.ADD)
            updateTasksPerFolderValueState(taskId = newTask.id!!, operation = Operation.ADD)
        }
    }

    fun updateTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.taskDao.updateTask(task)
            updateTaskState(task, Operation.CHANGE)
            updateTasksPerFolderValueState(task.id!!, Operation.CHANGE)
        }
    }

    fun deleteTask(task: TaskTable) {
        viewModelScope.launch {
            databaseRepository.taskDao.deleteTask(taskId = task.id!!)
            updateTasksPerFolderValueState(task.id, Operation.DELETE)
            updateTaskState(task, Operation.DELETE)
        }
    }

    private fun updateTaskState(task: TaskTable, operation: Operation) {
        if (task.id == null)
            throw Exception("TASK ID SHOULD NOT BE NULL")
        when (operation) {
            Operation.ADD, Operation.CHANGE -> {
                uiState.tasks[task.id] = task
            }
            Operation.DELETE -> {
                uiState.tasks.remove(task.id)
            }
        }
    }

    private fun updateFolderState(folder: FolderTable, operation: Operation) {
        if (folder.id == null)
            throw Exception("FOLDER ID SHOULD NOT BE NULL")
        when (operation) {
            Operation.ADD, Operation.CHANGE -> {
                uiState.folders[folder.id] = folder
            }
            Operation.DELETE -> {
                uiState.folders.remove(folder.id)
            }
        }
    }

    private fun updateTasksPerFolderKeyState(folderId: Long, operation: Operation) {
        val folder = uiState.folders[folderId] ?: throw Exception("FOLDER $folderId DO NOT EXISTS")
        when (operation) {
            Operation.ADD -> {
                uiState.tasksPerFolder[folder] = mutableListOf()
            }
            Operation.CHANGE -> {
                for (eachFolder in uiState.tasksPerFolder.keys) {
                    if (eachFolder.id == folderId) {
                        val list = uiState.tasksPerFolder[eachFolder]!!
                        uiState.tasksPerFolder.remove(eachFolder)
                        uiState.tasksPerFolder[folder] = list
                        return
                    }
                }
            }
            Operation.DELETE -> {
                uiState.tasksPerFolder.remove(folder)
            }
        }
    }

    private fun updateTasksPerFolderValueState(taskId: Long, operation: Operation) {
        val task = uiState.tasks[taskId] ?: throw Exception("TASK $taskId DO NOT EXISTS")
        val folder = uiState.folders[task.folder] ?: throw Exception("FOLDER ${task.folder} DO NOT EXISTS")
        when (operation) {
            Operation.ADD -> {
                uiState.tasksPerFolder[folder]!!.add(task)
            }
            Operation.CHANGE -> {
                val list = uiState.tasksPerFolder[folder]!!
                for (index in list.indices) {
                    if (list[index].id == task.id) {
                        if (list[index].folder == task.folder)
                            uiState.tasksPerFolder[folder]!![index] = task
                        else
                            uiState.tasksPerFolder[folder]!!.remove(list[index])

                        return
                    }
                }
                uiState.tasksPerFolder[folder]!!.add(task)
            }
            Operation.DELETE -> {
                uiState.tasksPerFolder[folder]!!.remove(task)
            }
        }
    }

    fun setUsername(name: String?) {
        uiState = uiState.copy(username = name)
        sharedPreferencesRepository.setUsername(name ?: "UNNAMED")
    }

    fun setProfilePicture(uri: Uri?) {
        uiState = uiState.copy(profilePicture = uri)
        sharedPreferencesRepository.setProfilePicture(uri.toString())
    }

    fun setIsDarkMode(isDark: Boolean) {
        uiState = uiState.copy(isDarkTheme = isDark)
        sharedPreferencesRepository.setMode(isDark)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDaysOfTheWeek() = calendarSystem.getWeeklyCalendar(LocalDate.now().year, LocalDate.now().month, LocalDate.now().dayOfMonth)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthForFiveWeeks(month: Month, year: Int) = calendarSystem.getMonthlyCalendar(
        year = year,
        month = month
    )

    fun getBitmap(context: Context, drawable: Int, color: Int) : Bitmap {

        if (drawable == 0) return colorizeBitmap(context, folderAssets.values.first(), folderAssets.keys.first(), color)

        val primaryAsset = folderAssets[drawable]
        return if (primaryAsset == null) {
            colorizeBitmap(context, folderAssets.values.first(), folderAssets.keys.first(), color)
        } else {
            colorizeBitmap(context, drawable, primaryAsset, color)
        }

    }

    private fun colorizeBitmap(context: Context, primaryAsset: Int, secondaryAsset: Int, color: Int): Bitmap {
        // Load the XML drawables into Drawable objects
        val primaryDrawable: Drawable? = ContextCompat.getDrawable(context, primaryAsset)
        val secondaryDrawable: Drawable? = ContextCompat.getDrawable(context, secondaryAsset)

        val wrappedDrawable = secondaryDrawable?.let { DrawableCompat.wrap(it) }
        wrappedDrawable?.setTint(color)

        // Define the width and height of the resulting bitmap
        val width = primaryDrawable?.intrinsicWidth ?: (0 + secondaryDrawable?.intrinsicWidth!!)
        val height = primaryDrawable?.intrinsicHeight ?: (0 + secondaryDrawable?.intrinsicHeight!!)

        // Create a Bitmap with the specified width and height
        val combinedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        // Create a Canvas to draw on the Bitmap
        val canvas = Canvas(combinedBitmap)

        // Draw the first drawable onto the canvas
        primaryDrawable?.setBounds(0, 0, primaryDrawable.intrinsicWidth, primaryDrawable.intrinsicHeight)
        primaryDrawable?.draw(canvas)

        // Draw the second drawable onto the canvas, below the first one
        secondaryDrawable?.setBounds(0, 0, secondaryDrawable.intrinsicWidth, height)
        secondaryDrawable?.draw(canvas)

        return combinedBitmap
    }

    private enum class Operation {
        ADD,
        CHANGE,
        DELETE
    }

}