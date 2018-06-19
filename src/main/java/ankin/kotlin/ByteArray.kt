package ankin.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

/**
 * 帝鲮于 2018/2/15创建.
 */
fun ByteArray.toBase64String(): String? {
    return Base64.encodeToString(this, Base64.DEFAULT)
}

fun ByteArray.decodeBitmap(): Bitmap? {
    return BitmapFactory.decodeByteArray(this,0,size)
}