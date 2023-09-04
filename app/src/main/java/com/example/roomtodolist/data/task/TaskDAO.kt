package com.example.roomtodolist.data.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(taskTable: TaskTable)

    @Query("SELECT * FROM TaskTable ORDER BY folder")
    suspend fun getTasks(): List<TaskTable>

//    @Query("SELECT * FROM TaskTable WHERE TaskTable.folder = :folder")
//    suspend fun getTasksFromFolder(folder: String)

    @Update
    suspend fun updateTask(taskTable: TaskTable)

    @Delete
    suspend fun deleteTask(taskTable: TaskTable)

}