package ankin.java.io

import ankin.kotlin.dataSizeString
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset
import java.text.DecimalFormat

/**
 * 帝鲮于 2018/2/15创建.
 */
fun File.mkdirsOnNotExist() {
    if (!exists()) {
        mkdirs()
    }
}
fun File.notExist(): Boolean {
    return !exists()
}
fun File.getChildPath(name: String): String? {
    return getChildFile(name).absolutePath
}
fun File.getChildFile(name: String): File {
    return File(this, name)
}

/**
 * 删除所有，包括子文件和自身
 */
fun File.deleteAll() {
    if (isDirectory) {
        listFiles()?.forEach {
            it.deleteAll()
        }
        delete()
    } else {
        delete()
    }
}

/**
 * 仅删除子文件
 */
fun File.deleteChildren() {
    if (isDirectory) {
        listFiles()?.forEach {
            it.deleteAll()
        }
    }
}

@Throws(IOException::class)
fun File.save(content: Any, charset: Charset = Charset.defaultCharset()) {
    save(content.toString().toByteArray(charset))
}

@Throws(IOException::class)
fun File.save(content: ByteArray) {
    parentFile?:return
    if (!parentFile.exists()) {
        parentFile.mkdirs()
    }
    writeBytes(content)
}

fun File.rename(newName: String) {
    val f = File(parentFile, newName)
    renameTo(f)
}

fun File.renameWithoutExtension(newName: String) {
    if (extension.isEmpty()) {
        rename(newName)
    }else {
        val f = File(parentFile, newName + ".${extension}")
        renameTo(f)
    }
}

val File.sizeString: String
    get() {
        return length().dataSizeString
    }

fun File.search(regex: Regex): ArrayList<File> {
    val list = ArrayList<File>()
    if (isDirectory) {
        val children = listFiles()
        if (children != null) {
            children.forEach {
                val matcher = regex.toPattern().matcher(it.nameWithoutExtension)
                if (matcher.find()) {
                    list.add(it)
                }
                if (it.isDirectory) {
                    list.addAll(it.search(regex))
                }
            }
        }
    }
    return list
}

fun File.search(keyword: String): ArrayList<File> {
    val list = ArrayList<File>()
    if (isDirectory) {
        val children = listFiles()
        if (children != null) {
            children.forEach {
                if (it.name.contains(keyword)) {
                    list.add(it)
                }
                if (it.isDirectory) {
                    list.addAll(it.search(keyword))
                }
            }
        }
    }
    return list
}