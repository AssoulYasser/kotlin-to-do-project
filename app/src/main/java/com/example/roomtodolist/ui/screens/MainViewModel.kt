package com.example.roomtodolist.ui.screens

import android.content.res.Configuration
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.roomtodolist.database.Repository

class MainViewModel(
    val repository: Repository,
) : ViewModel() {

    lateinit var navHostController: NavHostController
        private set

    fun setWindowSizeClass(windowSizeClass: WindowSizeClass) {
        this.windowSizeClass = windowSizeClass
    }

    lateinit var windowSizeClass: WindowSizeClass
        private set

    fun setNavHostController(navHostController: NavHostController) {
        this.navHostController = navHostController
    }



}