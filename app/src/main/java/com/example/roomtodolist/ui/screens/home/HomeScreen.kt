package com.example.roomtodolist.ui.screens.home

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomtodolist.R
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.domain.calendar.Days
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.EmptyElements
import com.example.roomtodolist.ui.components.FolderCard
import com.example.roomtodolist.ui.components.ProfilePicture
import com.example.roomtodolist.ui.components.TasksPerFolderCards
import com.example.roomtodolist.ui.components.Welcoming
import com.example.roomtodolist.ui.components.defaultTextFieldColors

@SuppressLint("NewApi")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
) {

    val tasksPerFolder = homeViewModel.getTasksPerFolderInSelectedDay()
    var isEmpty = true

    for (tasks in tasksPerFolder.values) {
        if (tasks.isNotEmpty()){
            isEmpty = false
            break
        }
    }

    Container(actionBar = {
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
            profilePicture = homeViewModel.getProfilePicture(),
            settingsOnClick = {
                homeViewModel.navigateToProfileScreen()
            },
            username = homeViewModel.getUsername(),
        )
    }) {
        Spacer(modifier = Modifier)

        WeekCalendar(
            selectedDay = homeViewModel.uiState.selectedDayInCurrentDate,
            setSelectedDay = { homeViewModel.setSelectedDay(it) },
            days = homeViewModel.getDaysOfWeek(),
            isDark = homeViewModel.isDarkMode(),
            isCompactWidth = homeViewModel.isCompactWidth()
        )
        SearchForTask(
            value = homeViewModel.uiState.search,
            onValueChange = { homeViewModel.setSearch(it) }
        )
        Folders(
            folders = homeViewModel.getFolders(),
            onClick = {
                homeViewModel.setFolderToUpdate(it)
                homeViewModel.navigateToFolderShowCase()
            },
            addFolder = { homeViewModel.navigateToAddFolderScreen() },
            isDark = homeViewModel.isDarkMode(),
            seeAll = { homeViewModel.navigateToFoldersScreen() }
        )
        Tasks(
            tasksPerFolder = tasksPerFolder,
            noTaskExists = isEmpty,
            seeAll = { homeViewModel.navigateToTasksScreen() },
            adjustFolder = {
                homeViewModel.setFolderToUpdate(it)
                homeViewModel.navigateToFolderShowCase()
            },
            addTask = { homeViewModel.navigateToAddTaskScreen() },
            onClick = {
                homeViewModel.setTaskToUpdate(it)
                homeViewModel.navigateToTaskShowCaseScreen()
            },
            isDark = homeViewModel.isDarkMode(),
            onSelectTask = {}
        )
        Spacer(modifier = Modifier)
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    profilePicture: Uri?,
    username: String?,
    settingsOnClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProfilePicture(
                modifier = Modifier
                    .size(60.dp),
                picture = profilePicture
            )
            Welcoming(
                modifier = Modifier
                    .padding(horizontal = 5.dp),
                username = username
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
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .clickable { settingsOnClick() },
                painter = painterResource(id = R.drawable.outlined_setting_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Composable
private fun WeekCalendar(
    days: HashMap<Int, Days>,
    isCompactWidth: Boolean,
    selectedDay: Int,
    isDark: Boolean,
    setSelectedDay: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(20f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            days.forEach {
                val dayOfMonth = it.key
                val dayOfWeek = it.value
                val dayName =
                    if (isCompactWidth) dayOfWeek.oneLetterAbbreviation else dayOfWeek.threeLetterAbbreviation
                DayElement(
                    dayName = dayName.toString(),
                    dayNumber = dayOfMonth,
                    selectedDay = selectedDay,
                    setSelectedDay = setSelectedDay,
                    isDark = isDark
                )
            }
        }
    }
}

@Composable
private fun RowScope.DayElement(
    selectedDay: Int,
    setSelectedDay: (Int) -> Unit,
    dayName: String,
    dayNumber: Int,
    isDark: Boolean
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .padding(vertical = 12.dp)
            .background(
                if (selectedDay == dayNumber)
                    MaterialTheme.colorScheme.secondary
                else
                    Color.Transparent,
                shape = RoundedCornerShape(20f)
            )
            .selectable(
                selected = selectedDay == dayNumber,
                enabled = true,
                role = Role.Tab,
                onClick = {
                    setSelectedDay(dayNumber)
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
                color =
                    if (isDark)
                        MaterialTheme.colorScheme.onBackground
                    else {
                        if (selectedDay == dayNumber) Color.White else Color.Black
                    },
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = dayNumber.toString(),
                color =
                    if (isDark)
                        MaterialTheme.colorScheme.onBackground
                    else {
                        if (selectedDay == dayNumber) Color.White else Color.Black
                    },
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun SearchForTask(
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
        },
        placeholder = {
            Text(text = "Search Task")
        },
        colors = defaultTextFieldColors(),
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
        Text(text = title, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onBackground)
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
    folders: List<FolderTable>,
    onClick: (FolderTable) -> Unit,
    addFolder: () -> Unit,
    isDark: Boolean,
    seeAll: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleWithSeeAll("FOLDERS") {
            seeAll()
        }
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
                    onCreateElement = addFolder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    isDark = isDark
                )
            }
        else
            LazyRow() {
                items(items = folders) {
                    FolderCard(
                        folder = it,
                        modifier = Modifier.clickable {
                            onClick(it)
                        }
                    )
                }
            }
    }
    
}

@Composable
fun Tasks(
    tasksPerFolder: HashMap<FolderTable, MutableList<TaskTable>>,
    noTaskExists: Boolean,
    seeAll: () -> Unit,
    isDark: Boolean,
    addTask: () -> Unit,
    onClick: (TaskTable) -> Unit,
    onSelectTask: (TaskTable) -> Unit,
    adjustFolder: (FolderTable) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleWithSeeAll("TASKS") {
            seeAll()
        }

        TasksPerFolderCards(
            tasksPerFolder = tasksPerFolder,
            noTaskExists = noTaskExists,
            addTask = addTask,
            onClick = onClick,
            onSelectTask = onSelectTask,
            onAdjustFolder = adjustFolder,
            isDark = isDark
        )

    }

}