package com.example.roomtodolist.ui.screens.addtask

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomtodolist.R
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskPriority
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.AddElementCard
import com.example.roomtodolist.ui.components.FolderCard
import com.example.roomtodolist.ui.components.ValidationButtons
import com.example.roomtodolist.ui.components.defaultTextFieldColors
import com.example.roomtodolist.ui.components.defaultTextFieldShape
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun AddTaskScreen(
    addTaskViewModel: AddTaskViewModel
) {
    val context = LocalContext.current
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
            addTaskViewModel.navigateToHomeScreen()
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier)
            TaskTitle(
                value = addTaskViewModel.uiState.taskTitle,
                onValueChange = { addTaskViewModel.setTaskTitle(it) }
            )
            PriorityCard(
                selectedPriority = addTaskViewModel.uiState.taskPriority,
                onSelectPriority = { addTaskViewModel.setPriority(it) }
            )
            DateCard(
                date = addTaskViewModel.uiState.date,
                time = addTaskViewModel.uiState.time,
                onDateChange = {
                    addTaskViewModel.setDate(it)
                },
                onTimeChange = {
                    addTaskViewModel.setTime(it)
                }
            )
            FoldersCard(
                selectedFolder = addTaskViewModel.uiState.folder,
                folders = addTaskViewModel.getFolders(),
                onAddFolder = { addTaskViewModel.navigateToAddFolderScreen() },
                onSelectFolder = { addTaskViewModel.setFolder(it) }
            )
            ValidationButtons(
                onSave = {
                    if (addTaskViewModel.isReadyToSave()) {
                        addTaskViewModel.save()
                        addTaskViewModel.navigateToHomeScreen()
                        addTaskViewModel.showSuccessMessage(context = context)
                        addTaskViewModel.clear()
                    } else {
                        addTaskViewModel.showErrorMessage(context = context)
                    }
                },
                onCancel = {
                    addTaskViewModel.clear()
                    addTaskViewModel.navigateToHomeScreen()
                }
            )
            Spacer(modifier = Modifier)
        }
    }

}

@Composable
fun FoldersCard(
    folders: List<FolderTable>,
    onAddFolder: () -> Unit,
    onSelectFolder: (FolderTable) -> Unit,
    selectedFolder: FolderTable?
) {

    @Composable
    fun RowScope.Folder(index: Int) {
        FolderCard(
            folder = folders[index],
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onSelectFolder(folders[index])
                },
            showArrowIcon = false,
            color =
                if (selectedFolder == folders[index])
                    MaterialTheme.colorScheme.primaryContainer
                else
                    Color.Transparent,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer)
        )
    }

    ExpandableCard(icon = R.drawable.outlined_folder_add_icon, title = "Set Folder") {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            for (folderIndex in folders.indices step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Folder(index = folderIndex)
                    if (folderIndex + 1 < folders.size)
                        Folder(index = folderIndex + 1)
                    else
                        AddElementCard(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 25.dp)
                        ) {
                            onAddFolder()
                        }
                }
            }
            if (folders.size % 2 == 0)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.Start),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AddElementCard(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(vertical = 25.dp)
                    ) {
                        onAddFolder()
                    }
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandableCard(
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

@Composable
private fun TaskTitle(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = defaultTextFieldColors(),
        shape = defaultTextFieldShape(),
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            Text(text = "Task title")
        }
    )
}

@Composable
fun PriorityCard(
    selectedPriority: TaskPriority,
    onSelectPriority: (TaskPriority) -> Unit
) {
    fun getContainerColor(selectedButton: TaskPriority) : Color {
        return if (selectedButton == selectedPriority)
                selectedPriority.getPriorityColor()
            else
                Color.Transparent
    }
    fun getContentColor(selectedButton: TaskPriority) : Color
        = if (selectedPriority == selectedButton) Color.White else Color.Black
    ExpandableCard(icon = R.drawable.outlined_document_favorite_icon, title = "Set Priority") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onSelectPriority(TaskPriority.LOW) },
                colors = ButtonDefaults.buttonColors(containerColor = getContainerColor(TaskPriority.LOW)),
                border = BorderStroke(color = Color.Green, width = 2.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                shape = RoundedCornerShape(20f)
            ) {
                Text(
                    text = "Low",
                    fontSize = 12.sp,
                    color = getContentColor(TaskPriority.LOW)
                )
            }
            Button(
                onClick = { onSelectPriority(TaskPriority.MEDIUM) },
                colors = ButtonDefaults.buttonColors(containerColor = getContainerColor(TaskPriority.MEDIUM)),
                border = BorderStroke(color = Color.Blue, width = 2.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                shape = RoundedCornerShape(20f)
            ) {
                Text(
                    text = "Medium",
                    fontSize = 12.sp,
                    color = getContentColor(TaskPriority.MEDIUM)
                )
            }
            Button(
                onClick = { onSelectPriority(TaskPriority.HIGH) },
                colors = ButtonDefaults.buttonColors(containerColor = getContainerColor(TaskPriority.HIGH)),
                border = BorderStroke(color = Color.Red, width = 2.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                shape = RoundedCornerShape(20f)
            ) {
                Text(
                    text = "High",
                    fontSize = 12.sp,
                    color = getContentColor(TaskPriority.HIGH)
                )
            }
        }
    }

}

