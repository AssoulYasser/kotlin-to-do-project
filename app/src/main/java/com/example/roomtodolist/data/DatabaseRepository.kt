package com.example.roomtodolist.data

import android.content.Context

class DatabaseRepository(private val context:Context) {
    private val database = MainDataBase(context = context)
    val taskDao = database.getTaskDao()
    val folderDao = database.getFolderDao()
}
