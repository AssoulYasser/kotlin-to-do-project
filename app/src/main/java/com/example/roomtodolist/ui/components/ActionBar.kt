package com.example.roomtodolist.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomtodolist.R

@Composable
fun ActionBar(
    height: Dp = 60.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    shadowElevation: Dp = 2.dp,
    title: String = "Dummy Title",
    leftButtonIcon: Int = R.drawable.outlined_arrow_left_icon,
    leftButtonAction: () -> Unit
) {
    Surface(
        shadowElevation = shadowElevation,
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .align(Alignment.Center)
            )
            IconButton(
                onClick = leftButtonAction,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(id = leftButtonIcon),
                    contentDescription = null
                )
            }
        }
    }

}