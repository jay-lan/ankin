package ankin.android.widget

import android.graphics.drawable.Drawable
import android.widget.EditText
import java.lang.reflect.InvocationTargetException

/**
 * 帝鲮于 2018/2/15创建.
 */
fun EditText.getCursorCoordinate(): FloatArray {
    /*
       *以下通过反射获取光标cursor的坐标。
       * 首先观察到TextView的invalidateCursorPath()方法，它是光标闪动时重绘的方法。
       * 方法的最后有个invalidate(bounds.left + horizontalPadding, bounds.top + verticalPadding,
                   bounds.right + horizontalPadding, bounds.bottom + verticalPadding);
       *即光标重绘的区域，由此可得到光标的坐标
       * 具体的坐标在TextView.mEditor.mCursorDrawable里，获得Drawable之后用getBounds()得到Rect。
       * 之后还要获得偏移量修正，通过以下三个方法获得：
       * getVerticalOffset(),getCompoundPaddingLeft(),getExtendedPaddingTop()。
       *
      */

    var xOffset = 0
    var yOffset = 0
    var clazz: Class<*> = EditText::class.java
    clazz = clazz.superclass
    try {
        val editor = clazz.getDeclaredField("mEditor")
        editor.isAccessible = true
        val mEditor = editor.get(this)
        val editorClazz = Class.forName("android.widget.Editor")
        val drawables = editorClazz.getDeclaredField("mCursorDrawable")
        drawables.isAccessible = true
        val drawable = drawables.get(mEditor) as Array<Drawable>

        val getVerticalOffset = clazz.getDeclaredMethod("getVerticalOffset", Boolean::class.javaPrimitiveType)
        val getCompoundPaddingLeft = clazz.getDeclaredMethod("getCompoundPaddingLeft")
        val getExtendedPaddingTop = clazz.getDeclaredMethod("getExtendedPaddingTop")
        getVerticalOffset.isAccessible = true
        getCompoundPaddingLeft.isAccessible = true
        getExtendedPaddingTop.isAccessible = true
        if (drawable != null) {
            if (drawable[0] != null) {
                val bounds = drawable[0].bounds
                //Log.d(TAG, bounds.toString())
                xOffset = getCompoundPaddingLeft.invoke(this) as Int + bounds.left
                yOffset = getExtendedPaddingTop.invoke(this) as Int + getVerticalOffset.invoke(this, false) as Int + bounds.bottom
            }
        }
    } catch (e: NoSuchMethodException) {
        e.printStackTrace()
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    }

    val x = this.getX() + xOffset
    val y = this.getY() + yOffset

    return floatArrayOf(x, y)
}
