package com.example.roomtodolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomtodolist.data.folder.FolderDAO
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskDAO
import com.example.roomtodolist.data.task.TaskTable

@Database(entities = [TaskTable::class, FolderTable::class], version = 1)
@TypeConverters(Converters::class)
abstract class MainDataBase: RoomDatabase() {

    abstract fun getTaskDao() : TaskDAO
    abstract fun getFolderDao() : FolderDAO

    companion object {
        @Volatile
        private var instance : MainDataBase? = null

        operator fun invoke(context: Context): MainDataBase {
            return if (instance != null)
                instance!!
            else {
                Room.databaseBuilder(
                    context = context,
                    klass = MainDataBase::class.java,
                    name = "TaskTable"
                ).build()
            }
        }
    }

}