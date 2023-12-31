package com.example.roomtodolist.ui.screens.task_show_case

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.DateCard
import com.example.roomtodolist.ui.components.FoldersCard
import com.example.roomtodolist.ui.components.PriorityCard
import com.example.roomtodolist.ui.components.TaskTitle
import com.example.roomtodolist.ui.components.UpdatingValidationButtons

@Composable
fun TaskShowCaseScreen(
    taskShowCaseViewModel: TaskShowCaseViewModel
) {
    taskShowCaseViewModel.start()
    val context = LocalContext.current
    Container(actionBar = {
        ActionBar(title = "Update task") {
            taskShowCaseViewModel.navigateBack()
        }
    }) {
        Spacer(modifier = Modifier)
        TaskTitle(
            value = { taskShowCaseViewModel.uiState.taskTitle }
        ) { taskShowCaseViewModel.updateTaskTitle(it) }
        PriorityCard(
            selectedPriority = { taskShowCaseViewModel.uiState.taskPriority }
        ) { taskShowCaseViewModel.updatePriority(it) }
        DateCard(
            date = { taskShowCaseViewModel.uiState.taskDate },
            time = { taskShowCaseViewModel.uiState.taskTime },
            onDateChange = {
                taskShowCaseViewModel.updateDate(it)
            }
        ) {
            taskShowCaseViewModel.updateTime(it)
        }
        FoldersCard(
            selectedFolder = { taskShowCaseViewModel.uiState.taskFolder },
            folders = taskShowCaseViewModel.getFolders(),
            onAddFolder = { taskShowCaseViewModel.navigateToAddFolderScreen() },
            onSelectFolder = { taskShowCaseViewModel.updateFolder(it) }
        )
        UpdatingValidationButtons(
            onUpdate = {
                if (taskShowCaseViewModel.isReadyToSave()) {
                    taskShowCaseViewModel.save()
                    taskShowCaseViewModel.navigateToHomeScreen()
                    taskShowCaseViewModel.showSuccessMessage(context = context)
                    taskShowCaseViewModel.clear()
                } else {
                    taskShowCaseViewModel.showErrorMessage(context = context)
                }
            },
            onDelete = {
                taskShowCaseViewModel.delete()
                taskShowCaseViewModel.clear()
                taskShowCaseViewModel.navigateBack()
            },
            onCancel = {
                taskShowCaseViewModel.clear()
                taskShowCaseViewModel.navigateToHomeScreen()
            }
        )
        Spacer(modifier = Modifier)
    }
}



