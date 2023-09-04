package com.example.roomtodolist.data.folder

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class FolderTable(
    @PrimaryKey
    val name: String,
    val color: Int
) {
    @Ignore
    var taskCounts = 0
}