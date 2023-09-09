package com.example.roomtodolist.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
data class CurrentDate(
    val date: LocalDate = LocalDate.now(),
    val currentDayOfWeek: DaysOfWeek = DaysOfWeek.getDayOfWeek(date.dayOfWeek),
    val currentDayOfMonth: Int = date.dayOfMonth,
    val currentDaysInMonth: Int = date.lengthOfMonth(),
    val currentMonth: Month = date.month,
    val currentYear: Int = date.year
)
