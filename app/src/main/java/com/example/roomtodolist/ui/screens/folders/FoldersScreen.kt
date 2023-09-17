package com.example.roomtodolist.ui.screens.folders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.AddElementCard
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.FolderCard
import com.example.roomtodolist.ui.components.FoldersCard
import com.maxkeppeker.sheets.core.views.Grid

@Composable
fun FoldersScreen(
    foldersViewModel: FoldersViewModel
) {
    val folders = foldersViewModel.getFolders()
    @Composable
    fun RowScope.Folder(index: Int) {
        FolderCard(
            folder = folders[index],
            onFolderClick = {
                foldersViewModel.setFolderToUpdate(folders[index])
                foldersViewModel.navigateToFolderShowCase()
            },
            showArrowIcon = false,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer)
        )
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
                    AddElementCard(
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
                AddElementCard(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(vertical = 25.dp)
                ) {
                    foldersViewModel.navigateToAddFolderScreen()
                }
            }
    }
}