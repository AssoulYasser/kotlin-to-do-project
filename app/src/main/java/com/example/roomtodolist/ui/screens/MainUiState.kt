package com.example.roomtodolist.ui.screens

import com.example.roomtodolist.data.folder.FolderTable

data class MainUiState(
    val folders : List<FolderTable> = mutableListOf()
)
