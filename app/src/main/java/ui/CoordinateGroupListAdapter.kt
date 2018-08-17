package ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import org.jetbrains.anko.UI

class CoordinateGroupListAdapter(var context: Context, groupList: MutableList<String>): BaseAdapter() {
    private var list = groupList
    override fun getItem(positon: Int): Any {
        return list[positon]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
            (convertView as? CoordinateGroupListUI) ?: CoordinateGroupListUI(context,list).apply {
                createView(context.UI {  })
            }.apply {
                update(position)
            }
}