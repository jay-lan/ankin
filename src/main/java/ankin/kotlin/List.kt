package ankin.kotlin

import java.util.*

/**
 * 帝鲮于 2018/2/22创建.
 */
fun List<*>.swap(from: Int, to: Int) {
    Collections.swap(this, from, to)
}

val <T> List<T>.randomItem: T
    get() {
        return get(Math.floor(Math.random() * size).toInt())
    }