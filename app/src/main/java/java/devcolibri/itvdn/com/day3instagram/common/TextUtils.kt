package java.devcolibri.itvdn.com.day3instagram.common

import android.text.format.DateUtils
import java.util.*

fun formatRelativeTimestamp(start: Date, end: Date): String =
    DateUtils.getRelativeTimeSpanString(start.time, end.time, DateUtils.SECOND_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_RELATIVE).replace(Regex("\\. ago$"), "")