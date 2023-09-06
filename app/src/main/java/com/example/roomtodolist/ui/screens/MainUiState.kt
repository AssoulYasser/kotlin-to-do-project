package com.example.roomtodolist.ui.screens

import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable

data class MainUiState(
    val folders : List<FolderTable> = mutableListOf(),
    val tasks : List<TaskTable> = mutableListOf(),
    val tasksPerFolder: HashMap<FolderTable, List<TaskTable>> = hashMapOf()
)
