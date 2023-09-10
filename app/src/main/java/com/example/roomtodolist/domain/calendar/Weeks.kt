package com.example.roomtodolist.domain.calendar

enum class Weeks(val nextWeekIn: Long) {
    FIRST(nextWeekIn = 0),
    SECOND(nextWeekIn = 7),
    THIRD(nextWeekIn = 14),
    FOURTH(nextWeekIn = 21),
    FIFTH(nextWeekIn = 28)
}