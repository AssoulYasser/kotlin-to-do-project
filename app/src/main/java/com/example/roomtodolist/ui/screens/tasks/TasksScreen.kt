package com.example.roomtodolist.ui.screens.tasks

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

const val TAG = "DEBUGGING : "
@Composable
fun TasksScreen() {
    Log.d(TAG, "TasksScreen: ")
    Box(modifier = Modifier
        .background(Color.Gray)
        .fillMaxSize())
    Text(text = "TASK")
}