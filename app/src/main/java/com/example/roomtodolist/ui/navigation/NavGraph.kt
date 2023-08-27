package com.example.roomtodolist.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.roomtodolist.ui.screens.AddTaskScreen
import com.example.roomtodolist.ui.screens.CalendarScreen
import com.example.roomtodolist.ui.screens.HomeScreen
import com.example.roomtodolist.ui.screens.TaskScreen

@Composable
fun NavGraph(navHostController: NavHostController, windowSizeClass: WindowSizeClass) {
    NavHost(navController = navHostController, startDestination = NavDestination.Home.rout) {
        composable(route = NavDestination.Home.rout) {
            HomeScreen()
        }
        composable(route = NavDestination.Task.rout) {
            TaskScreen()
        }
        composable(route = NavDestination.Calendar.rout) {
            CalendarScreen()
        }
        composable(route = NavDestination.AddTask.rout) {
            AddTaskScreen()
        }
    }
}

fun NavHostController.navigateTo(destination: String) {
    val navController = this
    navController.navigate(destination) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}