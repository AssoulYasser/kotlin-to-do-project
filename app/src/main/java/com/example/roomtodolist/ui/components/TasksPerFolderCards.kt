package com.example.roomtodolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable

@Composable
fun TasksPerFolderCards(
    tasksPerFolder: HashMap<FolderTable, MutableList<TaskTable>>,
    noTaskExists: Boolean = true,
    addTask: () -> Unit,
    onClick: (TaskTable) -> Unit,
    onSelectTask: (TaskTable) -> Unit
) {
    if (noTaskExists)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(20f)
                ),
            contentAlignment = Alignment.Center
        ) {
            EmptyElements(
                elementName = "Task",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                onCreateElement = addTask
            )
        }
    else {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (folder in tasksPerFolder.keys) {
                if (tasksPerFolder.containsKey(folder) && tasksPerFolder[folder]!!.isNotEmpty()) {
                    TasksPerFolderCard(
                        folder = folder,
                        tasks = tasksPerFolder[folder]!!,
                        onClick = onClick,
                        onTaskSelect = onSelectTask
                    )
                }
            }
        }
    }
}