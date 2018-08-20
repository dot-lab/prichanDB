package ui

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import entity.ItemData
import org.jetbrains.anko.*

class CoordinateListAdapter(private val coordinateList: List<ItemData>, var hasList: Map<String,Int>): BaseAdapter() {
    // hasList => コーディネートリストとは別のDBから読み書きする
    // number と has のペア（PCH1-01,1）ならPCH1-01を所持している,という感じ

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
                        // このままだとぜんぶチェックはずれる
                        when (coordinateList[position].has.toInt()) {
                            1 -> {
                                isChecked = true
                            }
                            0 -> {
                                isChecked = false
                            }
                        }
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