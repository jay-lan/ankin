package ankin.android.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Interpolator
import android.widget.PopupWindow
import ankin.android.content.startActivity
import ankin.android.content.toast
import com.dealin.ankin.*
import org.jetbrains.anko.dip

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        /**
         * 帝鲮于 2018/2/15创建.
         */
fun View.showPopupWindow(contentView: View, isOutsideTouchable: Boolean = true, isFocusable:Boolean = true,backgroundDrawable: Drawable? = null, elevation: Int = dip(3)) {
    val popupWindow = PopupWindow(contentView)
    popupWindow.isOutsideTouchable = isOutsideTouchable
    popupWindow.setBackgroundDrawable(backgroundDrawable)
    popupWindow.elevation = elevation.toFloat()
    popupWindow.isFocusable = isFocusable
    popupWindow.showAsDropDown(this)
}

fun View.onFocusChange(block: View.(hasFocus: Boolean) -> Unit) {
    setOnFocusChangeListener { v, hasFocus ->
        v.block(hasFocus)
    }
}

fun View.view(@LayoutRes layoutId: Int): View? {
    return View.inflate(context, layoutId, null)
}

fun View.visible() {
    visibility = VISIBLE
}

fun View.gone() {
    visibility = GONE
}

fun View.invisible() {
    visibility = INVISIBLE
}

fun View.toast(string: String) {
    context.toast(string)
}

//==========================Animation=======================================
fun View.onKeyUpEvent(keyCode: Int, block: () -> Unit) {
    setOnKeyListener { v, kc, event ->
        if (keyCode == kc && event.action == KeyEvent.ACTION_UP) {
            block()
            true
        } else {
            false
        }
    }
}
fun View.onKeyDownEvent(keyCode: Int, block: () -> Unit) {
    setOnKeyListener { v, kc, event ->
        if (keyCode == kc && event.action == KeyEvent.ACTION_DOWN) {
            block()
            true
        } else {
            false
        }
    }
}
fun View.animate(block: ViewPropertyAnimator.() -> Unit) {
    animate().block()
}

fun View.animateToAlpha(alpha: Float, duration: Long = 300, interpolator: Interpolator? = null, animatorListener: Animator.AnimatorListener? = null, animatorUpdateListener: ValueAnimator.AnimatorUpdateListener? = null) {
    animate().apply {
        alpha(alpha)
        this.duration = duration
        this.interpolator = interpolator
        this.setListener(animatorListener)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.setUpdateListener(animatorUpdateListener)
        }
    }.start()
}

fun View.alphaToInvisible(duration: Long = 300, interpolator: Interpolator? = null, animatorListener: Animator.AnimatorListener? = null, animatorUpdateListener: ValueAnimator.AnimatorUpdateListener? = null, finishState: Int = INVISIBLE) {
    animate().apply {
        alpha(0f)
        this.duration = duration
        this.interpolator = interpolator
        this.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
                animatorListener?.onAnimationRepeat(p0)
            }

            override fun onAnimationEnd(p0: Animator?) {
                animatorListener?.onAnimationEnd(p0)
                visibility = finishState
            }

            override fun onAnimationCancel(p0: Animator?) {
                animatorListener?.onAnimationCancel(p0)
            }

            override fun onAnimationStart(p0: Animator?) {
                animatorListener?.onAnimationStart(p0)
            }

        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.setUpdateListener(animatorUpdateListener)
        }
    }.start()
}

fun View.alphaToVisible(alpha: Float = 1f, duration: Long = 300, interpolator: Interpolator? = null, animatorListener: Animator.AnimatorListener? = null, animatorUpdateListener: ValueAnimator.AnimatorUpdateListener? = null) {
    animate().apply {
        alpha(alpha)
        this.duration = duration
        this.interpolator = interpolator
        this.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
                animatorListener?.onAnimationRepeat(p0)
            }

            override fun onAnimationEnd(p0: Animator?) {
                animatorListener?.onAnimationEnd(p0)
            }

            override fun onAnimationCancel(p0: Animator?) {
                animatorListener?.onAnimationCancel(p0)
            }

            override fun onAnimationStart(p0: Animator?) {
                animatorListener?.onAnimationStart(p0)
                visibility = View.VISIBLE
            }

        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.setUpdateListener(animatorUpdateListener)
        }
    }.start()
}