@Composable
fun DateCard(
    date: LocalDate?,
    time: LocalTime?,
    onDateChange: (LocalDate) -> Unit,
    onTimeChange: (LocalTime) -> Unit
) {
    val isCalendarVisible = remember { mutableStateOf(false) }
    val isClockVisible = remember { mutableStateOf(false) }
    MyCalendarDialog(setDate = onDateChange, time = time, isVisible = isCalendarVisible)
    MyClockDialog(setTime = onTimeChange, date = date, isVisible = isClockVisible)

    val dateUi = remember { mutableStateOf("--/--/----") }

    dateUi.value = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
        date?.toString() ?: "yyyy/mm/dd"
    else
        if (date == null)
            "--/--/----"
        else
            "${date.dayOfMonth}/" +
                    "${date.monthValue}/" +
                    "${date.year}"

    val timeUi = remember { mutableStateOf("--:--") }

    timeUi.value =

        if (time == null)
            "--:--"
        else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                time.toString()

            else if (time.hour/10 > 0 && time.minute /10 >0)
                "${time.hour}:${time.minute}"

            else if (time.hour /10 > 0)
                "${time.hour}:0${time.minute}"

            else if (time.minute /10 > 0)
                "0${time.hour}:${time.minute}"

            else
                "0${time.hour}:0${time.minute}"
        }

    ExpandableCard(icon = R.drawable.outlined_calendar_add_icon, title = "Set Date") {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "due to: ",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(0.5f)
                )
                Text(
                    text = dateUi.value,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { isCalendarVisible.value = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = 1.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                    shape = RoundedCornerShape(20f),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp)
                ) {
                    Text(
                        text = "set date",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "At: ", color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(0.5f))
                Text(
                    text = timeUi.value,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { isClockVisible.value = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = 1.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                    shape = RoundedCornerShape(20f),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp)
                ) {
                    Text(text = "set time", fontSize = 10.sp, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCalendarDialog(
    setDate: (LocalDate) -> Unit,
    time: LocalTime?,
    isVisible: MutableState<Boolean>
) {
    val context = LocalContext.current
    CalendarDialog(
        state = UseCaseState(
            visible = isVisible.value,
            onCloseRequest = {
                isVisible.value = false
            }
        ),
        selection = CalendarSelection.Date { date ->
            isVisible.value = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (time == null) {
                    if (date.isBefore(LocalDate.now()))
                        Toast.makeText(context, "Invalid date", Toast.LENGTH_LONG).show()
                    else
                        setDate(date)
                }
                else {
                    if ((date.isEqual(LocalDate.now()) &&
                            (time.hour < LocalTime.now().hour ||
                                (time.hour == LocalTime.now().hour &&
                                    time.minute < LocalTime.now().minute))) ||
                        date.isBefore(LocalDate.now())
                    ) {
                        Toast.makeText(context, "Invalid date", Toast.LENGTH_LONG).show()
                    } else {
                        setDate(date)
                    }
                }
            }
            else {
                setDate(date)
            }
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyClockDialog(
    setTime: (LocalTime) -> Unit,
    date: LocalDate?,
    isVisible: MutableState<Boolean>
) {
    val context = LocalContext.current
    ClockDialog(
        state = UseCaseState(visible = isVisible.value, onCloseRequest = {isVisible.value = false }),
        selection = ClockSelection.HoursMinutes { h, m ->

            isVisible.value = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val localTime = LocalTime.of(h, m)
                if (date == null) {
                    setTime(localTime)
                }
                else{
                    if (date.isEqual(LocalDate.now())){
                        if (h > LocalTime.now().hour){
                            setTime(localTime)
                        } else {
                            if (h != LocalTime.now().hour || m < LocalTime.now().minute)
                                Toast.makeText(context, "Invalid Time", Toast.LENGTH_LONG).show()
                            else{
                                setTime(localTime)
                            }
                        }
                    }
                    else if(date.isBefore(LocalDate.now())){
                        Toast.makeText(context, "Invalid date", Toast.LENGTH_LONG).show()
                    }
                    else {
                        setTime(localTime)
                    }
                }
            }
        }
    )
}