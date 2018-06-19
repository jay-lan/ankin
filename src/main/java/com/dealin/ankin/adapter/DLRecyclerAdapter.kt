package com.dealin.ankin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout

/**
 * 帝鲮于 2018/4/11创建.
 */
abstract class DLRecyclerAdapter<T>(var context: Context) : RecyclerView.Adapter<DLRecyclerAdapter.DLViewHolder>() {
    private var list: MutableList<T> = ArrayList<T>()
    private var onItemClick = { recyclerView: RecyclerView, item: T, position: Int -> }
    private var onItemLongClick = { recyclerView: RecyclerView, item: T, position: Int -> false }
    private var onItemCountChanged = { newCount: Int, oldCount: Int -> }
    lateinit var recyclerView: RecyclerView

    abstract fun View.onItemInflate(position: Int, item: T, viewHolder: DLViewHolder)
    abstract fun getView(viewType: Int): Any

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DLViewHolder {
        val view = getView(viewType)
        if (view is Int) {
            return DLViewHolder(LinearLayout(context).apply {
                val aimView = LayoutInflater.from(context).inflate(view, this, false)
                layoutParams = ViewGroup.LayoutParams(aimView.layoutParams)
                addView(aimView)
                gravity = Gravity.CENTER
            })
        } else {
            return DLViewHolder(LinearLayout(context).apply {
                layoutParams = ViewGroup.LayoutParams((view as View).layoutParams)
                addView(view as View)
                gravity = Gravity.CENTER
            })
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    operator fun get(position: Int): T {
        return list[position]
    }

    operator fun set(position: Int, item: T) {
        list.set(position, item)
        notifyItemChanged(position)
    }

    fun getItem(position: Int): T {
        return list[position]
    }

    open fun addItem(item: T, position: Int = list.size) {
        list.add(position, item)
        notifyItemInserted(position)
        onItemCountChanged(itemCount - 1, itemCount)
    }

    open fun addItems(items: Collection<T>, position: Int = list.size) {
        list.addAll(position, items)
        notifyItemRangeChanged(position, items.size)
        onItemCountChanged(itemCount - items.size, itemCount)
    }

    open fun getItems(): MutableList<T> {
        return list
    }
    fun setItems(items: Collection<T>) {
        val oldCount= itemCount
        val oldList = ArrayList<T>().apply { addAll(list) }
        list.clear()
        list.addAll(items)
       /* list.forEachIndexed { index, t ->
            if (index < oldList.size) {
                if (t != oldList[index]) {
                    notifyItemChanged(index)
                }
            } else {
                notifyItemChanged(index)
            }
        }*/
        notifyDataSetChanged()
        onItemCountChanged(oldCount, itemCount)
    }

    open fun clear() {
        val oldCount = itemCount
        list.clear()
        notifyDataSetChanged()
        onItemCountChanged(oldCount, 0)
    }

    open fun removeItemAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        onItemCountChanged(itemCount + 1, itemCount)
    }

    open fun removeItem(item: T) {
        list.remove(item)
        notifyDataSetChanged()
        onItemCountChanged(itemCount + 1, itemCount)
    }

    open fun removeItems(items: Collection<T>) {
        list.removeAll(items)
        notifyDataSetChanged()
        onItemCountChanged(itemCount + items.size, itemCount)
    }

    open fun removeItemIf(condition: (item: T) -> Boolean): Boolean {
        var removed = false
        val iterator = list.iterator()
        var removedCount = 0
        while (iterator.hasNext()) {
            if (condition(iterator.next())) {
                iterator.remove()
                removed = true
                removedCount++
            }
        }
        notifyDataSetChanged()
        onItemCountChanged(itemCount + removedCount, itemCount)
        return removed
    }

    open fun onItemClick(block: (recyclerView: RecyclerView, item: T, position: Int) -> Unit) {
        this.onItemClick = block
    }

    open fun onItemLongClick(block: (recyclerView: RecyclerView, item: T, position: Int) -> Boolean) {
        this.onItemLongClick = block
    }

    open fun onItemCountChanged(block: (oldCount: Int, newCount: Int) -> Unit) {
        this.onItemCountChanged = block
    }

    val lastItem: T
        get() {
            return this[list.lastIndex]
        }
    val lastIndex: Int
        get() {
            return list.lastIndex
        }

    override fun onBindViewHolder(holder: DLViewHolder, position: Int) {
        holder.itemView.onItemInflate(position, list[position], holder)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(recyclerView, get(position), position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClick(recyclerView, get(position), position)
        }
    }

    class DLViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView)
}

