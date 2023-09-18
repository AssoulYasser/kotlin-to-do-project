package com.example.roomtodolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.data.folder.FolderTable

@Composable
fun FolderCard(
    folder: FolderTable,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    border: BorderStroke? = null,
    onFolderClick: (FolderTable) -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = screenWidth.div(2F)
    val cardHeight = cardWidth.times(0.9F)
    val assetHeight = cardHeight.div(2.75F)


    Surface(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .clickable { onFolderClick(folder) },
        shape = RoundedCornerShape(24f),
        color = color,
        border = border
    ) {
        Column(
            modifier = Modifier
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.height(assetHeight)) {
                Image(
                    bitmap = folder.asset.asImageBitmap(),
                    modifier = Modifier,
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    alignment = Alignment.Center
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = folder.name,
                        color = Color(folder.color),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${folder.taskCounts} Tasks",
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}