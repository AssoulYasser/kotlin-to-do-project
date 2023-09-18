package com.example.roomtodolist.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.example.roomtodolist.data.task.TaskPriority
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalTime

class Converters {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun getDate(string: String) : LocalDate = LocalDate.parse(string)
    @TypeConverter
    fun setDate(date: LocalDate) : String = date.toString()


    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun getTime(string: String) : LocalTime = LocalTime.parse(string)
    @TypeConverter
    fun setTime(time: LocalTime) : String = time.toString()


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
            TaskPriority.HIGH -> 1
            TaskPriority.MEDIUM -> 2
            TaskPriority.LOW -> 3
        }
    }

    @TypeConverter
    fun getByteArrayFromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun getBitmapFromByteArray(byteArray: ByteArray): Bitmap{
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

}