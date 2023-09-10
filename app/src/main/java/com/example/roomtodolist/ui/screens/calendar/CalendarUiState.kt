package com.example.roomtodolist.ui.screens.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
data class CalendarUiState(
    val selectedDay: Int = LocalDate.now().dayOfMonth,
    val selectedMonth: Month = LocalDate.now().month,
    val selectedYear: Int = LocalDate.now().year
)
