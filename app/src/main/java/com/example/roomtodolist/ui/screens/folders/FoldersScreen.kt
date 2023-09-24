package com.example.roomtodolist.ui.screens.folders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.AddFolderCard
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.FolderCard
import com.maxkeppeker.sheets.core.views.Grid

@Composable
fun FoldersScreen(
    foldersViewModel: FoldersViewModel
) {
    val folders = foldersViewModel.getFolders()
    var isRight = false
    Container(
        actionBar = {
            ActionBar(title = "Folders") {
                foldersViewModel.navigateBack()
            }
        },
        isScrollable = false
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(2)
        ) {
            item {
                AddFolderCard(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(end = 10.dp, bottom = 10.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                            RoundedCornerShape(24f)
                        )
                ) {
                    foldersViewModel.navigateToAddFolderScreen()
                }
            }

            items(items = folders, key = { it.id!! }) { folder ->
                Box(
                    modifier =
                    if (isRight)
                        Modifier.aspectRatio(1f).padding(end = 10.dp, bottom = 10.dp)
                    else
                        Modifier.aspectRatio(1f).padding(start = 10.dp, bottom = 10.dp)
                ) {
                    FolderCard(
                        folder = folder,
                        onFolderClick = {
                            foldersViewModel.setFolderToUpdate(folder)
                            foldersViewModel.navigateToFolderShowCase()
                        }
                    )
                    isRight = !isRight
                }
            }
        }
    }
}