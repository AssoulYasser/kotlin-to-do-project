package com.example.roomtodolist.ui.navigation

sealed class Routes(
    val route: RootRoutes,
    val mainDestination: MainDestinations,
    val nestedDestination: MutableList<NestedDestinations>? = null
) {
    object Home : Routes(
        route = RootRoutes.HOME_ROOT,
        mainDestination = MainDestinations.Home,
        nestedDestination = mutableListOf(
            NestedDestinations.AddFolder,
            NestedDestinations.FolderShowCase,
            NestedDestinations.Folders
        )
    )
    object Tasks : Routes(
        route = RootRoutes.TASK_ROOT,
        mainDestination = MainDestinations.Task,
        nestedDestination = mutableListOf(NestedDestinations.TaskShowCase)
    )
    object Calendar : Routes(
        route = RootRoutes.CALENDAR_ROOT,
        mainDestination = MainDestinations.Calendar,
        nestedDestination = null
    )
    object AddTask : Routes(
        route = RootRoutes.ADD_TASK_ROOT,
        mainDestination = MainDestinations.AddTask,
        nestedDestination = mutableListOf(NestedDestinations.AddFolder)
    )
}
