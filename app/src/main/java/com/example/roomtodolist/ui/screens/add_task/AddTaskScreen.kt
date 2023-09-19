package com.example.roomtodolist.ui.screens.add_task

import android.util.Log
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

@Composable
fun AddTaskScreen(
    addTaskViewModel: AddTaskViewModel
) {
    val context = LocalContext.current
    Container(actionBar = {
        ActionBar {
            addTaskViewModel.navigateBack()
            addTaskViewModel.clear()
        }
    }) {
        Spacer(modifier = Modifier)
        TaskTitle(
            value = { addTaskViewModel.taskTitleState }
        ) { addTaskViewModel.setTaskTitle(it) }
        PriorityCard(
            selectedPriority = { addTaskViewModel.taskPriorityState }
        ) { addTaskViewModel.setPriority(it) }
        DateCard(
            date = { addTaskViewModel.taskDateState },
            time = { addTaskViewModel.taskTimeState },
            onDateChange = {
                addTaskViewModel.setDate(it)
            }
        ) {
            addTaskViewModel.setTime(it)
        }
        FoldersCard(
            selectedFolder = { addTaskViewModel.taskFolderState },
            folders = addTaskViewModel.getFolders(),
            onAddFolder = { addTaskViewModel.navigateToAddFolderScreen() },
            onSelectFolder = { addTaskViewModel.setFolder(it) }
        )
        SavingValidationButtons(
            onSave = {
                if (addTaskViewModel.isReadyToSave()) {
                    addTaskViewModel.save()
                    addTaskViewModel.navigateBack()
                    addTaskViewModel.showSuccessMessage(context = context)
                    addTaskViewModel.clear()
                } else {
                    addTaskViewModel.showErrorMessage(context = context)
                }
            },
            onCancel = {
                addTaskViewModel.clear()
                addTaskViewModel.navigateBack()
            }
        )
        Spacer(modifier = Modifier)
    }

}


