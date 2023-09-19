package com.example.roomtodolist.domain.calendar

import java.time.Month

data class DayOfCalendar(
    val day: Int,
    val month: Month,
    val inMonth: Boolean
)
