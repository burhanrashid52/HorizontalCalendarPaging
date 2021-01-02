package com.burhanrashid52.horizontalCalendar

import android.util.Log
import androidx.paging.PagingSource
import java.util.*

class DateSelectionPageSource(private val startDate: String) : PagingSource<String, Date>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Date> {
        return try {
            val key = params.key ?: startDate
            val loadSize = 30
            val nextDateRange: List<Date> = addDayToDate(key.toDate()!!, loadSize - 1)
            val prevDateRange: List<Date> = removeDayToDate(key.toDate()!!, loadSize.unaryMinus())

            val (newDateList, nextKey, prevKey) = getDateRangeMeta(nextDateRange, prevDateRange)
            Log.d("DateSource", "Next Key = $nextKey")
            Log.d("DateSource", "Previous Key = $prevKey")

            LoadResult.Page(
                    data = newDateList,
                    prevKey = prevKey,
                    nextKey = nextKey
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

}


private fun addDayToDate(startDate: Date, numberOfDays: Int): List<Date> {
    val datesInRange = mutableListOf<Date>()

    val startCalendar: Calendar = GregorianCalendar()
    startCalendar.time = startDate

    val endCalendar = GregorianCalendar()
    endCalendar.time = startDate
    endCalendar.add(Calendar.DATE, numberOfDays)

    while (startCalendar.before(endCalendar)) {
        datesInRange.add(startCalendar.time)
        startCalendar.add(Calendar.DATE, 1)
    }

    return datesInRange
}

fun removeDayToDate(startDate: Date, numberOfDays: Int): List<Date> {
    val datesInRange = mutableListOf<Date>()
    val startCalendar: Calendar = GregorianCalendar()
    startCalendar.time = startDate

    val endCalendar = GregorianCalendar()
    endCalendar.time = startDate
    endCalendar.add(Calendar.DATE, numberOfDays)

    while (startCalendar.after(endCalendar)) {
        datesInRange.add(startCalendar.time)
        startCalendar.add(Calendar.DATE, -1)
    }
    return datesInRange
}