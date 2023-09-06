package com.example.roomtodolist.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable

@Composable
fun TasksPerFolderCard(
    folder: FolderTable,
    tasks: List<TaskTable>,
    onTaskSelect: (TaskTable) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24f),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = folder.name,
                    color = Color(folder.color),
                    modifier = Modifier.align(Alignment.CenterStart),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.outlined_double_line_setting_icon),
                    contentDescription = null,
                    tint = Color(folder.color),
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            Column {
                tasks.forEach {
                    TaskCard(taskTable = it, onSelect = onTaskSelect)
                }
            }
        }
    }
}