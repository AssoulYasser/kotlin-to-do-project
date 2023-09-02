package com.example.roomtodolist.database

import android.content.Context

class Repository(private val context:Context) {
    private val database = MainDataBase(context = context)
    val taskDao = database.getTaskDao()
    val folderDao = database.getFolderDao()
}