fun View.scaleXToInvisible(duration: Long = 300, interpolator: Interpolator? = null, animatorListener: Animator.AnimatorListener? = null, animatorUpdateListener: ValueAnimator.AnimatorUpdateListener? = null, finishState: Int = INVISIBLE) {
    animate().apply {
        scaleX(0f)
        this.duration = duration
        this.interpolator = interpolator
        this.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
                animatorListener?.onAnimationRepeat(p0)
            }

            override fun onAnimationEnd(p0: Animator?) {
                animatorListener?.onAnimationEnd(p0)
                visibility = finishState
            }

            override fun onAnimationCancel(p0: Animator?) {
                animatorListener?.onAnimationCancel(p0)
            }

            override fun onAnimationStart(p0: Animator?) {
                animatorListener?.onAnimationStart(p0)
            }

        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.setUpdateListener(animatorUpdateListener)
        }
    }.start()
}

fun View.scaleYToInvisible(duration: Long = 300, interpolator: Interpolator? = null, animatorListener: Animator.AnimatorListener? = null, animatorUpdateListener: ValueAnimator.AnimatorUpdateListener? = null, finishState: Int = INVISIBLE) {
    animate().apply {
        scaleY(0f)
        this.duration = duration
        this.interpolator = interpolator
        this.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
                animatorListener?.onAnimationRepeat(p0)
            }

            override fun onAnimationEnd(p0: Animator?) {
                animatorListener?.onAnimationEnd(p0)
                visibility = finishState
            }

            override fun onAnimationCancel(p0: Animator?) {
                animatorListener?.onAnimationCancel(p0)
            }

            override fun onAnimationStart(p0: Animator?) {
                animatorListener?.onAnimationStart(p0)
            }

        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.setUpdateListener(animatorUpdateListener)
        }
    }.start()
}

fun View.scaleToInvisible(duration: Long = 300, interpolator: Interpolator? = null, animatorListener: Animator.AnimatorListener? = null, animatorUpdateListener: ValueAnimator.AnimatorUpdateListener? = null, finishState: Int = INVISIBLE,scaleX:Float = 0f,scaleY:Float = 0f) {
    animate().apply {
        scaleX(scaleX)
        scaleY(scaleY)
        this.duration = duration
        this.interpolator = interpolator
        this.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
                animatorListener?.onAnimationRepeat(p0)
            }

            override fun onAnimationEnd(p0: Animator?) {
                animatorListener?.onAnimationEnd(p0)
                visibility = finishState
            }

            override fun onAnimationCancel(p0: Animator?) {
                animatorListener?.onAnimationCancel(p0)
            }

            override fun onAnimationStart(p0: Animator?) {
                animatorListener?.onAnimationStart(p0)
            }

        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.setUpdateListener(animatorUpdateListener)
        }
    }.start()
}

fun View.scaleToVisible(duration: Long = 300, interpolator: Interpolator? = null, animatorListener: Animator.AnimatorListener? = null, animatorUpdateListener: ValueAnimator.AnimatorUpdateListener? = null) {
    animate().apply {
        scaleX(1f)
        scaleY(1f)
        this.duration = duration
        this.interpolator = interpolator
        this.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
                animatorListener?.onAnimationRepeat(p0)
            }

            override fun onAnimationEnd(p0: Animator?) {
                animatorListener?.onAnimationEnd(p0)
            }

            override fun onAnimationCancel(p0: Animator?) {
                animatorListener?.onAnimationCancel(p0)
            }

            override fun onAnimationStart(p0: Animator?) {
                animatorListener?.onAnimationStart(p0)
                visibility = com.dealin.ankin.VISIBLE
            }

        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.setUpdateListener(animatorUpdateListener)
        }
    }.start()
}

fun View.color(resId: Int): Int {
    return resources.getColor(resId)
}

fun View.string(resId: Int): String? {
    return resources.getString(resId)
}

fun View.startActivity(mClass: Class<*>) {
    context.startActivity(mClass)
}

fun View.startActivity(mClass: Class<*>, flags: Int, vararg extras: Pair<String, Any>) {
    context.startActivity(mClass, flags, *extras)
}

fun View.startActivity(activity: Class<*>, vararg extras: Pair<String, Any>) {
    context.startActivity(activity, 0, *extras)
}

fun View.startActivity(intent: Intent) {
    context.startActivity(intent)
}

fun View.onTouch(onTouch: (view: View, event: MotionEvent) -> Boolean) {
    setOnTouchListener(onTouch)
}

fun View.clearOnTouch() {
    setOnTouchListener(null)
}

fun View.onClick(onClick: (view: View) -> Unit) {
    setOnClickListener(onClick)
}

fun View.clearOnClick() {
    setOnClickListener(null)
}

fun View.onLongClick(onLongClick: (view: View) -> Boolean) {
    setOnLongClickListener(onLongClick)
}

fun View.clearOnLongClick() {
    setOnLongClickListener(null)
}

fun View.onKey(onKey: (view: View, keyCode: Int, keyEvent: KeyEvent) -> Boolean) {
    setOnKeyListener(onKey)
}

fun View.clearOnKey() {
    setOnKeyListener(null)
}