package com.example.roomtodolist.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.roomtodolist.R
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.navigation.NavDestination
import com.example.roomtodolist.ui.navigation.navigateTo
import com.example.roomtodolist.ui.theme.StateColors

@Composable
fun AddTaskScreen(
    navHostController: NavHostController,
    taskTitleState: MutableState<String> = remember { mutableStateOf("") }
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ActionBar(title = "Add Task") {
            navHostController.navigateTo(NavDestination.Home.rout)
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TaskTitle(taskTitleState)
            AddTaskContainer(icon = R.drawable.outlined_arrow_left_icon, title = "Dummy Title") {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Dummy Button")
                }
            }
            AddTaskContainer(icon = R.drawable.outlined_arrow_left_icon, title = "Dummy Title") {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Dummy Button")
                }
            }
            AddTaskContainer(icon = R.drawable.outlined_arrow_left_icon, title = "Dummy Title") {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Dummy Button")
                }
            }
            AddTaskContainer(icon = R.drawable.outlined_arrow_left_icon, title = "Dummy Title") {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Dummy Button")
                }
            }
            AddTaskContainer(icon = R.drawable.outlined_arrow_left_icon, title = "Dummy Title") {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Dummy Button")
                }
            }
        }
    }

}

@Composable
private fun AddTaskContainer(
    icon: Int,
    title: String,
    content: @Composable () -> Unit
) {
    val isOpen = remember { mutableStateOf(false) }
    Surface(
        shape = RoundedCornerShape(20f),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(300, easing = FastOutSlowInEasing)),
        color = MaterialTheme.colorScheme.primaryContainer,
        onClick = {
            isOpen.value = !isOpen.value
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
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
            if (isOpen.value) content()
        }
    }
}

@Composable
private fun ColumnScope.TaskTitle(
    taskTitleState: MutableState<String> = remember { mutableStateOf("") }
) {
    TextField(
        value = taskTitleState.value,
        onValueChange = { taskTitleState.value = it },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            errorTextColor = StateColors.Negative,
            errorContainerColor = StateColors.Negative,
            cursorColor = Color.Black,
            errorCursorColor = StateColors.Negative,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(20f),
        modifier = Modifier
            .fillMaxWidth()
            .weight(2f)
    )
}