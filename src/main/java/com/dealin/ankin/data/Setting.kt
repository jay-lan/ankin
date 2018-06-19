package com.dealin.ankin.data

import ankin.com.google.gson.singleInstanceFromJson
import ankin.java.io.save
import com.google.gson.Gson
import java.io.File

/**
 * 帝鲮于 2018/6/15创建.
 */
open class Setting(var path: String = "") {
    init {
        val a = 1
        try {
            Gson().singleInstanceFromJson(File(Test.path).readText(), this)
        } catch (e: Exception) {

        }
    }
   companion object {
       val gson = Gson()
   }
}