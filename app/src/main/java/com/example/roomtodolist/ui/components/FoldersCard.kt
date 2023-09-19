package com.example.roomtodolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.R
import com.example.roomtodolist.data.folder.FolderTable

@Composable
fun FoldersCard(
    folders: List<FolderTable>,
    onAddFolder: () -> Unit,
    onSelectFolder: (FolderTable) -> Unit,
    selectedFolder: () -> FolderTable?
) {

    @Composable
    fun RowScope.Folder(index: Int) {
        Box(modifier = Modifier.weight(1f)) {
            FolderCard(
                folder = folders[index],
                onFolderClick = onSelectFolder,
                color =
                if (selectedFolder() == folders[index])
                    MaterialTheme.colorScheme.primaryContainer
                else
                    Color.Transparent,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer)
            )
        }
    }

    ExpandableCard(icon = R.drawable.outlined_folder_add_icon, title = "Set Folder") {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AddFolderCard(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                            RoundedCornerShape(24f)
                        )
                ) {
                    onAddFolder()
                }
                if (folders.isNotEmpty())
                    Folder(index = 0)
            }
            for (folderIndex in 1 until folders.size step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (folderIndex + 1 < folders.size) {
                        Folder(index = folderIndex)
                        Folder(index = folderIndex + 1)
                    }
                    else {
                        Folder(index = folderIndex)
                    }
                }
            }
        }
    }
}
