package ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.preference.PreferenceManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import entity.ItemData
import org.jetbrains.anko.*
import xyz.dot_lab.prichandb.R
import java.io.IOException
import java.io.InputStream

class CoordinateListAdapter(private val context: Context, private val coordinateList: List<ItemData>): BaseAdapter() {
    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View =
        (convertView as? CoordinateListUI ) ?: CoordinateListUI(context,coordinateList).apply {
            createView(context.UI{ })
        }.apply {
            update(pos)
        }

    override fun getItem(position: Int): Any {
        return coordinateList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return coordinateList.size
    }
}