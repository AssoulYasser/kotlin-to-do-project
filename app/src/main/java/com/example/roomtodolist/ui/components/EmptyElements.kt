package com.example.roomtodolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.roomtodolist.R

@Composable
fun EmptyElements(
    modifier: Modifier = Modifier,
    elementName: String,
    showGif: Boolean = true,
    isDark: Boolean,
    onCreateElement: () -> Unit = {}
) {
    val animationGif by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.not_found_animation)
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showGif)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                color = Color.Transparent
            ) {
                LottieAnimation(composition = animationGif,
                    modifier = Modifier,
                    isPlaying = true,
                    restartOnPlay = true,
                    reverseOnRepeat = true,
                    contentScale = ContentScale.FillWidth,
                )
            }

        Text(
            text = "there are no $elementName to show",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (onCreateElement != {}) {
            Button(
                onClick = onCreateElement,
                modifier = Modifier.fillMaxWidth(0.4f),
                shape = RoundedCornerShape(24),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(
                    2.dp,
                    if (isDark) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Create $elementName",
                    textAlign = TextAlign.Center,
                    color = if (isDark) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary
                )
            }
        }
    }

}