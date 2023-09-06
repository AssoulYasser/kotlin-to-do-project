package com.example.roomtodolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.example.roomtodolist.data.task.TaskTable

@Composable
fun TaskCard(
    taskTable: TaskTable,
    color: Color = MaterialTheme.colorScheme.onBackground,
    selected: Boolean = false,
    onSelect: (TaskTable) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            shape = CircleShape,
            border = BorderStroke(1.2.dp, color),
            color = if (selected) color else Color.Transparent,
            modifier = Modifier
                .size(16.dp)
                .clickable { onSelect(taskTable) },
            content = {}
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = taskTable.title,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outlined_clock_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = taskTable.time.toString(),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 10.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.outlined_document_favorite_icon),
                    contentDescription = null,
                    tint = taskTable.priority.getPriorityColor(),
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = taskTable.priority.name,
                    color = taskTable.priority.getPriorityColor(),
                    fontSize = 10.sp
                )
            }
        } 

    }
}