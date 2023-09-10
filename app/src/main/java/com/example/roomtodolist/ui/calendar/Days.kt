package com.example.roomtodolist.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek

@RequiresApi(Build.VERSION_CODES.O)
enum class Days(
    val threeLetterAbbreviation: String,
    val oneLetterAbbreviation: Char,
    val dayOfWeek: DayOfWeek
) {
    MONDAY("Mon", 'M', DayOfWeek.MONDAY),
    TUESDAY("Tue", 'T', DayOfWeek.TUESDAY),
    WEDNESDAY("Wed", 'W', DayOfWeek.WEDNESDAY),
    THURSDAY("Thu", 'T', DayOfWeek.THURSDAY),
    FRIDAY("Fri", 'F', DayOfWeek.FRIDAY),
    SATURDAY("Sat", 'S', DayOfWeek.SATURDAY),
    SUNDAY("Sun", 'S', DayOfWeek.SUNDAY);

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun getDayOfWeek(dayOfWeek: DayOfWeek): Days = when (dayOfWeek) {
            DayOfWeek.MONDAY -> MONDAY
            DayOfWeek.TUESDAY -> TUESDAY
            DayOfWeek.WEDNESDAY -> WEDNESDAY
            DayOfWeek.THURSDAY -> THURSDAY
            DayOfWeek.FRIDAY -> FRIDAY
            DayOfWeek.SATURDAY -> SATURDAY
            DayOfWeek.SUNDAY -> SUNDAY
        }
    }
}
