package com.dealin.ankin

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by 帝鲮 on 2018/1/23.
 */
class TestClass(var str:String = "${System.currentTimeMillis()}") {
    fun print() {
        println(str)
    }
}
class ObjectWrapper<T>(var type: Int = ALL){
    var objLists = ArrayList<T>()
    override fun equals(other: Any?): Boolean {
        var result = type == ALL
        for (i in objLists.indices) {
            if (objLists[i] != other && type == ALL) {
                return false
            } else if (objLists[i] == other && type == ONE) {
                return true
            }
        }
        return result
    }
    companion object {
        val ALL = 0
        val ONE = 1
    }
}
inline fun <T, R> all(vararg aim: T, block: T.() -> R): ArrayList<R> {
    val result = ArrayList<R>()
    aim.forEach {
        result.add(it.block())
    }
    return result
}

inline fun <T> allInList(list: List<T>, block: T.() -> Unit) {
    list.forEach {
        it.block()
    }
}


infix fun <T> Collection<T>.equal(aim: T): Boolean {
    forEach {
        if (aim != it) {
            return false
        }
    }
    return true
}

infix fun <T> Collection<T>.notEqual(aim: T): Boolean {
    forEach {
        if (aim == it) {
            return false
        }
    }
    return true
}
