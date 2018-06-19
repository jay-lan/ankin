package ankin.kotlin

import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.regex.Pattern
import java.util.zip.*
import android.provider.SyncStateContract.Helpers.update
import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.io.BaseInputStream
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.util.Zip4jConstants
import net.lingala.zip4j.util.Zip4jUtil
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * 帝鲮于 2018/2/15创建.
 */
/**
 * A hashing method that changes a string (like a URL) into a hash suitable for using as a
 * disk filename.
 */
val String.hashKey:String
    get() {
        var cacheKey: String
        try {
            val mDigest = MessageDigest.getInstance("MD5")
            mDigest.update(this.toByteArray())
            cacheKey = bytesToHexString(mDigest.digest())
        } catch (e: NoSuchAlgorithmException) {
            cacheKey = this.hashCode().toString()
        }

        return cacheKey
    }

private fun bytesToHexString(bytes: ByteArray): String {
    // http://stackoverflow.com/questions/332079
    val sb = StringBuilder()
    for (i in bytes.indices) {
        val hex = Integer.toHexString(0xFF and bytes[i].toInt())
        if (hex.length == 1) {
            sb.append('0')
        }
        sb.append(hex)
    }
    return sb.toString()
}

fun String.getValue(pattern: String, valuePlaceholder: String = "*"): String {
    if (valuePlaceholder !in pattern) {
        return ""
    }
    val prefix = pattern.split(valuePlaceholder)[0]
    val after = pattern.split(valuePlaceholder)[1]
    var start = if(prefix!="")indexOf(prefix) else 0
    if (start == -1) {
        return ""
    }
    start += prefix.length
    val end = if(after!="")indexOf(after, start) else length
    if (end == -1) {
        return ""
    }
    return substring(start, end)
}
fun String.toFile(): File {
    return File(this)
}
fun String.countWithoutSpace(): Int {
    return replace(Regex("[\\s\n\t\r\b　]"), "").length
}
fun String.chineseNumber2Int(): Int {
    if (isEmpty()) {
        return NUMBER_NOT_FOUND
    }
    var flag = false//flag为true，则找到了数字
    var result = 0
    var temp = 1//存放一个单位的数字如：十万
    var count = 0//判断是否有chArr
    val cnArr = charArrayOf('一', '二', '三', '四', '五', '六', '七', '八', '九')
    val chArr = charArrayOf('十', '百', '千', '万', '亿')
    for (i in 0 until this.length) {
        var b = true//判断是否是chArr
        val c = this[i]
        for (j in cnArr.indices) {//非单位，即数字
            if (c == cnArr[j]) {
                flag = true
                if (0 != count) {//添加下一个单位之前，先把上一个单位值添加到结果中
                    result += temp
                    temp = 1
                    count = 0
                }
                // 下标+1，就是对应的值
                temp = j + 1
                b = false
                break
            }
        }
        if (b) {//单位{'十','百','千','万','亿'}
            for (j in chArr.indices) {
                if (c == chArr[j]) {
                    flag = true
                    when (j) {
                        0 -> temp *= 10
                        1 -> temp *= 100
                        2 -> temp *= 1000
                        3 -> temp *= 10000
                        4 -> temp *= 100000000
                        else -> {
                        }
                    }
                    count++
                }
            }
        }
        if (i == this.length - 1) {//遍历到最后一个字符
            result += temp
        }
    }
    return if (!flag) {
        NUMBER_NOT_FOUND
    } else result
}

fun String.checkOutInt(defaultNumber: Int = 0): Int {
    val pattern = Pattern.compile("[0-9]+")
    val matcher = pattern.matcher(this)
    return if (matcher.find()) {
        Integer.parseInt(this.substring(matcher.start(), matcher.end()))
    } else {
        val int = chineseNumber2Int()
        if (int == NUMBER_NOT_FOUND) {
            return defaultNumber
        } else {
            return int
        }
    }
}
fun String.position(p: Int): Int {
    if (p > length) {
        return length
    } else {
        return p
    }
}
/**
 *
 * 使用gzip进行压缩
 */
