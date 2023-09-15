package com.example.roomtodolist.domain.main_activity

import androidx.navigation.NavHostController
import com.example.roomtodolist.ui.navigation.MainRoutes
import java.util.Stack

data class NavState(
    val navHostController: NavHostController? = null,
    val navigationStack: Stack<String> = Stack<String>(),
    val currentDestination: String = MainRoutes.HOME.name
)
