package com.example.roomtodolist.ui.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.roomtodolist.ui.components.TasksPerFolderCards
import com.example.roomtodolist.ui.components.defaultButtonShape
import com.example.roomtodolist.ui.components.defaultFilledButtonColors

const val TAG = "DEBUGGING : "
@Composable
fun TasksScreen(
    tasksViewModel: TasksViewModel
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ActionBar(title = "Tasks") {
            tasksViewModel.navigateBack()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier)
            AddTaskButton { tasksViewModel.navigateToAddTaskScreen() }
            TasksPerFolderCards(
                tasksPerFolder = tasksViewModel.getTasksPerFolder(),
                addTask = { tasksViewModel.navigateToAddTaskScreen() },
                onSelectTask = {}
            )
        }

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
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
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
                fontSize = 18.sp
            )
        }
    }
}
