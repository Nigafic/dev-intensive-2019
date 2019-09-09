package com.example.devintensive.extensions

import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = SECOND * 60
const val HOUR = MINUTE * 60
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {

        var time = this.time

        time += when (units) {
            TimeUnits.SECOND -> value * SECOND
            TimeUnits.MINUTE -> value * MINUTE
            TimeUnits.HOUR -> value * HOUR
            TimeUnits.DAY -> value * DAY
        }
        this.time = time

        return this
    }


private val UNITS_MAP = mapOf(
    SECOND to Triple("секунду", "секунды", "секунд"),
    MINUTE to Triple("минуту", "минуты", "минут"),
    HOUR to Triple("час", "часа", "часов"),
    DAY to Triple("день", "дня", "дней")
)

fun Date.humanizeDiff(date: Date = Date()): String {
    val future = this.time - date.time > 0
    val diffSec = with (abs(this.time - date.time) / 1000) { if (future) this + 1 else this}


    var result = when(diffSec) {
        in 0..1 -> "только что"
        in 1..45 -> "${if (future) "через " else "" }несколько секунд${if (!future) " назад" else "" }"
        in 45..75 -> getPastOrFuturePhrase(future, MINUTE)
        in 75..45 * 60 -> getPastOrFuturePhrase(future, MINUTE, diffSec / 60)
        in 60 * 45..60 * 75 -> getPastOrFuturePhrase(future, HOUR)
        in 60 * 75..60 * 60 * 22 -> getPastOrFuturePhrase(future, HOUR, diffSec / (60 * 60))
        in 60 * 60 * 22..60 * 60 * 26 -> getPastOrFuturePhrase(future, DAY)
        in 60 * 60 * 26..60 * 60 * 24 * 360 -> getPastOrFuturePhrase(future, DAY, diffSec / (60 * 60 * 24))
        else -> if (future) "более чем через год" else "более года назад"
    }

    return result
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;
}

private fun getTimeUnit(count: Long, type: Long): String? {
    var newCount = count
    if (newCount > 100) newCount %= 100
    if (newCount > 20) newCount %= 10
    return when (newCount) {
        1L -> UNITS_MAP[type]?.first
        2L, 3L, 4L -> UNITS_MAP[type]?.second
        else -> UNITS_MAP[type]?.third
    }
}

private fun getPastOrFuturePhrase(future: Boolean, type: Long, count: Long = 1L): String =
    "${if (future) "через " else "" }${if (count > 1L) "$count " else "" }" +
            "${getTimeUnit(count, type)}${if (!future) " назад" else "" }"