package com.dealin.ankin.data

import ankin.com.google.gson.fromJson
import ankin.com.google.gson.singleInstanceFromJson
import ankin.com.google.gson.singleInstanceToJson
import com.dealin.ankin.toJson
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.io.File

/**
 * 帝鲮于 2018/6/15创建.
 */
object Test:Setting("E:\\开发工具\\jd-gui\\test.json") {
    var a = 0
    var b = 1
    init {
        val a = 0
    }
    var c = Bean()
    fun edit(block: Test.() -> Unit) {
        this.block()
        save()
    }
    fun save() {
        File(path).writeText(Gson().singleInstanceToJson(this))
    }
}