package com.example.roomtodolist.ui.screens.taskshowcase

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
import com.example.roomtodolist.ui.components.SavingValidationButtons
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
            value = taskShowCaseViewModel.uiState.taskTitle,
            onValueChange = { taskShowCaseViewModel.updateTaskTitle(it) }
        )
        PriorityCard(
            selectedPriority = taskShowCaseViewModel.uiState.taskPriority,
            onSelectPriority = { taskShowCaseViewModel.updatePriority(it) }
        )
        DateCard(
            date = taskShowCaseViewModel.uiState.date,
            time = taskShowCaseViewModel.uiState.time,
            onDateChange = {
                taskShowCaseViewModel.updateDate(it)
            },
            onTimeChange = {
                taskShowCaseViewModel.updateTime(it)
            }
        )
        FoldersCard(
            selectedFolder = taskShowCaseViewModel.uiState.folder,
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



