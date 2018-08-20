package ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import entity.ItemData
import kotlinx.android.synthetic.main.coordinate.view.*
import org.jetbrains.anko.*
import xyz.dot_lab.prichandb.R

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
                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    checkBox {

                    }.lparams(wrapContent, wrapContent)
                    textView {
                        // number
                        text = coordinateList[position].number
                        padding = dip(5)
                    }.lparams(wrapContent, wrapContent)
                    textView {
                        // reality
                        when (coordinateList[position].reality.toInt()) {
                            1 -> {
                                text = "N"
                            }
                            2 -> {
                                text = "R"
                            }
                            3 -> {
                                text = "SR"
                            }
                            4 -> {
                                text = "PR"
                            }
                            5 -> {
                                text = "KR"
                            }
                        }
                        padding = dip(5)
                    }.lparams(wrapContent, wrapContent)
                }.lparams(wrapContent, wrapContent)
                textView {
                    // name
                    text = coordinateList[position].name
                    padding = dip(5)
                }.lparams(wrapContent, wrapContent)
            }
        }
    }
}