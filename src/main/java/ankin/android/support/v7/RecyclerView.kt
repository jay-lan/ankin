package ankin.android.support.v7

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * 帝鲮于 2018/2/15创建.
 */
fun RecyclerView.refreshVisibleItems() {
    adapter ?: return
    layoutManager ?: return
    val adapter = adapter
    val layoutManager = layoutManager
    if (layoutManager is LinearLayoutManager) {
        adapter.notifyItemRangeChanged(layoutManager.findFirstVisibleItemPosition(), adapter.itemCount - layoutManager.findFirstVisibleItemPosition())
    }
}