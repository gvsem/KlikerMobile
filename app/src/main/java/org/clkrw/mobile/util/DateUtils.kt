package org.clkrw.mobile.util

import android.content.Context
import android.text.format.DateUtils.DAY_IN_MILLIS
import android.text.format.DateUtils.getRelativeDateTimeString
import java.text.ParseException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DateUtils {

    companion object {

        fun toString(dateStr: String, context: Context) : CharSequence {
            return try {
                val date: LocalDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME)!!
                getRelativeDateTimeString(context, date.toEpochSecond(ZoneOffset.UTC) * 1000, DAY_IN_MILLIS, DAY_IN_MILLIS, 0)
            } catch (e: ParseException) {
                dateStr
            }
        }

    }
}