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
import com.example.roomtodolist.domain.calendar.Days
import com.example.roomtodolist.domain.main_activity.MainViewModel
import com.example.roomtodolist.ui.navigation.MainRoutes
import java.time.LocalDate
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel(private val mainViewModel: MainViewModel) : ViewModel() {


    var selectedDay by mutableStateOf(LocalDate.now().dayOfMonth)
        private set
    var selectedMonth: Month by mutableStateOf(LocalDate.now().month)
        private set
    var selectedYear by mutableStateOf(LocalDate.now().year)
        private set

    fun navigateToAddTaskScreen() {
        mainViewModel.navigateTo(MainRoutes.ADD_TASK.name)
    }

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

    fun setNextMonth() {
        selectedDay = 1
        selectedMonth = selectedMonth.plus(1)
        selectedYear =
            if (selectedMonth == Month.DECEMBER)
                selectedYear + 1
            else
                selectedYear

    }

    fun setPreviousMonth() {
        selectedDay = 1
        selectedMonth = selectedMonth.minus(1)
        selectedYear =
            if (selectedMonth == Month.JANUARY)
                selectedYear - 1
            else
                selectedYear
    }

    fun setDay(dayOfMonth: Int) {
        selectedDay = dayOfMonth
    }

    fun isDarkMode(): Boolean = mainViewModel.uiState.isDarkTheme

    fun getDaysOfTheWeek() = Days.values()

    fun isCompactWidth() =
        mainViewModel.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    fun getMonthlyCalendar() =
        mainViewModel.getMonthForFiveWeeks(selectedMonth, selectedYear)

    private fun getSelectedDate() = LocalDate.of(
        selectedYear,
        selectedMonth,
        selectedDay
    )

    private fun filterTasksByCurrentDay() : HashMap<TaskTable, Color> {
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
                if (tasks[taskIndex].time.isBefore(firstTask.time))
                    firstTask = tasks[taskIndex]
            }
            returnValue[firstTask] = element.value
            tasks.remove(firstTask)
        }
        return returnValue
    }

}