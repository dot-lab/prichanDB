package ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import entity.ItemData
import org.jetbrains.anko.*

class CoordinateListAdapter(private val context: Context, private val coordinateList: List<ItemData>): BaseAdapter() {
    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View =
            (convertView as? CoordinateListUI) ?: CoordinateListUI(context, coordinateList).apply {
                createView(context.UI { })
            }.apply {
                update(pos)
            }

    override fun getItem(position: Int): Any {
        return coordinateList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return coordinateList.size
    }
}