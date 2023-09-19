package com.example.roomtodolist.ui.screens.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.R
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.domain.calendar.DayOfCalendar
import com.example.roomtodolist.domain.calendar.Days
import com.example.roomtodolist.ui.components.ActionBar
import com.example.roomtodolist.ui.components.Container
import com.example.roomtodolist.ui.components.EmptyElements
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    calendarViewModel: CalendarViewModel
) {
    Container(actionBar = {
        ActionBar(title = "Calendar") {
            calendarViewModel.navigateBack()
        }
    }) {
        CalendarUi(
            onPreviousMonth = { calendarViewModel.setPreviousMonth() },
            onNextMonth = { calendarViewModel.setNextMonth() },
            selectedMonth = { calendarViewModel.selectedMonth },
            selectedYear = { calendarViewModel.selectedYear },
            isCompactWidth = calendarViewModel.isCompactWidth(),
            daysOfTheWeek = { calendarViewModel.getDaysOfTheWeek() },
            monthlyCalendar = calendarViewModel.getMonthlyCalendar(),
            selectedDay = { calendarViewModel.selectedDay }
        ) { calendarViewModel.setDay(it) }
        Tasks(
            tasksPerColor = calendarViewModel.orderTasksByTime(),
            isDark = calendarViewModel.isDarkMode(),
            onCreateTask = { calendarViewModel.navigateToAddTaskScreen() }
        )
    }
}

@Composable
private fun Tasks(
    tasksPerColor: HashMap<TaskTable, Color>,
    isDark: Boolean,
    onCreateTask: () -> Unit
) {

    if (tasksPerColor.isEmpty())
        EmptyElements(elementName = "today's tasks", isDark = isDark,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            showGif = false,
            onCreateElement = onCreateTask
        )

    Column(
       modifier = Modifier
           .fillMaxWidth(),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
       tasksPerColor.forEach {
           Row(
               modifier = Modifier
                   .fillMaxWidth(),
               horizontalArrangement = Arrangement.spacedBy(14.dp),
               verticalAlignment = Alignment.CenterVertically
           ) {
               Spacer(modifier = Modifier)
               Text(
                   text = it.key.time.toString(),
                   modifier = Modifier,
                   color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
               )
               Box(modifier = Modifier
                   .size(width = 5.dp, height = 25.dp)
                   .background(it.value)
               )
               Text(
                   text = it.key.title,
                   modifier = Modifier.weight(4f),
                   color = MaterialTheme.colorScheme.onBackground
               )
           }
           Spacer(modifier = Modifier.height(10.dp))
       }
    }
}

@Composable
private fun CalendarUi(
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    selectedMonth: () -> Month,
    selectedYear: () -> Int,
    isCompactWidth: Boolean,
    daysOfTheWeek: () -> Array<Days>,
    monthlyCalendar: List<DayOfCalendar>,
    selectedDay: () -> Int,
    setDay: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(24f)
            )
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MonthPicker(
            onPreviousMonth = onPreviousMonth,
            onNextMonth = onNextMonth,
            selectedMonth = selectedMonth(),
            selectedYear = selectedYear(),
        )
        CalendarGrid(
            isCompactWidth = isCompactWidth,
            daysOfTheWeek = { daysOfTheWeek() },
            monthlyCalendar = { monthlyCalendar },
            selectedDay = { selectedDay() },
            setDay = setDay
        )
    }
}

@Composable
fun CalendarGrid(
    isCompactWidth: Boolean,
    daysOfTheWeek: () -> Array<Days>,
    monthlyCalendar: () -> List<DayOfCalendar>,
    selectedDay: () -> Int,
    setDay: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            daysOfTheWeek().forEach {
                Text(
                    text =
                    if (isCompactWidth) it.oneLetterAbbreviation.toString()
                    else it.threeLetterAbbreviation,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        var dayIndex = 0
        while (dayIndex < monthlyCalendar().size) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (dayOfWeek in 0 until 7) {
                    if (dayIndex == monthlyCalendar().size)
                        break

                    val currentDay = monthlyCalendar()[dayIndex]

                    Box(modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f, matchHeightConstraintsFirst = true)
                        .background(
                            color = if (selectedDay() == currentDay.day && currentDay.inMonth)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Transparent,
                            RoundedCornerShape(24f)
                        )
                        .clickable {
                            if (currentDay.inMonth)
                                setDay(currentDay.day)
                        }
                    ) {
                        Text(
                            text = "${currentDay.day}",
                            textAlign = TextAlign.Center,
                            color = if (currentDay.inMonth)
                                        if (selectedDay() == currentDay.day)
                                            Color.White
                                        else
                                            MaterialTheme.colorScheme.onBackground
                                    else
                                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    dayIndex += 1
                }
            }
        }
    }

}

@Composable
fun MonthPicker(
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    selectedMonth: Month,
    selectedYear: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(
                painter = painterResource(id = R.drawable.outlined_non_lined_arrow_left_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = "$selectedMonth $selectedYear",
            color = MaterialTheme.colorScheme.onBackground
        )
        IconButton(onClick = onNextMonth) {
            Icon(
                painter = painterResource(id = R.drawable.outlined_non_lined_arrow_right_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
