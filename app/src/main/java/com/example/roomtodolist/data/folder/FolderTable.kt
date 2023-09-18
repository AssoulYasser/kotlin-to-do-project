package com.example.roomtodolist.data.folder

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class FolderTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val color: Int,
    val asset: Bitmap
) {
    @Ignore
    var taskCounts = 0
}