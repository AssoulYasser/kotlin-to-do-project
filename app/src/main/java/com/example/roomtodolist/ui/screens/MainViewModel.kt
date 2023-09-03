package com.example.roomtodolist.ui.screens

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.roomtodolist.data.Repository
import com.example.roomtodolist.data.folder.FolderTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    val repository: Repository,
) : ViewModel() {

    var uiState = MainUiState()
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val folders = repository.folderDao.getFolders()
            uiState = uiState.copy(folders = folders)
            Log.d(TAG, "UISTATE: ${uiState.folders}")
        }
    }

    lateinit var navHostController: NavHostController
        private set

    lateinit var windowSizeClass: WindowSizeClass
        private set

    fun setNavHostController(navHostController: NavHostController) {
        this.navHostController = navHostController
    }

    fun setWindowSizeClass(windowSizeClass: WindowSizeClass) {
        this.windowSizeClass = windowSizeClass
    }

    fun navigateTo(destination: String) {
        navHostController.navigate(destination) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateBack() {
        navHostController.popBackStack()
    }

    fun addFolder(folder: FolderTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.folderDao.addFolder(folder)
        }
    }

}