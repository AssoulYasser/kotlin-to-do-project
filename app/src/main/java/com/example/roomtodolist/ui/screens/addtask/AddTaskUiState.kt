package com.example.roomtodolist.ui.screens.addtask

import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskPriority
import java.time.LocalDate
import java.time.LocalTime

data class AddTaskUiState(
    val taskTitle: String = "",
    val taskPriority: TaskPriority = TaskPriority.UNSPECIFIED,
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val folder: FolderTable? = null
)
