package com.example.roomtodolist.data.task

import androidx.compose.ui.graphics.Color

enum class TaskPriority {
    UNSPECIFIED,
    LOW,
    MEDIUM,
    HIGH;

    fun getPriorityColor() =
        when(this) {
            LOW -> Color.Green
            MEDIUM -> Color.Blue
            HIGH -> Color.Red
            else -> Color.Transparent
        }
}