fun String.gzip(): String {
    if (length == 0) {
        return ""
    }
    val out = ByteArrayOutputStream()

    var gzip: GZIPOutputStream? = null
    try {
        gzip = GZIPOutputStream(out)
        gzip!!.write(this.toByteArray())
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (gzip != null) {
            try {
                gzip!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
    return Base64.encodeToString(out.toByteArray(), Base64.DEFAULT)
}

/**
 *
 *
 * Description:使用gzip进行解压缩
 * @param this
 * @return
 */
fun String.unGzip(): String{
    val out = ByteArrayOutputStream()
    var `in`: ByteArrayInputStream? = null
    var ginzip: GZIPInputStream? = null
    var compressed: ByteArray? = null
    var decompressed: String? = null
    try {
        compressed = Base64.decode(this, Base64.DEFAULT)
        `in` = ByteArrayInputStream(compressed)
        ginzip = GZIPInputStream(`in`)

        val buffer = ByteArray(1024)
        var offset = ginzip.read(buffer)
        while (offset!= -1) {
            out.write(buffer, 0, offset)
            offset = ginzip.read(buffer)
        }
        decompressed = out.toString()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (ginzip != null) {
            try {
                ginzip!!.close()
            } catch (e: IOException) {
            }

        }
        if (`in` != null) {
            try {
                `in`!!.close()
            } catch (e: IOException) {
            }

        }
        if (out != null) {
            try {
                out.close()
            } catch (e: IOException) {
            }

        }
    }

    return decompressed?:""
}

fun String.zip(password: String): String {
    val out = ByteArrayOutputStream()
    val zos = net.lingala.zip4j.io.ZipOutputStream(out)
    val zipParam = ZipParameters().apply {
        this.encryptionMethod = Zip4jConstants.ENC_METHOD_AES
        this.isEncryptFiles = true
        this.setPassword(password)
    }
    zos.write(this.toByteArray(Charsets.UTF_8))
    return out.toByteArray().toBase64String()?:""
}

/**
 * 使用zip进行压缩
 * @param str 压缩前的文本
 * @return 返回压缩后的文本
 */
fun String.zip(): String {
    var compressed: ByteArray?
    var out: ByteArrayOutputStream? = null
    var zout: ZipOutputStream? = null
    var compressedStr: String? = null
    try {
        out = ByteArrayOutputStream()
        zout = ZipOutputStream(out)
        zout.putNextEntry(ZipEntry("0"))
        zout.write(toByteArray())
        zout.closeEntry()
        compressed = out!!.toByteArray()
        compressedStr = Base64.encodeToString(compressed, Base64.DEFAULT)
    } catch (e: IOException) {
        compressed = null
    } finally {
        if (zout != null) {
            try {
                zout!!.close()
            } catch (e: IOException) {
            }

        }
        if (out != null) {
            try {
                out!!.close()
            } catch (e: IOException) {
            }

        }
    }
    return compressedStr?:""
}

/**
 * 使用zip进行解压缩
 * @param compressed 压缩后的文本
 * @return 解压后的字符串
 */
fun String.unzip(): String {


    var out: ByteArrayOutputStream? = null
    var `in`: ByteArrayInputStream? = null
    var zin: ZipInputStream? = null
    var decompressed: String? = null
    try {
        val compressed = Base64.decode(this, Base64.DEFAULT)
        out = ByteArrayOutputStream()
        `in` = ByteArrayInputStream(compressed)
        zin = ZipInputStream(`in`)
        zin!!.getNextEntry()
        val buffer = ByteArray(1024)
        var offset = zin!!.read(buffer)
        while (offset != -1) {
            out!!.write(buffer, 0, offset)
            offset = zin.read(buffer)
        }
        decompressed = out!!.toString()
    } catch (e: IOException) {
        decompressed = null
    } finally {
        if (zin != null) {
            try {
                zin!!.close()
            } catch (e: IOException) {
            }

        }
        if (`in` != null) {
            try {
                `in`!!.close()
            } catch (e: IOException) {
            }

        }
        if (out != null) {
            try {
                out!!.close()
            } catch (e: IOException) {
            }

        }
    }
    return decompressed?:""
}
fun String.decodeBase64(): ByteArray? {
    return Base64.decode(this, Base64.DEFAULT)
}
