package com.example.roomtodolist.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Welcoming(
    modifier: Modifier = Modifier,
    username: String?,
    spacer: Dp = 0.dp
) {
    Column(modifier, verticalArrangement = Arrangement.Center) {
        Text(text = "Good morning", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(spacer))
        Text(text = username ?: "UNNAMED", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
    }
}