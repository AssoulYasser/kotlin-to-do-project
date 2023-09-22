package com.example.roomtodolist.domain.main_activity

import androidx.compose.runtime.mutableStateMapOf
import com.example.roomtodolist.data.folder.FolderDAO
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.folder.folderAssets
import com.example.roomtodolist.data.folder.folderColors

class FoldersManager(private val folderDAO: FolderDAO) {

    private var hasInitialized = false
    val folders = mutableStateMapOf<Long, FolderTable>()
    val colors = folderColors
    val assets = folderAssets

    suspend fun initFolders(onFinish: (Map<Long, FolderTable>) -> Unit) : Map<Long, FolderTable> {
        if (hasInitialized)
            return mapOf()

        hasInitialized = true

        for (folder in folderDAO.getFolders()) {
            folders[folder.id!!] = folder
        }
        onFinish(folders.toMap())
        return folders.toMap()
    }

    suspend fun addFolder(folder: FolderTable) : FolderTable {
        val id = folderDAO.addFolder(folder)
        val newFolder = folder.copy(id = id)
        updateFolderState(newFolder, Operation.ADD)
        return newFolder
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