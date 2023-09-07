package com.example.roomtodolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FolderColorPicker(
    colors: MutableList<Color>,
    setFolderColor: (Color) -> Unit,
    selectedColor: Color?
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        colors.forEach { color ->
            Surface(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable {
                        setFolderColor(color)
                    },
                shape = CircleShape,
                color =
                if (selectedColor == color)
                    color
                else
                    Color.Transparent,
                border = BorderStroke(2.dp, color),
                content = {}
            )
        }
    }
}
