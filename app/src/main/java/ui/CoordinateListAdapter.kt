package ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import entity.ItemData
import org.jetbrains.anko.UI

class CoordinateListAdapter(var context: Context, var coordinateList: List<ItemData>): BaseAdapter() {

    override fun getItem(position: Int): Any {
        return coordinateList[position]
    }

    override fun getItemId(position: Int): Long {
        return coordinateList[position]._id
    }

    override fun getCount(): Int {
        return coordinateList.size
    }
    override fun getView(potision: Int, convertView: View?, parent: ViewGroup?): View =
            (convertView as? CoordinateListUI) ?: CoordinateListUI(context,coordinateList).apply {
                createView(context.UI{ })
            }.apply {
                for (i in coordinateList){
                    update(i._id.toInt())
                }
            }
}