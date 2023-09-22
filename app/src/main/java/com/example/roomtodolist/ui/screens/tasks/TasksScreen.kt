package com.example.roomtodolist.ui.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomtodolist.R
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.TasksPerFolderCards
import com.example.roomtodolist.ui.components.defaultButtonShape
import com.example.roomtodolist.ui.components.defaultFilledButtonColors

const val TAG = "DEBUGGING : "
@Composable
fun TasksScreen(
    tasksViewModel: TasksViewModel
) {
    Container(actionBar = {
        ActionBar(title = "Tasks") {
            tasksViewModel.navigateBack()
        }
    }) {
        Spacer(modifier = Modifier)
        AddTaskButton { tasksViewModel.navigateToAddTaskScreen() }
        TasksPerFolderCards(
            tasksPerFolder = { tasksViewModel.getTasksPerFolder() },
            noTaskExists = tasksViewModel.noTaskExists(),
            addTask = { tasksViewModel.navigateToAddTaskScreen() },
            isDark = tasksViewModel.isDarkMode(),
            onAdjustFolder = {
                tasksViewModel.setFolderToUpdate(it)
                tasksViewModel.navigateToFolderShowCase()
            },
            onClick = {
                tasksViewModel.setTaskToUpdate(it)
                tasksViewModel.navigateToTaskShowCase()
            }
        ) {}
    }
}

@Composable
fun AddTaskButton(onAddTask: () -> Unit) {
    Button(
        onClick = onAddTask,
        colors = defaultFilledButtonColors(),
        shape = defaultButtonShape()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outlined_add_icon),
                contentDescription = null,
                tint = Color.White
            )
            Text(
                text = "Add Task",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}
