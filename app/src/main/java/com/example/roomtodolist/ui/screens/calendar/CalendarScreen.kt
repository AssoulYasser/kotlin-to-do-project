package com.example.roomtodolist.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CalendarScreen() {
    Box(modifier = Modifier.background(Color.Blue).fillMaxSize())
    Text(text = "CAL")
}