package com.example.roomtodolist.ui.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomtodolist.R
import com.example.roomtodolist.data.task.TaskTable

@Composable
fun TaskCard(
    taskTable: TaskTable,
    color: Color = MaterialTheme.colorScheme.onBackground,
    onClick: (TaskTable) -> Unit,
    onSelect: (TaskTable) -> Unit,
    isSelected: Boolean
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onClick(taskTable) },
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            shape = CircleShape,
            border = BorderStroke(1.2.dp, color),
            color = if (isSelected) color else Color.Transparent,
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .clickable {
                    Toast.makeText(
                        context,
                        if (isSelected) "Task marked as uncompleted" else "task marked as completed",
                        Toast.LENGTH_SHORT
                    ).show()
                    onSelect(taskTable)
                },
            content = {
                if (isSelected)
                    Icon(
                        painter = painterResource(id = R.drawable.check_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                    )
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = taskTable.title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge,
                textDecoration = if (isSelected) TextDecoration.LineThrough else TextDecoration.None
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
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(id = R.drawable.outlined_document_favorite_icon),
                    contentDescription = null,
                    tint = taskTable.priority.getPriorityColor(),
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = taskTable.priority.name,
                    color = taskTable.priority.getPriorityColor(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        } 

    }
}