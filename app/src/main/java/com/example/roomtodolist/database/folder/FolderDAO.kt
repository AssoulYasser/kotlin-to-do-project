package com.example.roomtodolist.database.folder

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomtodolist.database.task.TaskTable

@Dao
interface FolderDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFolder(folderTable: FolderTable)

    @Query("SELECT * FROM FolderTable ORDER BY name")
    suspend fun getFolders(): List<FolderTable>

    @Update
    suspend fun updateFolder(folderTable: FolderTable)

    @Delete
    suspend fun deleteFolder(folderTable: FolderTable)

}