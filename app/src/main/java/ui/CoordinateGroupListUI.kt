package ui

import android.content.Context
import android.widget.FrameLayout
import android.widget.TextView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class CoordinateGroupListUI(context: Context, groupNameList: MutableList<String>): FrameLayout(context), AnkoComponent<Context> {
    private var label: TextView? = null
    private var list = groupNameList
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        verticalLayout {
            label = textView {

            }
        }.apply{ this@CoordinateGroupListUI.addView(this) }
    }
    fun update(position: Int) {
        label?.text = list[position]
    }
}