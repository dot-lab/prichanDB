package ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import entity.ItemData
import org.jetbrains.anko.*
import java.io.IOException
import java.io.InputStream

class CoordinateListAdapter(private val coordinateList: List<ItemData>, private val context: Context): BaseAdapter() {

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
            linearLayout { // Wrapper
                orientation = LinearLayout.VERTICAL
                padding = dip(10)
                lparams(matchParent, wrapContent)
                linearLayout { // Container : has,number,realty,bland,type
                    orientation = LinearLayout.HORIZONTAL
                    lparams(wrapContent, matchParent)
                    checkBox { // has
                        gravity = Gravity.CENTER
                        setOnClickListener {
                            Log.d("onClick","$position isChecked = $isChecked" )
                        }
                    }.lparams(wrapContent, matchParent)
                    textView { // number
                        text = coordinateList[position].number
                        padding = dip(5)
                        gravity = Gravity.CENTER
                    }.lparams(wrapContent, matchParent)
                    textView { // reality
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
                        gravity = Gravity.CENTER
                    }.lparams(wrapContent, matchParent)
                    imageView { // bland
                        when (coordinateList[position].bland) {
                            "Sweet Honey" -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-sweethoney.png"))
                            }
                            "Girl's Yell" -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-girlsyell.png"))
                            }
                            "Secret Alice" -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-secretalice.png"))
                            }
                            "Dolly Waltz" -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-dollywaltz.png"))
                            }
                            "Romance Beat" -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-romancebeat.png"))
                            }
                            "Prism Stone" -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo_prismstone.png"))
                            }
                            "Twinkle Ribbon" -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-twinkleribbon.png"))
                            }
                            "Love Devi" -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-lovedevi.png"))
                            }
                            "Baby Monster" -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-babymonster.png"))
                            }
                            "Brilliant Prince" -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-brilliantprince.png"))
                            }
                            else -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/none.png"))
                            }
                        }
                    }.lparams(dip(100),dip(30))
                    textView { // type
                        padding = dip(5)
                        gravity = Gravity.LEFT
                        text = coordinateList[position].type
                    }.lparams(dip(100), wrapContent)
                }
                linearLayout { // contains: name
                    padding = dip(10)
                    orientation = LinearLayout.HORIZONTAL
                    textView { // name
                        text = coordinateList[position].name
                        padding = dip(5)
                        textSize = 16f
                        gravity = Gravity.LEFT
                    }.lparams(wrapContent, matchParent)
                }.lparams(matchParent, wrapContent)
                linearLayout { // contains : category,iine,color
                    textView { // category
                        padding = dip(5)
                        gravity = Gravity.LEFT
                        text = coordinateList[position].category
                    }.lparams(dip(100), wrapContent)
                    textView { // iine
                        padding = dip(5)
                        gravity = Gravity.LEFT
                        text = coordinateList[position].iine.toString() + "いいね★"
                    }.lparams(dip(100), wrapContent)
                    textView { // color
                        padding = dip(5)
                        gravity = Gravity.LEFT
                        text = coordinateList[position].color
                    }.lparams(dip(100), wrapContent)
                }.lparams(wrapContent, matchParent)
            }
        }
    }
    // 引数でAssets内の画像パスを指定して,Bitmapで返す
    private fun getResourceFromAssets(path: String): Bitmap {
        var iStrm: InputStream = context.assets.open(path)
        return try {
            BitmapFactory.decodeStream(iStrm)
        } catch (e: IOException) {
            e.stackTrace
            var iStrmNone: InputStream = context.assets.open("blandLogos/none.png")
            BitmapFactory.decodeStream(iStrmNone)
        } finally {
             iStrm.close()
        }
    }
}