package com.example.roomtodolist.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.roomtodolist.ui.theme.StateColors

fun defaultTextFieldShape() = RoundedCornerShape(20f)

@Composable
fun defaultTextFieldColors() = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
        focusedLabelColor = MaterialTheme.colorScheme.onBackground,
        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
        focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
        focusedTextColor = MaterialTheme.colorScheme.onBackground,
        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
        errorTextColor = StateColors.Negative,
        errorContainerColor = StateColors.Negative,
        cursorColor = Color.Black,
        errorCursorColor = StateColors.Negative,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )
