package ankin.kotlin

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 帝鲮于 2018/2/15创建.
 */
var NUMBER_NOT_FOUND = -940482458

val Long.dataSizeString:String
    get() {
        val formater = DecimalFormat("####.00")
        val size = this
        if (size < 1024) {
            return size.toString() + "B"
        } else if (size < 1024 * 1024) {
            val kbsize = size / 1024f
            return formater.format(kbsize.toDouble()) + "KB"
        } else if (size < 1024 * 1024 * 1024) {
            val mbsize = size.toFloat() / 1024f / 1024f
            return formater.format(mbsize.toDouble()) + "MB"
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            val gbsize = size.toFloat() / 1024f / 1024f / 1024f
            return formater.format(gbsize.toDouble()) + "GB"
        } else {
            return "size: error"
        }
    }
fun Long.toCalendar(): Calendar {
    return Calendar.getInstance().apply {
        timeInMillis = this@toCalendar
    }
}

fun Long.getTimeString(format: String="yyyy-MM-dd HH:mm:ss"): String? {
    return SimpleDateFormat(format).format(this)
}
fun Int.toChinese(): String {
    var str = toChineseImpl()
    if (str.startsWith("一十")) {
        str = str.substring(1)
    }
    if (str.endsWith("零")) {
        str = str.substring(1, str.length-1)
    }
    return str
}
private fun Int.toChineseImpl(): String {
    val si = this.toString()
    var sd = ""
    if (si.length == 1)
    // 個
    {
        sd += GetCH(this)
        return sd
    } else if (si.length == 2)
    // 十
    {
        /* if (si.substring(0, 1) == "1")
             sd += "十"
         else*/
        sd += GetCH(this / 10) + "十"
        sd += (this % 10).toChineseImpl()
    } else if (si.length == 3)
    // 百
    {
        sd += GetCH(this / 100) + "百"
        if ((this % 100).toString().length < 2 && this % 100 != 0) {
            sd += "零"
        }
        sd += (this % 100).toChineseImpl()
    } else if (si.length == 4)
    // 千
    {
        sd += GetCH(this / 1000) + "千"
        if ((this % 1000).toString().length < 3&&this % 1000 != 0)
            sd += "零"
        sd += (this % 1000).toChineseImpl()
    } else if (si.length == 5)
    // 萬
    {
        sd += GetCH(this / 10000) + "万"
        if ((this % 10000).toString().length < 4&&this % 10000 != 0)
            sd += "零"
        sd += (this % 10000).toChineseImpl()
    }
    /*while (sd.length - sd.replace("零".toRegex(), "").length > 1) {
        sd = sd.replaceFirst("零".toRegex(), "")
    }*/
    return sd
}

private fun GetCH(input: Int): String {
    var sd = ""
    when (input) {
        1 -> sd = "一"
        2 -> sd = "二"
        3 -> sd = "三"
        4 -> sd = "四"
        5 -> sd = "五"
        6 -> sd = "六"
        7 -> sd = "七"
        8 -> sd = "八"
        9 -> sd = "九"
        else -> {
        }
    }
    return sd
}
