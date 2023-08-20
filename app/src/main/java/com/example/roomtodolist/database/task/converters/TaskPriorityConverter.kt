package com.example.roomtodolist.database.task.converters

import androidx.room.TypeConverter
import com.example.roomtodolist.database.task.TaskPriority

class TaskPriorityConverter {
    @TypeConverter
    fun getPriority(int: Int) : TaskPriority {
        return when(int) {
            1 -> TaskPriority.HIGH
            2 -> TaskPriority.MEDIUM
            3 -> TaskPriority.LOW
            else -> TaskPriority.UNSPECIFIED
        }
    }

    @TypeConverter
    fun setPriority(taskPriority: TaskPriority) : Int {
        return when(taskPriority){
            TaskPriority.UNSPECIFIED -> 0
            TaskPriority.LOW -> 1
            TaskPriority.MEDIUM -> 2
            TaskPriority.HIGH -> 3
        }
    }
}