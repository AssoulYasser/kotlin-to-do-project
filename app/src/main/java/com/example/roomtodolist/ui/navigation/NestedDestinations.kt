package com.example.roomtodolist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.ui.screens.add_folder.AddFolderScreen
import com.example.roomtodolist.ui.screens.add_folder.AddFolderViewModel
import com.example.roomtodolist.ui.screens.folders.FoldersScreen
import com.example.roomtodolist.ui.screens.folders.FoldersViewModel
import com.example.roomtodolist.ui.screens.folder_show_case.FolderShowCaseScreen
import com.example.roomtodolist.ui.screens.folder_show_case.FolderShowCaseViewModel
import com.example.roomtodolist.ui.screens.profile.ProfileScreen
import com.example.roomtodolist.ui.screens.profile.ProfileViewModel
import com.example.roomtodolist.ui.screens.task_show_case.TaskShowCaseScreen
import com.example.roomtodolist.ui.screens.task_show_case.TaskShowCaseViewModel

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

    object Folders: NestedDestinations(
        route = NestedRoutes.FOLDERS,
        screen = { viewModel -> FoldersScreen(viewModel as FoldersViewModel) }
    )

    object Profile: NestedDestinations(
        route = NestedRoutes.PROFILE,
        screen = { viewModel -> ProfileScreen(viewModel as ProfileViewModel) }
    )
}

