package ankin.android.support.design.widget

import android.support.design.widget.TabLayout

/**
 * 帝鲮于 2018/2/14创建.
 */
fun TabLayout.onTabReselected(onTabReselected: (tab: TabLayout.Tab) -> Unit): TabLayout.OnTabSelectedListener {
    val listener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            if (tab != null) {
                onTabReselected(tab)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabSelected(tab: TabLayout.Tab?) {

        }
    }
    addOnTabSelectedListener(listener)
    return listener
}
fun TabLayout.onTabUnselected(onTabUnselected: (tab: TabLayout.Tab) -> Unit): TabLayout.OnTabSelectedListener {
    val listener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            if (tab != null) {
                onTabUnselected(tab)
            }
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {

        }
    }
    addOnTabSelectedListener(listener)
    return listener
}
fun TabLayout.onTabSelected(onTabSelected: (tab: TabLayout.Tab) -> Unit): TabLayout.OnTabSelectedListener {
    val listener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab != null) {
                onTabSelected(tab)
            }
        }
    }
    addOnTabSelectedListener(listener)
    return listener
}
