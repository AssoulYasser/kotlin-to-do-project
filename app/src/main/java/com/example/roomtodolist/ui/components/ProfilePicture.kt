package com.example.roomtodolist.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Filled.AccountCircle,
        contentDescription = null,
        modifier = modifier
    )
}