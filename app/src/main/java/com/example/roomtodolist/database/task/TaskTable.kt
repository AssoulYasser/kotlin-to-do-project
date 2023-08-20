package com.example.roomtodolist.database.task

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class TaskTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val dueTo: Date,
    val estimation: Int,
    val priority: TaskPriority,
    val folder: String
)
