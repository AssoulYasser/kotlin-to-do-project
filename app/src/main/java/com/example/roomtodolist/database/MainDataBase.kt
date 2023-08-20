package com.example.roomtodolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomtodolist.database.folder.FolderTable
import com.example.roomtodolist.database.task.TaskDAO
import com.example.roomtodolist.database.task.converters.TaskDateConverter
import com.example.roomtodolist.database.task.converters.TaskPriorityConverter
import com.example.roomtodolist.database.task.TaskTable

@Database(entities = [TaskTable::class, FolderTable::class], version = 1)
@TypeConverters(value = [TaskDateConverter::class, TaskPriorityConverter::class])
abstract class MainDataBase: RoomDatabase() {
    abstract fun getDao() : TaskDAO

    companion object {
        @Volatile
        private var instance : MainDataBase? = null

        operator fun invoke(context: Context): MainDataBase {
            return if (instance != null)
                instance!!
            else {
                val room = Room
                val builder = room.databaseBuilder(
                    context = context,
                    klass = MainDataBase::class.java,
                    name = "TaskTable"
                )
                val build = builder.build()
                instance = build
                instance!!
            }
        }
    }

}