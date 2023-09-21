package com.example.roomtodolist.domain.main_activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.roomtodolist.data.DatabaseRepository
import com.example.roomtodolist.data.SharedPreferencesRepository
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.folder.folderAssets
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.domain.calendar.CalendarSystem
import com.example.roomtodolist.ui.navigation.MainRoutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import java.time.Month

class MainViewModel(
    private val databaseRepository: DatabaseRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : ViewModel() {

    private var navState by mutableStateOf(MainNavState())

    var uiState by mutableStateOf(MainUiState())
        private set

    private val tasksManager = TasksManager(databaseRepository.taskDao)
    private val folderManager = FoldersManager(databaseRepository.folderDao)
    private val tasksPerFolderManager = TasksPerFolderManager()
    private val calendarSystem: CalendarSystem

    val tasks : Map<Long, TaskTable>
        get() = try {
            tasksManager.tasks.toMap()
        } catch (e: Exception) {
            mapOf()
        }

    val folders : Map<Long, FolderTable>
        get() = try {
            folderManager.folders.toMap()
        } catch (e: Exception) {
            mapOf()
        }

    val tasksPerFolder : Map<FolderTable, MutableList<TaskTable>>
        get() = try {
            tasksPerFolderManager.tasksPerFolder.toMap()
        } catch (e: Exception) {
            mapOf()
        }

    var isDarkTheme by mutableStateOf(false)



    init {
        @RequiresApi(Build.VERSION_CODES.O)
        calendarSystem = CalendarSystem()

        isDarkTheme = sharedPreferencesRepository.isDarkMode()

        uiState = uiState.copy(
            profilePicture = if (sharedPreferencesRepository.getProfilePicture() == null) null
                            else Uri.parse(sharedPreferencesRepository.getProfilePicture()),
            username = sharedPreferencesRepository.getUsername()
        )

    }

    lateinit var windowSizeClass: WindowSizeClass
        private set

    fun start() {
        viewModelScope.launch(Dispatchers.IO) {
            folderManager.start()
            tasksManager.start()
            if (!tasksPerFolderManager.isInitialized) {
                for (folder in folders) {
                    tasksPerFolderManager.updateTasksPerFolderKeyState(folder.value, Operation.ADD)
                }
                for (task in tasks) {
                    folders[task.value.folder]?.let {
                        tasksPerFolderManager.updateTasksPerFolderValueState(
                            task.value,
                            it,
                            Operation.ADD
                        )
                    }
                }
                tasksPerFolderManager.isInitialized = true
            }
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
                val newDestination = navState.navigationStack.pop()
                navState = navState.copy(currentDestination = newDestination)
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
        navState.navHostController?.navigate(navState.currentDestination)
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

    fun getFolderColors() = folderManager.colors

    fun getFolderAssets() = folderManager.assets

    fun addTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksManager.addTask(task)
            val folder = folders[task.folder]
            if (folder != null)
                tasksPerFolderManager.updateTasksPerFolderValueState(task, folder, Operation.ADD)
        }
    }

    fun updateTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksManager.updateTask(task)
            val folder = folders[task.folder]
            if (folder != null)
                tasksPerFolderManager.updateTasksPerFolderValueState(task, folder, Operation.CHANGE)
        }
    }

    fun deleteTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksManager.deleteTask(task)
            val folder = folders[task.folder]
            if (folder != null)
                tasksPerFolderManager.updateTasksPerFolderValueState(task, folder, Operation.DELETE)
        }
    }

    fun addFolder(folder: FolderTable) {
        viewModelScope.launch(Dispatchers.IO) {
            folderManager.addFolder(folder)
            tasksPerFolderManager.updateTasksPerFolderKeyState(folder, Operation.ADD)
        }
    }

    fun updateFolder(folder: FolderTable) {
        viewModelScope.launch(Dispatchers.IO) {
            folderManager.updateFolder(folder)
            tasksPerFolderManager.updateTasksPerFolderKeyState(folder, Operation.CHANGE)
        }
    }

    fun deleteFolder(folder: FolderTable) {
        viewModelScope.launch(Dispatchers.IO) {
            folderManager.deleteFolder(folder)
            tasksPerFolderManager.updateTasksPerFolderKeyState(folder, Operation.DELETE)
        }
    }

    fun setTaskToUpdate(task: TaskTable) {
        uiState = uiState.copy(taskToUpdate = task)
    }

    fun clearTaskToUpdate() {
        uiState = uiState.copy(taskToUpdate = null)
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
        isDarkTheme = isDark
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

}