package com.example.roomtodolist.ui.screens.taskshowcase

import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskPriority
import com.example.roomtodolist.data.task.TaskTable
import java.time.LocalDate
import java.time.LocalTime

data class TaskShowCaseUiState(
    var id: Long? = null,
    var taskTitle: String = "",
    var taskPriority: TaskPriority = TaskPriority.UNSPECIFIED,
    var date: LocalDate? = null,
    var time: LocalTime? = null,
    var folder: FolderTable? = null,
    var oldFolderName: String = ""
)
