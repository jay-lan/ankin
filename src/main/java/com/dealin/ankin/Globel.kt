package com.dealin.ankin

import android.content.Context
import android.os.Handler
import android.view.View
import java.util.*

/**
 * Created by 帝鲮 on 2018/1/23.
 */
val VISIBLE = View.VISIBLE
val INVISIBLE = View.INVISIBLE
val GONE = View.GONE

fun dip2px(context: Context, dipValue: Float): Int {
    val scale = context.resources.displayMetrics.densityDpi.toFloat()
    return (dipValue * (scale / 160) + 0.5f).toInt()
}

fun px2dp(context: Context, pxValue: Float): Int {
    val scale = context.resources.displayMetrics.densityDpi.toFloat()
    return (pxValue * 160 / scale + 0.5f).toInt()
}
fun delayDo(time: Long = 100, block: () -> Unit) {
    Handler().postDelayed(Runnable {
        block()
    },time)
}

infix fun Long.asDelayTimeDo(block: () -> Unit) {
    Handler().postDelayed(Runnable {
        block()
    },this)
}

infix fun Int.asDelayTimeDo(block: () -> Unit) {
    Handler().postDelayed(Runnable {
        block()
    },this.toLong())
}