package ankin.android.content

import android.app.Activity
import android.app.ProgressDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcelable
import android.support.annotation.AttrRes
import android.support.annotation.RequiresApi
import android.support.annotation.StyleRes
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast

/**
 * 帝鲮于 2018/2/15创建.
 */
fun Context.startActivity(activity: Class<*>, flags:Int = 0, vararg extras:Pair<String,Any>) {
    val intent = Intent(this, activity)
    intent.setFlags(flags)
    extras.forEach {
        when (it.second) {
            is Int -> {
                intent.putExtra(it.first, it.second as Int)
            }
            is String -> {
                intent.putExtra(it.first, it.second as String)
            }
            is Long -> {
                intent.putExtra(it.first, it.second as Long)
            }
            is Short -> {
                intent.putExtra(it.first, it.second as Short)
            }
            is Char -> {
                intent.putExtra(it.first, it.second as Char)
            }
            is Byte -> {
                intent.putExtra(it.first, it.second as Byte)
            }
            is Float -> {
                intent.putExtra(it.first, it.second as Float)
            }
            is Double -> {
                intent.putExtra(it.first, it.second as Double)
            }
            is CharSequence -> {
                intent.putExtra(it.first, it.second as CharSequence)
            }
            is Parcelable ->{
                intent.putExtra(it.first,it.second as Parcelable)
            }
        }
    }
    startActivity(intent)
}

fun Context.startActivity(activity: Class<*>, vararg extras: Pair<String, Any>) {
    startActivity(activity, 0, *extras)
}
fun Context.color(resId: Int): Int {
    return resources.getColor(resId)
}

@RequiresApi(Build.VERSION_CODES.M)
fun Context.color(resId: Int, theme: Resources.Theme): Int {
    return resources.getColor(resId,theme)
}

fun Context.string(resId: Int): String? {
    return resources.getString(resId)
}

fun Context.dimen(resId: Int): Float {
    return resources.getDimension(resId)
}

fun Context.boolean(resId: Int): Boolean {
    return resources.getBoolean(resId)
}

fun Context.anim(resId: Int): XmlResourceParser? {
    return resources.getAnimation(resId)
}

fun Context.intArray(resId: Int): IntArray? {
    return resources.getIntArray(resId)
}

fun Context.stringArray(resId: Int): Array<out String>? {
    return resources.getStringArray(resId)
}

fun Context.textArray(resId: Int): Array<out CharSequence>? {
    return resources.getTextArray(resId)
}

fun Context.view(resId: Int): View? {
    return View.inflate(this, resId, null)
}

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_LONG) {
    if (toastLayoutId == null) {
        Toast.makeText(this, msg, duration).show()
    } else {
        toast(msg, toastLayoutId ?: -1, toastMsgId ?: -1)
    }
}

fun Context.dimenAttr(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.getDimension(resources.displayMetrics).toInt()
}
fun Context.colorAttr(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr,typedValue,true)
    return typedValue.data
}

fun Context.drawableAttr(@AttrRes attr: Int):Drawable {
    val typedValue = TypedValue()
    getTheme()
            .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
    val attribute = intArrayOf(android.R.attr.selectableItemBackground)
    val typedArray = getTheme().obtainStyledAttributes(typedValue.resourceId, attribute)
    return typedArray.getDrawable(0)
}

fun Context.theme(@StyleRes attr: Int): Resources.Theme? {
    return resources.newTheme().apply{applyStyle(attr,true)}
}

private var toastLayoutId: Int? = null
private var toastMsgId: Int? = null
private var toastLayoutInit: ((layout: View) -> Unit)? = null

fun Context.toast(msg: String, layoutViewId: Int? = toastLayoutId, msgTextViewId: Int = toastMsgId ?: -1, layoutInit: ((layout: View) -> Unit)? = toastLayoutInit, duration: Int = Toast.LENGTH_LONG) {
    val layoutView = View.inflate(this, toastLayoutId ?: -1, null)
    val textView = layoutView?.findViewById<TextView>(msgTextViewId)
    textView?.text = msg
    if (layoutInit != null) {
        layoutInit(layoutView)
    }
    val toast = Toast(this)
    toast.setGravity(Gravity.CENTER,0,500)
    toast.view = layoutView
    toast.duration = duration
    toast.show()
}

fun Context.setToastLayout(layoutId: Int, msgTextViewId: Int, layoutInit: ((layout: View) -> Unit)? = null) {
    toastLayoutId = layoutId
    toastMsgId = msgTextViewId
    toastLayoutInit = layoutInit
}

fun Context.startActivity(mClass:Class<*>) {
    startActivity(Intent(this,mClass))
}



private var show: ProgressDialog? = null

fun Activity.showProgressDialog(title: String? = null, message: String? = null, block: ProgressDialog.()->Unit) {
    dismissProgress()
    show = ProgressDialog.show(this, title, message, true, false)
    show?.block()
}

fun Service.showProgressDialog(context: Context, title: String, message: String) {
    dismissProgress()
    show = ProgressDialog(context)
    show!!.setTitle(title)
    show!!.setMessage(message)
    show!!.isIndeterminate = true
    show!!.setCancelable(false)
    show!!.window!!.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
    show!!.show()
}

fun Context.updateProgressMessage(message: String) {
    if (show != null)
        show!!.setMessage(message)
}

fun Context.dismissProgress() {
    if (show != null)
        show!!.dismiss()
}
fun Context.getWindowHeight(): Int {
    return resources.displayMetrics.heightPixels
}

fun Context.getWindowWidth(): Int {
    return resources.displayMetrics.widthPixels
}

fun Context.sp(textSize: Int): Float {
    val fontScale = resources.displayMetrics.scaledDensity;
    return (textSize * fontScale + 0.5f);
}
fun Context.sp(textSize: Float): Float {
    val fontScale = resources.displayMetrics.scaledDensity;
    return (textSize * fontScale + 0.5f);
}