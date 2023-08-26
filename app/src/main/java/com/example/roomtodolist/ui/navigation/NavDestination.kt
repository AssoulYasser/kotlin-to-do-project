package com.example.roomtodolist.ui.navigation

import com.example.roomtodolist.R

sealed class NavDestination(val rout:String, val title: String, val icon: NavIcon) {
    object Home : NavDestination(
        "home",
        "Home",
        NavIcon(R.drawable.filled_home_icon, R.drawable.outlined_home_icon)
    )
    object Task : NavDestination(
        "task",
        "Tasks",
        NavIcon(R.drawable.filled_add_icon, R.drawable.outlined_task_icon)
    )
    object Calendar : NavDestination(
        "calendar",
        "Calendar",
        NavIcon(R.drawable.filled_calendar_icon, R.drawable.outlined_calendar_icon)
    )
    object AddTask : NavDestination(
        "addTask",
        "AddTask",
        NavIcon(R.drawable.filled_add_icon, R.drawable.outlined_add_icon)
    )
}

data class NavIcon(
    val selected: Int,
    val unselected: Int
)