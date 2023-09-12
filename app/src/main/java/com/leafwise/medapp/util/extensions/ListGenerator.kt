package com.leafwise.medapp.util.extensions

import android.content.Context
import com.leafwise.medapp.R
import java.util.Calendar


@Suppress("MagicNumber")
object ListGenerator {

    class NumberListBuilder(private val maxNumber: Int) {
        private val numberList = mutableListOf<Double>()

        fun generateList(): NumberListBuilder {
            for (i in 1..maxNumber) {
                numberList.add(i.toDouble())
            }
            return this
        }

        fun addDecimals(): NumberListBuilder {
            numberList.addAll(listOf(1.5, 2.5))
            return this
        }

        fun build(): Array<String> {
            return numberList.sorted().map {
                if (it % 1 == 0.0) it.toInt().toString() else it.toString()
            }.toTypedArray()
        }
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



    fun generateCalendarList(
        numCalendars: Int,
        baseCalendar: Calendar = Calendar.getInstance(),
    ): List<Calendar> {
        val calendarList = mutableListOf<Calendar>()

        for (i in 0 until numCalendars) {
            val newCalendar = Calendar.getInstance()
            newCalendar.timeInMillis = baseCalendar.timeInMillis
            newCalendar.add(Calendar.HOUR_OF_DAY, i)

            calendarList.add(newCalendar)
        }

        return calendarList
    }


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