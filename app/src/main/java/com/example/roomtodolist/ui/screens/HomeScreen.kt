package com.example.roomtodolist.ui.screens

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomtodolist.R
import com.example.roomtodolist.database.folder.FolderTable
import com.example.roomtodolist.database.task.TaskTable
import com.example.roomtodolist.ui.components.EmptyElements
import com.example.roomtodolist.ui.components.ProfilePicture
import com.example.roomtodolist.ui.components.Welcoming
import com.example.roomtodolist.ui.theme.StateColors

@Composable
fun HomeScreen(
    searchFor: MutableState<String> = remember { mutableStateOf("") },
    folders: List<FolderTable> = listOf(),
    tasks: List<TaskTable> = listOf()
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    )
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier)
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        WeekCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(20f)
                )
        )
        SearchForTask(value = searchFor)
        Folders(folders)
        Tasks(tasks)
        Spacer(modifier = Modifier)
    }

}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfilePicture(modifier = Modifier
                .size(75.dp)
            )
            Welcoming(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
            )
        }
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(25.dp),
                painter = painterResource(id = R.drawable.outlined_notification_icon),
                contentDescription = null
            )
            Icon(
                modifier = Modifier
                    .size(25.dp),
                painter = painterResource(id = R.drawable.outlined_setting_icon),
                contentDescription = null
            )
        }
    }
}


@Composable
private fun WeekCalendar(modifier: Modifier = Modifier) {
    val days = hashMapOf(
        "mon" to 18,
        "tue" to 19,
        "wed" to 20,
        "thu" to 20,
        "fri" to 21,
        "sat" to 22,
        "sun" to 22,
    )
    val selectedDay = remember { mutableStateOf("wed") }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            days.forEach {
                DayElement(dayName = it.key, dayNumber = it.value, selectedDay = selectedDay)
            }
        }
    }
}

@Composable
private fun RowScope.DayElement(
    selectedDay: MutableState<String>,
    dayName: String,
    dayNumber: Int
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .padding(vertical = 12.dp)
            .background(
                if (selectedDay.value == dayName)
                    MaterialTheme.colorScheme.secondary
                else
                    Color.Transparent,
                shape = RoundedCornerShape(20f)
            )
            .selectable(
                selected = selectedDay.value == dayName,
                enabled = true,
                role = Role.Tab,
                onClick = {
                    selectedDay.value = dayName
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 15.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayName,
                color = if (selectedDay.value == dayName) MaterialTheme.colorScheme.background else Color.Black,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = dayNumber.toString(),
                color = if (selectedDay.value == dayName) MaterialTheme.colorScheme.background else Color.Black,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchForTask(
    modifier: Modifier = Modifier,
    value: MutableState<String>
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = value.value,
        onValueChange = { value.value = it },
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
        },
        placeholder = {
            Text(text = "Search Task")
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
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
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
            }
        )
    )
}

@Composable
fun TitleWithSeeAll(
    title: String,
    onSeeAll: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontWeight = FontWeight.Black)
        TextButton(
            onClick = onSeeAll,
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier.absoluteOffset(x = 10.dp)
        ) {
            Text(
                text = "See All",
                modifier = Modifier.padding(start = 10.dp)
            )
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun Folders(
    folders: List<FolderTable>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleWithSeeAll("FOLDERS") {}
        if (folders.isEmpty())
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(20f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                EmptyElements(
                    elementName = "Folder",
                    showGif = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                )
            }
    }
    
}

@Composable
fun Tasks(
    tasks: List<TaskTable>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleWithSeeAll("TASKS") {}
        if (tasks.isEmpty())
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(20f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                ->
                EmptyElements(
                    elementName = "Task",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                )
            }
    }

}