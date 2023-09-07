package com.example.roomtodolist.ui.screens.taskshowcase

import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskPriority
import com.example.roomtodolist.data.task.TaskTable
import java.time.LocalDate
import java.time.LocalTime

data class TaskShowCaseUiState(
    var taskTitle: String = "",
    var taskPriority: TaskPriority = TaskPriority.UNSPECIFIED,
    var taskDate: LocalDate? = null,
    var taskTime: LocalTime? = null,
    var taskFolder: FolderTable? = null
)
