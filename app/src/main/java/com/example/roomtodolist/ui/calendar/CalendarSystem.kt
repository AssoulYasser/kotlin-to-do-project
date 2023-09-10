package com.example.roomtodolist.ui.calendar

import android.os.Build
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
    ) : List<Int> {

        val firstDateInMonth = LocalDate.of(year, month, 1)

        val daysInMonth = firstDateInMonth.lengthOfMonth()

        val monthlyCalendar = mutableListOf<Int>()

        val firstDateInCalendar = if (firstDateInMonth.dayOfWeek != DayOfWeek.MONDAY){
            val distanceBetweenFirstDayAndStartOfWeek =
                firstDateInMonth.dayOfWeek.value.toLong() - START_OF_WEEK.value.toLong()
            firstDateInMonth.minusDays(distanceBetweenFirstDayAndStartOfWeek)
        }
        else firstDateInMonth

        var iteratorDate = firstDateInCalendar

        var lastDateInCalendar = firstDateInMonth
            .plusDays(daysInMonth.toLong())

        while (lastDateInCalendar.dayOfWeek != START_OF_WEEK)
            lastDateInCalendar = lastDateInCalendar.plusDays(1)

        while (iteratorDate.isBefore(lastDateInCalendar)) {
            monthlyCalendar.add(iteratorDate.dayOfMonth)
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

        val weeklyCalendar = hashMapOf<Int, Days>()

        while (iterableDate.dayOfMonth.toLong() < lastDateInTheWeek.dayOfMonth.toLong()) {

            val dayOfWeek = Days.getDayOfWeek(iterableDate.dayOfWeek)
            weeklyCalendar[iterableDate.dayOfMonth] = dayOfWeek
            iterableDate = iterableDate.plusDays(1)

        }

        return weeklyCalendar
    }
//
//    private fun getDateInCurrentWeek(day: Days): LocalDate {
//        val distanceBetweenTodayAndStartOfWeek =
//            currentDate.date.dayOfWeek.value.toLong() - START_OF_WEEK.value.toLong()
//        val startOfWeekDate = currentDate.date.minusDays(distanceBetweenTodayAndStartOfWeek)
//        val distanceBetweenStartOfWeekAndTargetDay =
//            day.dayOfWeek.value.toLong() - startOfWeekDate.dayOfWeek.value.toLong()
//
//        return startOfWeekDate.plusDays(distanceBetweenStartOfWeekAndTargetDay)
//    }
//
//    private fun getDayInCurrentWeek(day: Days) = getDateInCurrentWeek(day).dayOfWeek

    companion object {
        val START_OF_WEEK = Days.values().first().dayOfWeek
    }

}