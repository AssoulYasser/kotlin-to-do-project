package com.example.roomtodolist.ui.screens.folders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.AddFolderCard
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.FolderCard

@Composable
fun FoldersScreen(
    foldersViewModel: FoldersViewModel
) {
    val folders = foldersViewModel.getFolders()
    @Composable
    fun RowScope.Folder(index: Int) {
        Box(modifier = Modifier.weight(1f)) {
            FolderCard(
                folder = folders[index],
                onFolderClick = {
                    foldersViewModel.setFolderToUpdate(folders[index])
                    foldersViewModel.navigateToFolderShowCase()
                },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer)
            )
        }
    }
    Container(actionBar = {
        ActionBar(title = "Folders") {
            foldersViewModel.navigateBack()
        }
    }) {
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
                    foldersViewModel.navigateToAddFolderScreen()
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