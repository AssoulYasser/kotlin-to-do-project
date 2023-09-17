package com.example.roomtodolist.data.folder

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class FolderTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val color: Int,
    val asset: Int
) {
    @Ignore
    var taskCounts = 0
}