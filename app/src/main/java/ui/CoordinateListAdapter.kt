package ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import entity.ItemData
import org.jetbrains.anko.*

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

    override fun getView(position: Int, v: View?, parent: ViewGroup?): View {
        // listView の1行分のレイアウトを定義
        return with(parent!!.context) {
            verticalLayout {
                padding = dip(10)
                textView {
                    padding = dip(5)
                    text = coordinateList[position].number
                }
                textView {
                    padding = dip(5)
                    text = coordinateList[position].name
                }
            }
        }
    }
//    override fun getView(potision: Int, convertView: View?, parent: ViewGroup?): View =
//            (convertView as? CoordinateListUI) ?: CoordinateListUI(context,coordinateList).apply {
//                createView(context.UI{ })
//            }.apply {
//
//            }
}