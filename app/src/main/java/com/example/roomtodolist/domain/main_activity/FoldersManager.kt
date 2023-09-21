package com.example.roomtodolist.domain.main_activity

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import com.example.roomtodolist.data.folder.FolderDAO
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.folder.folderAssets
import com.example.roomtodolist.data.folder.folderColors

class FoldersManager(private val folderDAO: FolderDAO) {

    var hasInitialized = false
    val folders = mutableStateMapOf<Long, FolderTable>()
    val colors = folderColors
    val assets = folderAssets

    suspend fun start()  {
        if (!hasInitialized) {
            for (folder in folderDAO.getFolders()) {
                folders[folder.id!!] = folder
            }
            hasInitialized = true
        }
    }

    suspend fun addFolder(folder: FolderTable) {
        val id = folderDAO.addFolder(folder)
        val newFolder = folder.copy(id = id)
        updateFolderState(newFolder, Operation.ADD)
    }

    suspend fun updateFolder(folder: FolderTable) {
        folderDAO.updateFolder(folder)
        updateFolderState(folder, Operation.CHANGE)
    }

    suspend fun deleteFolder(folder: FolderTable) {
        folderDAO.deleteFolder(folder.id!!)
        updateFolderState(folder, Operation.DELETE)
    }

    private fun updateFolderState(folder: FolderTable, operation: Operation) {
        if (folder.id == null)
            throw Exception("FOLDER ID SHOULD NOT BE NULL")
        when (operation) {
            Operation.ADD, Operation.CHANGE -> {
                folders[folder.id] = folder
            }
            Operation.DELETE -> {
                folders.remove(folder.id)
            }
        }
    }

}