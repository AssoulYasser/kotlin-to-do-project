package com.example.roomtodolist.ui.screens.task_show_case

import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskPriority
import java.time.LocalDate
import java.time.LocalTime

data class TaskShowCaseUiState(
    var taskTitle: String = "",
    var taskPriority: TaskPriority = TaskPriority.UNSPECIFIED,
    var taskDate: LocalDate? = null,
    var taskTime: LocalTime? = null,
    var taskFolder: FolderTable? = null
)
