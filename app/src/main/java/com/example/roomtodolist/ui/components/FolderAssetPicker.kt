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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun FolderAssetPicker(
    assets: List<Int>,
    selectedAsset: Int,
    setFolderAsset: (Int) -> Unit
) {

    @Composable
    fun RowScope.Asset(assetIndex: Int) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .padding(horizontal = 5.dp),
            color = if (selectedAsset == assets[assetIndex])
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        Color.Transparent,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(24f),
            onClick = {
                setFolderAsset(assets[assetIndex])
            }
        ) {
            Image(
                painter = painterResource(id = assets[assetIndex]),
                contentDescription = null,
                modifier = Modifier
                    .padding(25.dp)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        for (assetIndex in assets.indices step 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Asset(assetIndex = assetIndex)
                if (assetIndex + 1 < assets.size)
                    Asset(assetIndex = assetIndex + 1)
            }
        }
    }
}