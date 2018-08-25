package ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import entity.ItemData
import kotlinx.android.synthetic.main.coordinate.view.*
import org.jetbrains.anko.*
import org.w3c.dom.Text
import xyz.dot_lab.prichandb.CoordinateListActivity
import xyz.dot_lab.prichandb.R
import java.io.IOException
import java.io.InputStream

class CoordinateListUI(context: Context, itemList: List<ItemData>, hasItems: Set<String>): FrameLayout(context) {
    private val list = itemList
    private val itemSet = hasItems
    private var containerLayout1: LinearLayout? = null
    private var containerLayout2: LinearLayout? = null
    private var containerLayout3: LinearLayout? = null
    private var hasCheckBox: CheckBox? = null
    private var numberTextView: TextView? = null
    private var realityTextView: TextView? = null
    private var blandImageView: ImageView? = null
    private var typeTextView: TextView? = null
    private var nameTextView: TextView? = null
    private var categoryTextView: TextView? = null
    private var iineTextView: TextView? = null
    private var colorTextView: TextView? = null
    companion object {
        var changedFlag = false
    }
    init {
        context.UI {
            verticalLayout {
                orientation = LinearLayout.VERTICAL
                padding = dip(10)
                lparams(matchParent, wrapContent)
                containerLayout1 = linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    lparams(wrapContent, matchParent)
                    hasCheckBox = checkBox {
                        gravity = Gravity.CENTER
                        tag = false
                    }.lparams(wrapContent, matchParent)
                    numberTextView = textView {
                        padding = dip(5)
                        gravity = Gravity.CENTER
                    }.lparams(wrapContent, matchParent)
                    realityTextView = textView {
                        padding = dip(5)
                        gravity = Gravity.CENTER
                    }.lparams(wrapContent, matchParent)
                    blandImageView = imageView {
                    }.lparams(dip(100), dip(30))
                    typeTextView = textView {
                        padding = dip(5)
                        gravity = Gravity.LEFT
                    }.lparams(dip(100), wrapContent)
                }
                containerLayout2 = linearLayout {
                    padding = dip(10)
                    orientation = LinearLayout.HORIZONTAL
                    nameTextView = textView {
                        padding = dip(5)
                        gravity = Gravity.LEFT
                        textSize = 16f
                    }.lparams(wrapContent, matchParent)
                }.lparams(matchParent, wrapContent)
                containerLayout3 = linearLayout {
                    categoryTextView = textView {
                        padding = dip(5)
                        gravity = Gravity.LEFT
                    }.lparams(dip(100), wrapContent)
                    iineTextView = textView {
                        padding = dip(5)
                        gravity = Gravity.LEFT
                    }.lparams(dip(100), wrapContent)
                    colorTextView = textView {
                        padding = dip(5)
                        gravity = Gravity.LEFT
                    }.lparams(dip(100), wrapContent)
                }.lparams(wrapContent, matchParent)
            }.apply {
                this@CoordinateListUI.addView(this)
            }
        }
    }

    fun update(pos: Int) {
        numberTextView?.text = list[pos].number
        hasCheckBox?.isChecked = itemSet.contains(list[pos].number)
        hasCheckBox?.isEnabled = !itemSet.contains(list[pos].number)
        hasCheckBox?.setOnClickListener {
            changedFlag = true
        }
        hasCheckBox?.setOnCheckedChangeListener {
            compoundButton: CompoundButton, b: Boolean ->
            if(hasCheckBox?.isChecked!!){
                CoordinateListActivity.checkedSet.add(list[pos].number)
            } else {
                CoordinateListActivity.checkedSet.remove(list[pos].number)
            }
        }
        realityTextView?.text = when(list[pos].reality.toInt()) {
            1 -> context.getString(R.string.n)
            2 -> context.getString(R.string.r)
            3 -> context.getString(R.string.sr)
            4 -> context.getString(R.string.pr)
            5 -> context.getString(R.string.kr)
            else -> ""
        }
        blandImageView?.setImageBitmap(
            when (list[pos].bland) {
                context.getString(R.string.sh) -> { getResourceFromAssets("blandLogos/logo-sweethoney.png") }
                context.getString(R.string.gy) -> { getResourceFromAssets("blandLogos/logo-girlsyell.png") }
                context.getString(R.string.sa) -> { getResourceFromAssets("blandLogos/logo-secretalice.png") }
                context.getString(R.string.dw) -> { getResourceFromAssets("blandLogos/logo-dollywaltz.png") }
                context.getString(R.string.rb) -> { getResourceFromAssets("blandLogos/logo-romancebeat.png") }
                context.getString(R.string.ps) -> { getResourceFromAssets("blandLogos/logo_prismstone.png") }
                context.getString(R.string.tr) -> { getResourceFromAssets("blandLogos/logo-twinkleribbon.png") }
                context.getString(R.string.ld) -> { getResourceFromAssets("blandLogos/logo-lovedevi.png") }
                context.getString(R.string.bm) -> { getResourceFromAssets("blandLogos/logo-babymonster.png") }
                context.getString(R.string.bp) -> { getResourceFromAssets("blandLogos/logo-brilliantprince.png") }
                else -> { (getResourceFromAssets("blandLogos/none.png")) }
            }
        )
        nameTextView?.text = list[pos].name
        categoryTextView?.text = list[pos].category
        iineTextView?.text = "${list[pos].iine}${context.getString(R.string.iine)}"
        colorTextView?.text = list[pos].color
    }

    private fun getResourceFromAssets(path: String): Bitmap {
        val iStrm: InputStream = context.assets.open(path)
        return try {
            BitmapFactory.decodeStream(iStrm)
        } catch (e: IOException) {
            e.stackTrace
            val iStrmNone: InputStream = context.assets.open("blandLogos/none.png")
            BitmapFactory.decodeStream(iStrmNone)
        } finally {
            iStrm.close()
        }
    }
}