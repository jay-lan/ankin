package ankin.android.view

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import ankin.android.content.view
import com.dealin.ankin.GONE
import com.dealin.ankin.VISIBLE

/**
 * 帝鲮于 2018/2/15创建.
 */
//================The all Operation of viewGroup===============
fun ViewGroup.allTextView(block: TextView.()->Unit={}): List<TextView> {
    return allView(TextView::class.java,block)
}

fun ViewGroup.allImageView(block: ImageView.()->Unit={}): List<ImageView> {
    return allView(ImageView::class.java,block)

}
fun ViewGroup.allButton(block: Button.()->Unit={}): List<Button> {
    return allView(Button::class.java, block)
}

fun ViewGroup.allEditText(block: EditText.()->Unit = {}): List<EditText> {
    return allView(EditText::class.java, block)
}

fun <T> ViewGroup.allView(clazz: Class<T>, block: T.() -> Unit= {}):List<T> {
    val list = ArrayList<T>()
    val clazzString = clazz.name
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        var bool = true
        var childClazz:Class<*> = child.javaClass
        while (childClazz.name != clazzString) {
            if (childClazz.name == Any::class.java.name) {
                bool = false
                break
            } else {
                childClazz = childClazz.superclass
            }
        }
        if (bool) {
            list.add(child as T)
            child.block()
        } else if (child is ViewGroup) {
            list.addAll(child.allView(clazz, block))
        }
    }
    return list
}
infix operator fun ViewGroup.plus(view: View) {
    addView(view)
}
fun ViewGroup.addLayout(layoutId: Int) {
    ViewGroup.inflate(context,layoutId,this)
}
fun ViewGroup.setView(view: View) {
    removeAllViews()
    addView(view)
}

fun ViewGroup.setView(layoutId: Int) {
    removeAllViews()
    addView(view(layoutId))
}
fun ViewGroup.setContentView(layoutId: Int) {
    removeAllViews()
    addView(context.view(layoutId))
}

fun ViewGroup.setContentView(view: View?) {
    removeAllViews()
    addView(view)
}
fun  ViewGroup.setContentView(view: View?, params: ViewGroup.LayoutParams?) {
    removeAllViews()
    addView(view,params)
}
fun ViewGroup.showView(view: View) {
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        if (child == view) {
            child.visibility = VISIBLE
        } else {
            child.visibility = GONE
        }
    }
}