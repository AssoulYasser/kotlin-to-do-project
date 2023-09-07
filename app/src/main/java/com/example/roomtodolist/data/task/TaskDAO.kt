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
    suspend fun addTask(taskTable: TaskTable) : Long

    @Query("SELECT * FROM TaskTable ORDER BY folder")
    suspend fun getTasks(): MutableList<TaskTable>

    @Query("SELECT * FROM TaskTable WHERE TaskTable.folder == :folder ORDER BY priority")
    suspend fun getTasksFromFolder(folder: Long) : MutableList<TaskTable>

    @Update
    suspend fun updateTask(taskTable: TaskTable)

    @Query("DELETE FROM TaskTable WHERE id == :taskId")
    suspend fun deleteTask(taskId: Long)

}