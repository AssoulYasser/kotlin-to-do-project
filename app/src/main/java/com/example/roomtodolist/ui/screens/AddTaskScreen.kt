package com.example.roomtodolist.ui.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.roomtodolist.R
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.navigation.NavDestination
import com.example.roomtodolist.ui.navigation.navigateTo
import com.example.roomtodolist.ui.theme.StateColors
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import java.util.Locale

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
            PriorityCard()
            DateCard()
        }
    }

}

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
private fun TaskTitle(
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
            .height(120.dp)
    )
}

@Composable
fun PriorityCard(
    selectedPriority: MutableState<String?> = remember { mutableStateOf(null) }
) {
    fun getColor(button: String) : Color {
        return when(button) {
            "Low" -> {
                if (selectedPriority.value == button)
                    Color.Green
                else
                    Color.Transparent
            }
            "Medium" -> {
                if (selectedPriority.value == button)
                    Color.Blue
                else
                    Color.Transparent
            }
            "High" -> {
                if (selectedPriority.value == button)
                    Color.Red
                else
                    Color.Transparent
            }

            else -> Color.Transparent
        }
    }
    ExpandableCard(icon = R.drawable.outlined_document_favorite_icon, title = "Set Priority") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { selectedPriority.value = "Low" },
                colors = ButtonDefaults.buttonColors(containerColor = getColor("Low")),
                border = BorderStroke(color = Color.Green, width = 2.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
            ) {
                Text(text = "Low", fontSize = 12.sp, color = if (selectedPriority.value == "Low") Color.White else Color.Black)
            }
            Button(
                onClick = { selectedPriority.value = "Medium" },
                colors = ButtonDefaults.buttonColors(containerColor = getColor("Medium")),
                border = BorderStroke(color = Color.Blue, width = 2.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
            ) {
                Text(text = "Medium", fontSize = 12.sp, color = if (selectedPriority.value == "Medium") Color.White else Color.Black)
            }
            Button(
                onClick = { selectedPriority.value = "High" },
                colors = ButtonDefaults.buttonColors(containerColor = getColor("High")),
                border = BorderStroke(color = Color.Red, width = 2.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
            ) {
                Text(
                    text = "High",
                    fontSize = 12.sp,
                    color = if (selectedPriority.value == "High") Color.White else Color.Black
                )
            }
        }
    }

}

@Composable
fun DateCard(
    dueTo: MutableState<LocalDate?> = remember { mutableStateOf(null) }
) {
    MyCalendarDialog(dueTo)
    ExpandableCard(icon = R.drawable.outlined_calendar_add_icon, title = "Set Date") {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "due to: ")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCalendarDialog(dueTo: MutableState<LocalDate?>) {
    val context = LocalContext.current
    CalendarDialog(
        state = UseCaseState(
            visible = true
        ),
        selection = CalendarSelection.Date { date ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                if (date.isAfter(LocalDate.now()) or date.isEqual(LocalDate.now()))
                    dueTo.value = date
                else
                    Toast.makeText(context, "NO", Toast.LENGTH_LONG).show()
            else
                dueTo.value = date
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        )
    )
}