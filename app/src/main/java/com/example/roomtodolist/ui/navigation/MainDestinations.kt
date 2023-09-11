package com.example.roomtodolist.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.R
import com.example.roomtodolist.ui.screens.add_task.AddTaskScreen
import com.example.roomtodolist.ui.screens.add_task.AddTaskViewModel
import com.example.roomtodolist.ui.screens.calendar.CalendarScreen
import com.example.roomtodolist.ui.screens.calendar.CalendarViewModel
import com.example.roomtodolist.ui.screens.home.HomeScreen
import com.example.roomtodolist.ui.screens.home.HomeViewModel
import com.example.roomtodolist.ui.screens.tasks.TasksScreen
import com.example.roomtodolist.ui.screens.tasks.TasksViewModel

sealed class MainDestinations(
    val route: MainRoutes,
    val title: String,
    val icon: NavIcon,
    var screen: @Composable (viewModel: ViewModel?) -> Unit
) {
    object Home : MainDestinations(
        route = MainRoutes.HOME,
        title = "Home",
        icon = NavIcon(R.drawable.filled_home_icon, R.drawable.outlined_home_icon),
        screen = { viewModel ->
            HomeScreen(viewModel as HomeViewModel)
        }
    )
    object Task : MainDestinations(
        route = MainRoutes.TASKS,
        title = "Tasks",
        icon = NavIcon(R.drawable.filled_task_icon, R.drawable.outlined_task_icon),
        screen = {
            TasksScreen(it as TasksViewModel)
        }
    )
    @RequiresApi(Build.VERSION_CODES.O)
    object Calendar : MainDestinations(
        route = MainRoutes.CALENDAR,
        title = "Calendar",
        icon = NavIcon(R.drawable.filled_calendar_icon, R.drawable.outlined_calendar_icon),
        screen = {
            CalendarScreen(it as CalendarViewModel)
        }
    )
    object AddTask : MainDestinations(
        route = MainRoutes.ADD_TASK,
        title = "AddTask",
        icon = NavIcon(R.drawable.filled_add_icon, R.drawable.outlined_add_icon),
        screen = {
            AddTaskScreen(it as AddTaskViewModel)
        }
    )
}

