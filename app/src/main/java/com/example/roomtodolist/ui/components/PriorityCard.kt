package com.example.roomtodolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomtodolist.R
import com.example.roomtodolist.data.task.TaskPriority

@Composable
fun PriorityCard(
    selectedPriority: () -> TaskPriority,
    onSelectPriority: (TaskPriority) -> Unit
) {
    val onBackground = MaterialTheme.colorScheme.onBackground
    fun getContainerColor(selectedButton: TaskPriority) : Color {
        return if (selectedButton == selectedPriority())
            selectedPriority().getPriorityColor()
        else
            Color.Transparent
    }
    fun getContentColor(selectedButton: TaskPriority) : Color
            = if (selectedPriority() == selectedButton) Color.White else onBackground
    ExpandableCard(icon = R.drawable.outlined_document_favorite_icon, title = "Set Priority") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onSelectPriority(TaskPriority.LOW) },
                colors = ButtonDefaults.buttonColors(containerColor = getContainerColor(TaskPriority.LOW)),
                border = BorderStroke(color = Color.Green, width = 2.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                shape = RoundedCornerShape(20f)
            ) {
                Text(
                    text = "Low",
                    style = MaterialTheme.typography.bodySmall,
                    color = getContentColor(TaskPriority.LOW)
                )
            }
            Button(
                onClick = { onSelectPriority(TaskPriority.MEDIUM) },
                colors = ButtonDefaults.buttonColors(containerColor = getContainerColor(TaskPriority.MEDIUM)),
                border = BorderStroke(color = Color.Blue, width = 2.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                shape = RoundedCornerShape(20f)
            ) {
                Text(
                    text = "Medium",
                    style = MaterialTheme.typography.bodySmall,
                    color = getContentColor(TaskPriority.MEDIUM)
                )
            }
            Button(
                onClick = { onSelectPriority(TaskPriority.HIGH) },
                colors = ButtonDefaults.buttonColors(containerColor = getContainerColor(TaskPriority.HIGH)),
                border = BorderStroke(color = Color.Red, width = 2.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                shape = RoundedCornerShape(20f)
            ) {
                Text(
                    text = "High",
                    style = MaterialTheme.typography.bodySmall,
                    color = getContentColor(TaskPriority.HIGH)
                )
            }
        }
    }

}
