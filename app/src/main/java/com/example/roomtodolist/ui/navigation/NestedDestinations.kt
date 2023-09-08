package com.example.roomtodolist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.ui.screens.addfolder.AddFolderScreen
import com.example.roomtodolist.ui.screens.addfolder.AddFolderViewModel
import com.example.roomtodolist.ui.screens.foldershowcase.FolderShowCaseScreen
import com.example.roomtodolist.ui.screens.foldershowcase.FolderShowCaseViewModel
import com.example.roomtodolist.ui.screens.taskshowcase.TaskShowCaseScreen
import com.example.roomtodolist.ui.screens.taskshowcase.TaskShowCaseViewModel

sealed class NestedDestinations(
    val route: NestedRoutes,
    var screen: @Composable (viewModel: ViewModel?) -> Unit
) {
    object AddFolder : NestedDestinations(
        route = NestedRoutes.ADD_FOLDER,
        screen = { viewModel -> AddFolderScreen(viewModel as AddFolderViewModel) }
    )

    object TaskShowCase : NestedDestinations(
        route = NestedRoutes.TASK_SHOW_CASE,
        screen = { viewModel -> TaskShowCaseScreen(viewModel as TaskShowCaseViewModel) }
    )

    object FolderShowCase : NestedDestinations(
        route = NestedRoutes.FOLDER_SHOW_CASE,
        screen = { viewModel -> FolderShowCaseScreen(viewModel as FolderShowCaseViewModel) }
    )
}
