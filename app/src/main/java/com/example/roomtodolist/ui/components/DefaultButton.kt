package com.example.roomtodolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun defaultButtonShape() = RoundedCornerShape(24)
@Composable
fun defaultButtonStroke(primary: Color = MaterialTheme.colorScheme.primary) =
    BorderStroke(2.dp, primary)
@Composable
fun defaultOutlinedButtonColors() = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
@Composable
fun defaultFilledButtonColors(primary: Color = MaterialTheme.colorScheme.primary) =
    ButtonDefaults.buttonColors(containerColor = primary, contentColor = MaterialTheme.colorScheme.background)