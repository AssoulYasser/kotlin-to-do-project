package com.example.roomtodolist.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    icon: Int,
    title: String,
    content: @Composable () -> Unit
) {
    val isOpen = remember { mutableStateOf(false) }
    Surface(
        shape = RoundedCornerShape(20f),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(300, easing = LinearEasing)),
        color = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20f),
                colors = CardDefaults
                    .cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                onClick = {
                    isOpen.value = !isOpen.value
                }
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart) ,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Text(text = title, color = MaterialTheme.colorScheme.onBackground)
                    }

                    Icon(
                        painter = painterResource(id =
                        if (isOpen.value)
                            R.drawable.outlined_non_lined_arrow_up_icon
                        else
                            R.drawable.outlined_non_lined_arrow_down_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )

                }
            }

            if (isOpen.value) content()
        }
    }
}