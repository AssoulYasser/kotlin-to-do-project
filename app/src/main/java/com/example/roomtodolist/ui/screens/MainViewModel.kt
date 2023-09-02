package com.example.roomtodolist.ui.screens

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.roomtodolist.data.Repository

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