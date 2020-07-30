package com.urveshtanna.imgur.ui.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {

    companion object {

        @JvmField
        val DATE_FORMAT_D_MMM = "d MMM"

        @JvmField
        val DATE_FORMAT_DAY_HOURS = "d MMM 'at' hh:mm a"

        @JvmStatic
        fun getFormattedDate(
            date: Date,
            toFormat: String
        ): String {
            val toFormatter = SimpleDateFormat(toFormat, Locale.ENGLISH)
            return toFormatter.format(date)
        }

    }

}