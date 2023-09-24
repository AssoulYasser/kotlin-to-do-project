package com.example.roomtodolist.domain.main_activity

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.Color
import com.example.roomtodolist.data.folder.FolderDAO
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.folder.folderAssets
import com.example.roomtodolist.data.folder.folderColors

class FolderManager(private val folderDAO: FolderDAO) {

    private var hasInitialized = false
    val folders = mutableStateMapOf<Long, FolderTable>()

    suspend fun initFolders(onFinish: (List<FolderTable>) -> Unit) {
        if (hasInitialized)
            return

        for (folder in folderDAO.getFolders()) {
            folders[folder.id!!] = folder
        }
        onFinish(folders.values.toList())
        hasInitialized = true
    }

    suspend fun addFolder(folder: FolderTable) : FolderTable {
        val generatedId = folderDAO.addFolder(folder)
        val newFolder = folder.copy(id = generatedId)
        folders[generatedId] = newFolder
        return newFolder
    }

    suspend fun updateFolder(folder: FolderTable) {
        folderDAO.updateFolder(folder)
        folders[folder.id!!] = folder
    }

    suspend fun deleteFolder(folder: FolderTable) {
        folderDAO.deleteFolder(folderId = folder.id!!)
        folders.remove(folder.id)
    }

    fun getAssets() : HashMap<Int, Int> = folderAssets

    fun getColors() : List<Color> = folderColors

}