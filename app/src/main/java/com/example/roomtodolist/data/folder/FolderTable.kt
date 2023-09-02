package com.example.roomtodolist.data.folder

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FolderTable(
    @PrimaryKey
    val name: String,
    val color: Int
)