package com.burhanrashid52.horizontalCalendar

import java.util.*

internal data class DateRangeMeta(
    val newDateList: List<Date>,
    val nextKey: String?,
    val prevKey: String?
)


internal fun getDateRangeMeta(
    nextDateRange: List<Date>,
    prevDateRange: List<Date>
): DateRangeMeta {
    val endDate = maxDate.toStringFormat().toDate()
    val startDate = minDate.toStringFormat().toDate()
    return when {
        nextDateRange.contains(endDate) -> {
            val index = nextDateRange.indexOf(endDate)
            DateRangeMeta(
                newDateList = nextDateRange.slice(0..index),
                nextKey = null,
                prevKey = prevDateRange.reversed().first().toStringFormat()
            )
        }
        nextDateRange.contains(startDate) -> {
            val index = nextDateRange.indexOf(startDate)
            val newDateRang = nextDateRange.slice(index until nextDateRange.size)
            DateRangeMeta(
                newDateList = newDateRang,
                prevKey = null,
                nextKey = newDateRang.last().toStringFormat()
            )
        }
        else -> {
            DateRangeMeta(
                newDateList = nextDateRange,
                nextKey = nextDateRange.last().toStringFormat(),
                prevKey = prevDateRange.reversed().first().toStringFormat()
            )
        }
    }
}

private const val NUMBER_OF_YEARS = 50

val maxDate: Date
    get() {
        val currentDate = Date()
        val calendar: Calendar = GregorianCalendar()
        calendar.time = currentDate
        calendar.add(Calendar.YEAR, NUMBER_OF_YEARS)
        //calendar.add(Calendar.DATE, 50)
        return calendar.time
    }
val minDate: Date
    get() {
        val currentDate = Date()
        val calendar: Calendar = GregorianCalendar()
        calendar.time = currentDate
        calendar.add(Calendar.YEAR, -NUMBER_OF_YEARS)
        //calendar.add(Calendar.DATE, -50)
        return calendar.time
    }