package ankin.java.util

import ankin.kotlin.toCalendar
import java.util.*

/**
 * 帝鲮于 2018/2/15创建.
 */
var Calendar.year: Int
    get() {
        return this.get(Calendar.YEAR)
    }
    set(value) {
        set(Calendar.YEAR, value)
    }


var Calendar.month: Int
    get() {
        return this.get(Calendar.MONTH)
    }
    set(value) {
        set(Calendar.MONTH, value)
    }

var Calendar.dayOfMonth: Int
    get() {
        return this.get(Calendar.DAY_OF_MONTH)
    }
    set(value) {
        set(Calendar.DAY_OF_MONTH, value)
    }

var Calendar.dayOfYear: Int
    get() {
        return this.get(Calendar.DAY_OF_YEAR)
    }
    set(value) {
        set(Calendar.DAY_OF_YEAR, value)
    }

var Calendar.weekOfMonth: Int
    get() {
        return this.get(Calendar.WEEK_OF_MONTH)
    }
    set(value) {
        set(Calendar.WEEK_OF_MONTH, value)
    }

var Calendar.weekOfYear: Int
    get() {
        return this.get(Calendar.WEEK_OF_YEAR)
    }
    set(value) {
        set(Calendar.WEEK_OF_YEAR, value)
    }

var Calendar.dayOfWeek: Int
    get() {
        return this.get(Calendar.DAY_OF_WEEK)
    }
    set(value) {
        set(Calendar.DAY_OF_WEEK, value)
    }
var Calendar.hour: Int
    get() {
        return this.get(Calendar.HOUR)
    }
    set(value) {
        set(Calendar.HOUR, value)
    }
var Calendar.hourOfDay: Int
    get() {
        return this.get(Calendar.HOUR_OF_DAY)
    }
    set(value) {
        set(Calendar.HOUR_OF_DAY, value)
    }
var Calendar.minute:Int
    get() {
        return this.get(Calendar.MINUTE)
    }
    set(value) {
        set(Calendar.MINUTE, value)
    }
var Calendar.second: Int
    get() {
        return this.get(Calendar.SECOND)
    }
    set(value) {
        set(Calendar.SECOND, value)
    }
var Calendar.millisecond: Int
    get() {
        return this.get(Calendar.MILLISECOND)
    }
    set(value) {
        set(Calendar.MILLISECOND, value)
    }

fun Calendar.isOneDay(otherCalendar: Calendar): Boolean {
    return year == otherCalendar.year && dayOfYear == otherCalendar.dayOfYear
}

fun Calendar.isOneDay(time: Long): Boolean {
    return isOneDay(time.toCalendar())
}