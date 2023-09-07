package com.example.roomtodolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    selectedFolder: FolderTable?
) {

    @Composable
    fun RowScope.Folder(index: Int) {
        FolderCard(
            folder = folders[index],
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onSelectFolder(folders[index])
                },
            showArrowIcon = false,
            color =
            if (selectedFolder == folders[index])
                MaterialTheme.colorScheme.primaryContainer
            else
                Color.Transparent,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer)
        )
    }

    ExpandableCard(icon = R.drawable.outlined_folder_add_icon, title = "Set Folder") {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            for (folderIndex in folders.indices step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Folder(index = folderIndex)
                    if (folderIndex + 1 < folders.size)
                        Folder(index = folderIndex + 1)
                    else
                        AddElementCard(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 25.dp)
                        ) {
                            onAddFolder()
                        }
                }
            }
            if (folders.size % 2 == 0)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.Start),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AddElementCard(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(vertical = 25.dp)
                    ) {
                        onAddFolder()
                    }
                }
        }
    }
}
