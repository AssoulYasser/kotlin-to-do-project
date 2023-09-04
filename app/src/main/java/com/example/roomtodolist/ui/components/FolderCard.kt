package com.example.roomtodolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ChipBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.R
import com.example.roomtodolist.data.folder.FolderTable

@Composable
fun FolderCard(
    modifier: Modifier = Modifier,
    folder: FolderTable,
    showArrowIcon: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    border: BorderStroke? = null
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24f),
        color = color,
        border = border
    ) {
        Row(
            modifier = Modifier.padding(25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = folder.name, color = Color(folder.color))
                Text(text = "${folder.taskCounts}", color = MaterialTheme.colorScheme.onBackground)
            }
            if (showArrowIcon)
                Icon(
                    painter = painterResource(id = R.drawable.outlined_arrow_right_icon),
                    contentDescription = null,
                    tint = Color(folder.color)
                )
        }
    }
}