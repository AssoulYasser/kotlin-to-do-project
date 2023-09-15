package com.example.roomtodolist.domain.main_activity

import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable

data class MainUiState(
    val folders : HashMap<Long, FolderTable> = hashMapOf(),
    val tasks : HashMap<Long, TaskTable> = hashMapOf(),
    val tasksPerFolder: HashMap<FolderTable, MutableList<TaskTable>> = hashMapOf(),
    val taskToUpdate: TaskTable? = null,
    val folderToUpdate: FolderTable? = null,
    val profilePicture: Uri? = null,
    val username: String? = null,
    val isDarkTheme: Boolean = false,
    val statusBarColor: Color = Color.Transparent
)
