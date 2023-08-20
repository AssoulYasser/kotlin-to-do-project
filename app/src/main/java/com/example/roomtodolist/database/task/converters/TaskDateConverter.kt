package com.example.roomtodolist.database.task.converters

import androidx.room.TypeConverter
import java.util.Date

class TaskDateConverter {
    @TypeConverter
    fun getDate(long:Long) : Date = Date(long)

    @TypeConverter
    fun setDate(date: Date) : Long = date.time

}