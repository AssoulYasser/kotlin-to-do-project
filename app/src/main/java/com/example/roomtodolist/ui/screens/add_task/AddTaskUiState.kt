package com.example.roomtodolist.ui.screens.add_task

import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskPriority
import java.time.LocalDate
import java.time.LocalTime

data class AddTaskUiState(
    val taskTitle: String = "",
    val taskPriority: TaskPriority = TaskPriority.UNSPECIFIED,
    val taskDate: LocalDate? = null,
    val taskTime: LocalTime? = null,
    val taskFolder: FolderTable? = null
)
