package com.example.roomtodolist.ui.screens.folders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
                folderAsset = folders[index].asset,
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
                    AddFolderCard(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 25.dp)
                    ) {
                        foldersViewModel.navigateToAddFolderScreen()
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
                AddFolderCard(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(vertical = 25.dp)
                ) {
                    foldersViewModel.navigateToAddFolderScreen()
                }
            }
    }
}