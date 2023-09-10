package com.example.roomtodolist.ui.screens.home

import java.time.LocalDate

data class HomeUiState(
    val search: String = "",
    val selectedDayInCurrentDate: Int = LocalDate.now().dayOfMonth,
)
