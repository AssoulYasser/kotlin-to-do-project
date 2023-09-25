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
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.domain.calendar.CalendarSystem
import com.example.roomtodolist.ui.navigation.MainRoutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

class MainViewModel(
    private val databaseRepository: DatabaseRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : ViewModel() {

    private var navState by mutableStateOf(MainNavState())

    private val taskManager = TaskManager(databaseRepository.folderDao, databaseRepository.taskDao)

    private val profileManager = ProfileManager(sharedPreferencesRepository)

    @RequiresApi(Build.VERSION_CODES.O)
    private val calendarSystem: CalendarSystem = CalendarSystem()

    val tasks : Map<Long, TaskTable>
        get() = try {
            taskManager.tasks.toMap()
        } catch (e: Exception) {
            mapOf()
        }

    val folders : Map<Long, FolderTable>
        get() = try {
            taskManager.folders
        } catch (e: Exception) {
            mapOf()
        }

    val tasksPerFolder : Map<FolderTable, MutableList<TaskTable>>
        get() = try {
            taskManager.tasksPerFolder.toMap()
        } catch (e: Exception) {
            mapOf()
        }

    val username: String
        get() = profileManager.usernameState.toString()

    val profilePicture: Uri?
        get() = profileManager.profilePictureState

    val isDarkTheme: Boolean
        get() = profileManager.isDarkThemeState

    val taskToUpdate : TaskTable?
        get() = taskManager.taskToUpdate

    val folderToUpdate : FolderTable?
        get() = taskManager.folderToUpdate

    lateinit var windowSizeClass: WindowSizeClass
        private set

    fun start() {
        viewModelScope.launch(Dispatchers.IO) {
            taskManager.init()
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
        taskManager.setFolderToUpdate(folder)
    }

    fun clearFolderToUpdate() {
        taskManager.clearFolderToUpdate()
    }

    fun addFolder(folder: FolderTable) {
        viewModelScope.launch(Dispatchers.IO) {
            taskManager.addFolder(folder)
        }
    }

    fun getFolderColors() = taskManager.getFolderColors()

    fun getFolderAssets() = taskManager.getFolderAssets()

    fun updateFolder(folder: FolderTable) {
        viewModelScope.launch(Dispatchers.IO) {
            taskManager.updateFolder(folder)
        }
    }

    fun deleteFolder(folder: FolderTable) {
        viewModelScope.launch(Dispatchers.IO) {
            taskManager.deleteFolder(folder)
        }
    }

    fun setTaskToUpdate(task: TaskTable) {
        taskManager.setTaskToUpdate(task)
    }

    fun clearTaskToUpdate() {
        taskManager.clearTaskToUpdate()
    }

    fun addTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) {
            taskManager.addTask(task)
        }
    }

    fun updateTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) {
            taskManager.updateTask(task)
        }
    }

    fun deleteTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) {
            taskManager.deleteTask(task)
        }
    }

    fun selectTask(taskTable: TaskTable) {
        viewModelScope.launch (Dispatchers.IO){
            taskManager.selectTask(taskTable, this.coroutineContext.job)
        }
    }

    fun setUsername(name: String?) {
        profileManager.setUsername(name)
    }

    fun setProfilePicture(uri: Uri?) {
        profileManager.setProfilePicture(uri)
    }

    fun setIsDarkMode(isDark: Boolean) {
        profileManager.setIsDarkMode(isDark)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDaysOfTheWeek() = calendarSystem.getWeeklyCalendar(LocalDate.now().year, LocalDate.now().month, LocalDate.now().dayOfMonth)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthForFiveWeeks(month: Month, year: Int) = calendarSystem.getMonthlyCalendar(
        year = year,
        month = month
    )

    fun getBitmap(context: Context, drawable: Int, color: Int) : Bitmap {

        if (drawable == 0) return colorizeBitmap(context, getFolderAssets().keys.first(), getFolderAssets().values.first(), color)

        val primaryAsset = getFolderAssets()[drawable]
        return if (primaryAsset == null) {
            colorizeBitmap(context, getFolderAssets().keys.first(), getFolderAssets().values.first(), color)
        } else {
            colorizeBitmap(context, drawable, primaryAsset, color)
        }

    }

    private fun colorizeBitmap(context: Context, primaryAsset: Int, secondaryAsset: Int, color: Int): Bitmap {
        val primaryDrawable: Drawable? = ContextCompat.getDrawable(context, primaryAsset)
        val secondaryDrawable: Drawable? = ContextCompat.getDrawable(context, secondaryAsset)

        val wrappedDrawable = secondaryDrawable?.let { DrawableCompat.wrap(it) }
        wrappedDrawable?.setTint(color)

        val width = primaryDrawable?.intrinsicWidth ?: (0 + secondaryDrawable?.intrinsicWidth!!)
        val height = primaryDrawable?.intrinsicHeight ?: (0 + secondaryDrawable?.intrinsicHeight!!)

        val combinedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(combinedBitmap)

        primaryDrawable?.setBounds(0, 0, primaryDrawable.intrinsicWidth, primaryDrawable.intrinsicHeight)
        primaryDrawable?.draw(canvas)

        secondaryDrawable?.setBounds(0, 0, secondaryDrawable.intrinsicWidth, height)
        secondaryDrawable?.draw(canvas)

        return combinedBitmap
    }
}