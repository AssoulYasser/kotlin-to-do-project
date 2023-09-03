package com.example.roomtodolist.data

import androidx.room.TypeConverter
import com.example.roomtodolist.data.task.TaskPriority
import java.util.Date

class Converters {
    @TypeConverter
    fun getDate(long:Long) : Date = Date(long)
    @TypeConverter
    fun setDate(date: Date) : Long = date.time


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