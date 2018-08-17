package ui

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import entity.ItemData
import org.jetbrains.anko.*
import java.text.FieldPosition

class CoordinateListUI(context: Context,coordinateList: List<ItemData>): FrameLayout(context),AnkoComponent<Context> {
    private var list = coordinateList
    private var itemName: TextView? = null
    override fun createView(ui: AnkoContext<Context>)= with(ui) {
        verticalLayout {
            itemName = textView {
                textSize = 14f
            }.lparams(wrapContent)
        }.apply { this@CoordinateListUI.addView(this) }
    }
    fun update(position: Int) {
        itemName?.text = list[position].number
        Log.d("update",list[position].number)
    }
}