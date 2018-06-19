package com.dealin.ankin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader

/**
 * Created by 帝鲮 on 2018/1/23.
 */
fun Any.toJson(): String {
    try {
        return Gson().toJson(this)
    } catch (e: Exception) {
        return ""
    }
}

fun <T> Class<T>.fromJson(json: String): T? {
    try {
        return Gson().fromJson(json, (object : TypeToken<T>() {}).type)
    } catch (e: Exception) {
        return null
    }
}

fun <T> Class<T>.fromJson(file: File): T? {
    try {
        return Gson().fromJson(FileReader(file), object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        return null
    }
}