package com.example.roomtodolist.ui.screens.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.ui.calendar.Days
import com.example.roomtodolist.ui.screens.MainViewModel
import java.time.LocalDate
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel(private val mainViewModel: MainViewModel) : ViewModel() {

    var uiState by mutableStateOf(CalendarUiState())
        private set

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

    fun setNextMonth() {
        uiState = uiState.copy(
            selectedDay = 1,
            selectedMonth = uiState.selectedMonth.plus(1),
            selectedYear =
                if (uiState.selectedMonth == Month.DECEMBER)
                    uiState.selectedYear + 1
                else
                    uiState.selectedYear
        )
    }

    fun setPreviousMonth() {
        uiState = uiState.copy(
            selectedDay = 1,
            selectedMonth = uiState.selectedMonth.minus(1),
            selectedYear =
            if (uiState.selectedMonth == Month.JANUARY)
                uiState.selectedYear + -1
            else
                uiState.selectedYear
        )
    }

    fun setDay(dayOfMonth: Int) {
        uiState = uiState.copy(selectedDay = dayOfMonth)
    }

    fun getDaysOfTheWeek() = Days.values()

    fun isCompactWidth() =
        mainViewModel.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    fun getMonthlyCalendar() =
        mainViewModel.getMonthForFiveWeeks(uiState.selectedMonth, uiState.selectedYear).toList()

    private fun getSelectedDate() = LocalDate.of(
        uiState.selectedYear,
        uiState.selectedMonth,
        uiState.selectedDay
    )

    fun filterTasksByCurrentDay() : HashMap<TaskTable, Color> {
        val tasksPerFolder = mainViewModel.uiState.tasksPerFolder
        val returnValue = HashMap<TaskTable, Color>()
        for ((folder, tasks) in tasksPerFolder) {
            for(task in tasks) {
                if (task.date.isEqual(getSelectedDate()))
                    returnValue[task] = Color(folder.color)
            }
        }
        return returnValue
    }

    fun orderTasksByTime() : HashMap<TaskTable, Color> {
        val tasksPerColor = filterTasksByCurrentDay()
        val returnValue = LinkedHashMap<TaskTable, Color>()
        val tasks = tasksPerColor.keys.toMutableList()
        for (element in tasksPerColor) {
            var firstTask = tasks.first()
            for (taskIndex in 0 until tasks.size) {
                if (tasks[taskIndex].date.isBefore(firstTask.date))
                    firstTask = tasks[taskIndex]
            }
            returnValue[firstTask] = element.value
            tasks.remove(firstTask)
        }
        return returnValue
    }

}