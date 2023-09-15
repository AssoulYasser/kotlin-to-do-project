package com.example.roomtodolist.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.roomtodolist.domain.main_activity.MainViewModel
import com.example.roomtodolist.ui.screens.add_folder.AddFolderViewModel
import com.example.roomtodolist.ui.screens.add_task.AddTaskViewModel
import com.example.roomtodolist.ui.screens.calendar.CalendarViewModel
import com.example.roomtodolist.ui.screens.folders.FoldersViewModel
import com.example.roomtodolist.ui.screens.folder_show_case.FolderShowCaseViewModel
import com.example.roomtodolist.ui.screens.home.HomeViewModel
import com.example.roomtodolist.ui.screens.profile.ProfileViewModel
import com.example.roomtodolist.ui.screens.tasks.TasksViewModel
import com.example.roomtodolist.ui.screens.task_show_case.TaskShowCaseViewModel

@Composable
fun NavGraph(mainViewModel: MainViewModel) {

    val navHostController = mainViewModel.getNavHostController()

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
            @RequiresApi(Build.VERSION_CODES.O)
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

    val taskShowCaseViewModel = viewModel<TaskShowCaseViewModel>(
        factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaskShowCaseViewModel(
                    mainViewModel = mainViewModel
                ) as T
            }
        }
    )

    val folderShowCaseViewModel = viewModel<FolderShowCaseViewModel>(
        factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FolderShowCaseViewModel(
                    mainViewModel = mainViewModel
                ) as T
            }
        }
    )

    val foldersViewModel = viewModel<FoldersViewModel>(
        factory = object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FoldersViewModel(
                    mainViewModel = mainViewModel
                ) as T
            }
        }
    )

    val profileViewModel = viewModel<ProfileViewModel>(
        factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProfileViewModel(
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
        NestedRoutes.ADD_FOLDER to addFolderViewModel,
        NestedRoutes.TASK_SHOW_CASE to taskShowCaseViewModel,
        NestedRoutes.FOLDER_SHOW_CASE to folderShowCaseViewModel,
        NestedRoutes.FOLDERS to foldersViewModel,
        NestedRoutes.PROFILE to profileViewModel
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