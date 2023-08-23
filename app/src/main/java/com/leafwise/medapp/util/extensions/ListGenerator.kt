package com.leafwise.medapp.util.extensions

import android.content.Context
import com.leafwise.medapp.R


@Suppress("MagicNumber")
object ListGenerator {
    fun generateQuantityList(maxNumber: Int): Array<String> {
        val arrayList = arrayListOf<String>()

        for (i in 1..5) {
            if (i.valueIsPair)
                arrayList.add((i / 2).toString())
            else
                arrayList.add((i.toDouble() / 2.0).toString())
        }
        for (i in 3..maxNumber) {
            arrayList.add(i.toString())
        }

        return arrayList.toTypedArray()
    }

    fun Context.generateWeekdaysList(): List<String> =
        listOf(
            "${getString(R.string.every_weekday)} ${getString(R.string.weekday_monday)}",
            "${getString(R.string.every_weekday)} ${getString(R.string.weekday_tuesday)}",
            "${getString(R.string.every_weekday)} ${getString(R.string.weekday_wednesday)}",
            "${getString(R.string.every_weekday)} ${getString(R.string.weekday_thursday)}",
            "${getString(R.string.every_weekday)} ${getString(R.string.weekday_friday)}",
            "${getString(R.string.every_weekday)} ${getString(R.string.weekday_saturday)}",
            "${getString(R.string.every_weekday)} ${getString(R.string.weekday_sunday)}"
        )


//    fun generateAlarmDaysList(): HashMap<Int, String>{
//        val durationLabels = HashMap<Int, String>().apply {
//            // Days
//            for (i in 1..30) {
//                val label = context.resources.getQuantityString(
//                    if (i == 1) R.plurals.day else R.plurals.days,
//                    i, i
//                )
//                put(i, R.plurals.days)
//            }
//
//            // Months
//            for (i in 2..6) {
//                val label = context.resources.getQuantityString(
//                    if (i == 1) R.plurals.month else R.plurals.months,
//                    i, i
//                )
//                put(i * 30, label)
//            }
//
//            // Years
//            for (i in 1..12) {
//                val label = context.resources.getQuantityString(
//                    if (i == 1) R.plurals.year else R.plurals.years,
//                    i, i
//                )
//                put(i * 30 * 12, label)
//            }
//        }
//    }
}