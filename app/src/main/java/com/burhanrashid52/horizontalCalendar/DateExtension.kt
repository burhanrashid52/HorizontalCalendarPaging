package com.burhanrashid52.horizontalCalendar

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Burhanuddin Rashid on 2/1/21.
 * @author  <https://github.com/burhanrashid52>
 */
fun Date.toStringFormat(stringFormat: String = "dd-MM-yyyy"): String {
    val dateFormat = SimpleDateFormat(stringFormat, Locale.getDefault())
    return try {
        dateFormat.format(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        "N/A"
    }
}

fun String.toDate(stringFormat: String = "dd-MM-yyyy"): Date? {
    val format = SimpleDateFormat(stringFormat, Locale.US)
    return try {
        format.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}