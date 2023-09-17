package com.example.roomtodolist.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
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
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.roomtodolist.R
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.folder.folderAssets

@Composable
fun FolderCard(
    folder: FolderTable,
    folderAsset: Int = R.drawable.cooking_asset_primary,
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

    val topAsset = folderAssets[folderAsset]

    val assetBitmap =
        if (topAsset != null)
            getBitmapFromImage(
                context = context,
                primaryAsset = folderAsset,
                secondaryAsset = topAsset!!,
                color = folder.color
            )
        else
            getBitmapFromImage(
                context = context,
                primaryAsset = folderAssets.keys.first(),
                secondaryAsset = folderAssets.values.first(),
                color = folder.color
            )


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
                    bitmap = assetBitmap.asImageBitmap(),
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

fun getBitmapFromImage(context: Context, primaryAsset: Int, secondaryAsset: Int, color: Int): Bitmap {
    // Load the XML drawables into Drawable objects
    val primaryDrawable: Drawable? = ContextCompat.getDrawable(context, primaryAsset)
    val secondaryDrawable: Drawable? = ContextCompat.getDrawable(context, secondaryAsset)

    val wrappedDrawable = secondaryDrawable?.let { DrawableCompat.wrap(it) }
    wrappedDrawable?.setTint(color)

    // Define the width and height of the resulting bitmap
    val width = primaryDrawable?.intrinsicWidth ?: (0 + secondaryDrawable?.intrinsicWidth!!)
    val height = primaryDrawable?.intrinsicHeight ?: (0 + secondaryDrawable?.intrinsicHeight!!)

    // Create a Bitmap with the specified width and height
    val combinedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    // Create a Canvas to draw on the Bitmap
    val canvas = Canvas(combinedBitmap)

    // Draw the first drawable onto the canvas
    primaryDrawable?.setBounds(0, 0, primaryDrawable.intrinsicWidth, primaryDrawable.intrinsicHeight)
    primaryDrawable?.draw(canvas)

    // Draw the second drawable onto the canvas, below the first one
    secondaryDrawable?.setBounds(0, 0, secondaryDrawable.intrinsicWidth, height)
    secondaryDrawable?.draw(canvas)

    return combinedBitmap
}
