package com.example.roomtodolist.database.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomtodolist.database.folder.FolderTable

@Dao
interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(taskTable: TaskTable)

    @Query("SELECT * FROM TaskTable ORDER BY id")
    suspend fun getTasks(): List<TaskTable>

    @Query("SELECT * FROM TaskTable WHERE folder = :folder")
    suspend fun getTasksFromFolder(folder: String)

    @Update
    suspend fun updateTask(taskTable: TaskTable)

    @Delete
    suspend fun deleteTask(taskTable: TaskTable)

}