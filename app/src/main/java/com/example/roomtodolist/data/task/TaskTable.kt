package com.example.roomtodolist.data.task

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class TaskTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val date: LocalDate,
    val time: LocalTime,
    val priority: TaskPriority,
    val folder: String
)
