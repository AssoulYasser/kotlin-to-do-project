package com.example.roomtodolist.ui.screens

import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable

data class MainUiState(
    val folders : HashMap<Long, FolderTable> = hashMapOf(),
    val tasks : HashMap<Long, TaskTable> = hashMapOf(),
    val tasksPerFolder: HashMap<FolderTable, MutableList<TaskTable>> = hashMapOf(),
    val taskToUpdate: TaskTable? = null,
    val folderToUpdate: FolderTable? = null
)
