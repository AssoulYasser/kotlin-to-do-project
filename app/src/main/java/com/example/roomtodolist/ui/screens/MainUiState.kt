package com.example.roomtodolist.ui.screens

import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable

data class MainUiState(
    val folders : MutableList<FolderTable> = mutableListOf(),
    val tasks : MutableList<TaskTable> = mutableListOf(),
    val tasksPerFolder: HashMap<FolderTable, MutableList<TaskTable>> = hashMapOf(),
    val taskToUpdate: TaskTable? = null
)
