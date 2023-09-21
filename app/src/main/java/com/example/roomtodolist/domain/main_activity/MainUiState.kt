package com.example.roomtodolist.domain.main_activity

import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable

data class MainUiState(
    val taskToUpdate: TaskTable? = null,
    val folderToUpdate: FolderTable? = null,
    val profilePicture: Uri? = null,
    val username: String? = null,
)
