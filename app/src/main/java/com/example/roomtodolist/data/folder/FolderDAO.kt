package com.example.roomtodolist.data.folder

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FolderDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFolder(folderTable: FolderTable) : Long

    @Query("SELECT * FROM FolderTable ORDER BY name")
    suspend fun getFolders(): MutableList<FolderTable>

    @Query("SELECT * FROM FolderTable WHERE id == :folderId")
    suspend fun getFolderById(folderId: Long) : FolderTable

    @Query("SELECT * FROM FolderTable WHERE name == :folderName")
    suspend fun getFolderByName(folderName : String) : FolderTable

    @Update
    suspend fun updateFolder(folderTable: FolderTable)

    @Query("DELETE FROM FolderTable WHERE id == :folderId")
    suspend fun deleteFolder(folderId: Long)

}