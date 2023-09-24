package com.example.roomtodolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

@Composable
fun FolderColorPicker(
    colors: List<Color>,
    setFolderColor: (Color) -> Unit,
    selectedColor: () -> Int
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(items = colors, key = { it.toArgb() }) {
            Surface(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable {
                        setFolderColor(it)
                    },
                shape = CircleShape,
                color =
                if (selectedColor() == it.toArgb())
                    it
                else
                    Color.Transparent,
                border = BorderStroke(2.dp, it),
                content = {}
            )
        }
    }
}
