package com.example.roomtodolist.domain.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
class CalendarSystem {

    fun getMonthlyCalendarByDayName(
        year: Int,
        month: Month
    ) : HashMap<Int, Days> {

        val firstDateOfMonth = LocalDate.of(year, month, 1)

        val daysInMonth = firstDateOfMonth.lengthOfMonth()

        val monthlyCalendar = hashMapOf<Int, Days>()

        for (dayOfMonth in 1..daysInMonth) {
            val date = LocalDate.of(year, month, dayOfMonth)
            val dayOfWeek = Days.getDayOfWeek(date.dayOfWeek)
            monthlyCalendar[dayOfMonth] = dayOfWeek
        }
        return monthlyCalendar
    }

    fun getMonthlyCalendar(
        year: Int,
        month: Month
    ) : List<DayOfCalendar> {

        val firstDateInMonth = LocalDate.of(year, month, 1)

        val daysInMonth = firstDateInMonth.lengthOfMonth()

        val monthlyCalendar = mutableListOf<DayOfCalendar>()

        val firstDateInCalendar = if (firstDateInMonth.dayOfWeek != DayOfWeek.MONDAY){
            val distanceBetweenFirstDayAndStartOfWeek =
                firstDateInMonth.dayOfWeek.value.toLong() - START_OF_WEEK.value.toLong()
            firstDateInMonth.minusDays(distanceBetweenFirstDayAndStartOfWeek)
        }
        else firstDateInMonth

        var iteratorDate = firstDateInCalendar

        var isInside = false

        var lastDateInCalendar = firstDateInMonth
            .plusDays(daysInMonth.toLong())

        while (lastDateInCalendar.dayOfWeek != START_OF_WEEK)
            lastDateInCalendar = lastDateInCalendar.plusDays(1)

        while (iteratorDate.isBefore(lastDateInCalendar)) {
            if (iteratorDate.dayOfMonth == 1) isInside = !isInside
            monthlyCalendar.add(
                DayOfCalendar(
                    day = iteratorDate.dayOfMonth,
                    month = iteratorDate.month,
                    inMonth = isInside
                )
            )
            iteratorDate = iteratorDate.plusDays(1)
        }
        return monthlyCalendar
    }

    fun getWeeklyCalendar(
        year: Int,
        month: Month,
        week: Weeks
    ) : HashMap<Int, Days> {
        val firstDateOfMonth = LocalDate.of(year, month, 1)

        val distanceBetweenFirstDayAndStartOfWeek =
            firstDateOfMonth.dayOfWeek.value.toLong() - START_OF_WEEK.value.toLong()

        val firstDateInCalendar = firstDateOfMonth.minusDays(distanceBetweenFirstDayAndStartOfWeek)

        val firstDateInTheWeek = firstDateInCalendar.plusDays(week.nextWeekIn)

        var iterableDate = firstDateInTheWeek

        val lastDateInTheWeek = firstDateInTheWeek.plusDays(7)

        val weeklyCalendar = hashMapOf<Int, Days>()

        while (iterableDate.dayOfMonth.toLong() < lastDateInTheWeek.dayOfMonth.toLong()) {

            val dayOfWeek = Days.getDayOfWeek(iterableDate.dayOfWeek)
            weeklyCalendar[iterableDate.dayOfMonth] = dayOfWeek
            iterableDate = iterableDate.plusDays(1)

        }

        return weeklyCalendar
    }

    fun getWeeklyCalendar(
        year: Int,
        month: Month,
        day: Int
    ) : HashMap<Int, Days> {
        val dateOfTheDay = LocalDate.of(year, month, day)

        val distanceBetweenDateOfTheDayAndStartOfWeek =
            dateOfTheDay.dayOfWeek.value.toLong() - START_OF_WEEK.value.toLong()

        val firstDateInTheWeek = dateOfTheDay.minusDays(distanceBetweenDateOfTheDayAndStartOfWeek)

        var iterableDate = firstDateInTheWeek

        val lastDateInTheWeek = firstDateInTheWeek.plusDays(7)

        val weeklyCalendar = linkedMapOf<Int, Days>()

        while (iterableDate.month.value < lastDateInTheWeek.month.value || iterableDate.dayOfMonth.toLong() < lastDateInTheWeek.dayOfMonth.toLong()) {

            val dayOfWeek = Days.getDayOfWeek(iterableDate.dayOfWeek)
            weeklyCalendar[iterableDate.dayOfMonth] = dayOfWeek
            iterableDate = iterableDate.plusDays(1)

        }

        return weeklyCalendar
    }

    companion object {
        val START_OF_WEEK = Days.values().first().dayOfWeek
    }

}