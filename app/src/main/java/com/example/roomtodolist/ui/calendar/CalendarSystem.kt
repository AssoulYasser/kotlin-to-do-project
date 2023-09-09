package com.example.roomtodolist.ui.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CalendarSystem {

    val currentDate = CurrentDate()
    val currentMonthlyCalendar: HashMap<Int, DaysOfWeek> = hashMapOf()
    val currentWeeklyCalendar: HashMap<Int, DaysOfWeek> = hashMapOf()

    init {
        setCurrentMonthlyCalendar()
        setCurrentWeeklyCalendar()
    }

    private fun setCurrentMonthlyCalendar() {
        for (dayOfMonth in 1..currentDate.currentDaysInMonth) {
            val date = LocalDate.of(currentDate.currentYear, currentDate.currentMonth, dayOfMonth)
            val dayOfWeek = DaysOfWeek.getDayOfWeek(date.dayOfWeek)
            currentMonthlyCalendar[dayOfMonth] = dayOfWeek
        }
    }

    private fun setCurrentWeeklyCalendar() {
        val distanceBetweenTodayAndStartOfWeek =
            currentDate.date.dayOfWeek.value.toLong() - START_OF_WEEK.value.toLong()
        var startOfWeekDate = currentDate.date.minusDays(distanceBetweenTodayAndStartOfWeek)
        val endOfWeekDate = startOfWeekDate.plusDays(7)
        while (startOfWeekDate.dayOfMonth.toLong() < endOfWeekDate.dayOfMonth.toLong()) {
            val dayOfWeek = DaysOfWeek.getDayOfWeek(startOfWeekDate.dayOfWeek)
            currentWeeklyCalendar[startOfWeekDate.dayOfMonth] = dayOfWeek
            startOfWeekDate = startOfWeekDate.plusDays(1)
        }
    }

    private fun getDateInCurrentWeek(day: DaysOfWeek): LocalDate {
        val distanceBetweenTodayAndStartOfWeek =
            currentDate.date.dayOfWeek.value.toLong() - START_OF_WEEK.value.toLong()
        val startOfWeekDate = currentDate.date.minusDays(distanceBetweenTodayAndStartOfWeek)
        val distanceBetweenStartOfWeekAndTargetDay =
            day.dayOfWeek.value.toLong() - startOfWeekDate.dayOfWeek.value.toLong()

        return startOfWeekDate.plusDays(distanceBetweenStartOfWeekAndTargetDay)
    }
    
    private fun getDayInCurrentWeek(day: DaysOfWeek) = getDateInCurrentWeek(day).dayOfWeek

    companion object {
        val START_OF_WEEK = DaysOfWeek.values().first().dayOfWeek
    }

}