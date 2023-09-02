package com.example.roomtodolist.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.roomtodolist.ui.screens.MainViewModel
import com.example.roomtodolist.ui.screens.addtask.AddTaskViewModel
import com.example.roomtodolist.ui.screens.calendar.CalendarViewModel
import com.example.roomtodolist.ui.screens.home.HomeViewModel
import com.example.roomtodolist.ui.screens.tasks.TasksViewModel

const val TAG = "DEBUGGING : "

@Composable
fun NavGraph(mainViewModel: MainViewModel) {

    val navHostController = mainViewModel.navHostController

    val homeViewModel = viewModel<HomeViewModel>()
    val tasksViewModel = viewModel<TasksViewModel>()
    val calendarViewModel = viewModel<CalendarViewModel>()
    val addTaskViewModel = viewModel<AddTaskViewModel>()

    val screenHash = hashMapOf<ScreenRoute, ViewModel?>(
        MainRoutes.HOME to homeViewModel,
        MainRoutes.TASKS to tasksViewModel,
        MainRoutes.CALENDAR to calendarViewModel,
        MainRoutes.ADD_TASK to addTaskViewModel
    )

    NavHost(navController = navHostController, startDestination = Routes.Home.route.name) {
        navigationList.forEach { route ->
            navigation(
                startDestination = route.mainDestination.route.name,
                route = route.route.name
            ) {
                composable(route = route.mainDestination.route.name) {
                    route.mainDestination.also {
                        val viewModel = screenHash[it.route]
                        Log.d(TAG, "NavGraph: $viewModel")
                        it.screen(viewModel)
                    }
                }
                route.nestedDestination?.forEach { nestedRoute ->
                    composable(route = nestedRoute.rout.name){

                    }
                }
            }
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