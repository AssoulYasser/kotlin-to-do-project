package com.example.roomtodolist.data.task

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity
data class TaskTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val dueTo: LocalDate,
    val estimation: Int,
    val priority: TaskPriority,
    val folder: String
)
