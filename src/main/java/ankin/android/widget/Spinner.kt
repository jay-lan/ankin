package ankin.android.widget

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

/**
 * 帝鲮于 2018/2/17创建.
 */
fun Spinner.onItemSelectedListener(onItemSelected: (adapterView: AdapterView<*>?, selectedView: View?, position: Int, id: Long) -> Unit = {adapterView,selectedView,position,id ->},
                                   onNothingSelected:(adapterView: AdapterView<*>?)->Unit = {adapterView ->}){
    onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(p0: AdapterView<*>?) {
            onNothingSelected(p0)
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            onItemSelected(p0, p1, p2, p3)
        }

    }
}