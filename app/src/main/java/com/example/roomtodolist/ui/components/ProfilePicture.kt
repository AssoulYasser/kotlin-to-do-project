package com.example.roomtodolist.ui.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    picture: Uri?,
    onPictureClick: () -> Unit
) {
    if (picture == null) {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = null,
            modifier = modifier.clip(CircleShape).clickable { onPictureClick() },
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
    else {
        Button(
            modifier = modifier.aspectRatio(1f),
            onClick = onPictureClick,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground),
            contentPadding = PaddingValues(2.dp)
        ) {
            AsyncImage(
                model = picture,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .aspectRatio(1f)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    picture: Uri?,
) {
    if (picture == null) {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = modifier
        )
    }
    else {
        AsyncImage(
            model = picture,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .aspectRatio(1f)
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }
}