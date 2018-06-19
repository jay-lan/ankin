package com.dealin.ankin.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import android.text.TextUtils
import android.app.Activity
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.WindowManager






/**
 * 帝鲮于 2018/3/6创建.
 */
object MIUI {
    fun setStatusBarDarkMode(darkmode: Boolean, activity: Activity) {
        val buildTime = (getSystemProperty("ro.miui.version.code_time") ?: "").toLong()
        if (buildTime > 1499875200) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.getDecorView().setSystemUiVisibility(if(darkmode) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0)
        } else {
            val clazz = activity.window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                extraFlagField.invoke(activity.window, if (darkmode) darkModeFlag else 0, darkModeFlag)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
fun getSystemProperty(propName: String): String? {
    val line: String
    var input: BufferedReader? = null
    try {
        val p = Runtime.getRuntime().exec("getprop " + propName)
        input = BufferedReader(InputStreamReader(p.inputStream), 1024)
        line = input.readLine()
        input.close()
    } catch (ex: IOException) {
        return null
    } finally {
        if (input != null) {
            try {
                input.close()
            } catch (e: IOException) {
            }
        }
    }
    return line
}

fun isMIUIRom(): Boolean {
    val property = getSystemProperty("ro.miui.ui.version.name")
    return !TextUtils.isEmpty(property)
}