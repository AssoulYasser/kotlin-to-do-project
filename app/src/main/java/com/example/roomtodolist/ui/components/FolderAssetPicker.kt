package com.example.roomtodolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.views.Grid


@Composable
fun FolderAssetPicker(
    assets: List<Int>,
    selectedAsset: () -> Int,
    setFolderAsset: (Int) -> Unit
) {

    @Composable
    fun Asset(asset: Int) {
        val isSelected by remember {
            derivedStateOf{ selectedAsset() == asset }
        }

        Surface(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(horizontal = 5.dp),
            color = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                Color.Transparent,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(24f),
            onClick = {
                setFolderAsset(asset)
            }
        ) {
            Image(
                painter = painterResource(id = asset),
                contentDescription = null,
                modifier = Modifier
                    .padding(25.dp)
            )
        }
    }

    Grid(
        modifier = Modifier.fillMaxWidth(),
        items = assets,
        columns = 2,
        rowSpacing = 12.dp,
        columnSpacing = 12.dp
    ) {
        Asset(asset = it)
    }
}
