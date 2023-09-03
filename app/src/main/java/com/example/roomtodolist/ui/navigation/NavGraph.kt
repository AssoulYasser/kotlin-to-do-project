package com.example.roomtodolist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.roomtodolist.ui.screens.MainViewModel
import com.example.roomtodolist.ui.screens.addfolder.AddFolderViewModel
import com.example.roomtodolist.ui.screens.addtask.AddTaskViewModel
import com.example.roomtodolist.ui.screens.calendar.CalendarViewModel
import com.example.roomtodolist.ui.screens.home.HomeViewModel
import com.example.roomtodolist.ui.screens.tasks.TasksViewModel

const val TAG = "DEBUGGING : "

@Composable
fun NavGraph(mainViewModel: MainViewModel) {

    val navHostController = mainViewModel.navHostController

    val homeViewModel = viewModel<HomeViewModel>(
        factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(
                    mainViewModel = mainViewModel
                ) as T
            }
        }
    )
    val tasksViewModel = viewModel<TasksViewModel>(
        factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TasksViewModel(
                    mainViewModel = mainViewModel
                ) as T
            }
        }
    )
    val calendarViewModel = viewModel<CalendarViewModel>(
        factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CalendarViewModel(
                    mainViewModel = mainViewModel
                ) as T
            }
        }
    )
    val addTaskViewModel = viewModel<AddTaskViewModel>(
        factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AddTaskViewModel(
                    mainViewModel = mainViewModel
                ) as T
            }
        }
    )
    val addFolderViewModel = viewModel<AddFolderViewModel>(
        factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AddFolderViewModel(
                    mainViewModel = mainViewModel
                ) as T
            }
        }
    )

    val viewModels = hashMapOf<ScreenRoute, ViewModel?>(
        MainRoutes.HOME to homeViewModel,
        MainRoutes.TASKS to tasksViewModel,
        MainRoutes.CALENDAR to calendarViewModel,
        MainRoutes.ADD_TASK to addTaskViewModel,
        NestedRoutes.ADD_FOLDER to addFolderViewModel
    )

    NavHost(navController = navHostController, startDestination = Routes.Home.route.name) {
        navigationList.forEach { route ->
            navigation(
                startDestination = route.mainDestination.route.name,
                route = route.route.name
            ) {
                composable(route = route.mainDestination.route.name) {
                    route.mainDestination.also {
                        val viewModel = viewModels[it.route]
                        it.screen(viewModel)
                    }
                }
                route.nestedDestination?.forEach { nestedRoute ->
                    composable(route = nestedRoute.route.name){
                        nestedRoute.also {
                            val viewModel = viewModels[it.route]
                            it.screen(viewModel)
                        }
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