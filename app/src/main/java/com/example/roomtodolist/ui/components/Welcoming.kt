package com.example.roomtodolist.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
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
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Welcoming(
    modifier: Modifier = Modifier,
    username: String?,
    spacer: Dp = 0.dp
) {
    Column(modifier, verticalArrangement = Arrangement.Center) {
        Text(
            text = if (LocalTime.now().isBefore(LocalTime.of(6, 0))) "Good morning" else "Good evening",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(spacer))
        Text(
            text = username ?: "UNNAMED",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}