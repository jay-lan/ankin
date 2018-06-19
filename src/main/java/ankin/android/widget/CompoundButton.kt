package ankin.android.widget

import android.view.View
import android.widget.CompoundButton

/**
 * 帝鲮于 2018/2/15创建.
 */
fun CompoundButton.onCheckedChange(onCheckedChanged: (view: View, isChecked: Boolean) -> Unit) {
    setOnCheckedChangeListener(onCheckedChanged)
}