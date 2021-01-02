package com.burhanrashid52.horizontalCalendar

import java.util.*

data class DateDetailsUI(
    val dateKey: String,
    val day: Int,
    val year: Int,
    val monthName: String,
    val dayOfWeek: String,
    val date: Date,
)

fun Date.toDateDetails(): DateDetailsUI {
    val calendar = GregorianCalendar()
    calendar.time = this

    return DateDetailsUI(
        dateKey = calendar.time.toStringFormat(),
        day = calendar.get(Calendar.DAY_OF_MONTH),
        year = calendar.get(Calendar.YEAR),
        monthName = monthOfYear[calendar.get(Calendar.MONTH)],
        dayOfWeek = dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1],
        date = calendar.time,
    )
}

val dayOfWeek = listOf(
    "SUNDAY",
    "MONDAY",
    "TUESDAY",
    "WEDNESDAY",
    "THURSDAY",
    "FRIDAY",
    "SATURDAY"
)

val monthOfYear = listOf(
    "JANUARY",
    "FEBRUARY",
    "MARCH",
    "APRIL",
    "MAY",
    "JUNE",
    "JULY",
    "AUGUST",
    "SEPTEMBER",
    "OCTOBER",
    "NOVEMBER",
    "DECEMBER",
)
