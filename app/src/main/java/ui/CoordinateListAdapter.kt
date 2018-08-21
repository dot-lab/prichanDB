package ui

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import db.CoordinateDatabaseOpenHelper
import entity.ItemData
import org.jetbrains.anko.*
import org.jetbrains.anko.db.update
import xyz.dot_lab.prichandb.R
import java.io.IOException
import java.io.InputStream

class CoordinateListAdapter(private val coordinateList: List<ItemData>, private val context: Context): BaseAdapter() {
    companion object {
        var checkedList: List<String> = mutableListOf()
    }
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
            linearLayout {
                // wrapper
                id = R.id.wrapper
                orientation = LinearLayout.VERTICAL
                padding = dip(10)
                lparams(matchParent, wrapContent)
                linearLayout {
                    // number has reality
                    id = R.id.inner4
                    orientation = LinearLayout.HORIZONTAL
                    lparams(wrapContent, matchParent)
                    checkBox {
                        id = R.id.has
                        gravity = Gravity.CENTER
                        isChecked = coordinateList[position].has.toInt() == 1
                        setOnClickListener(View.OnClickListener {
                            if (isChecked) {
                                checkedList += coordinateList[position].number
                                coordinateList[position].has = 1
                            } else {
                                checkedList -= coordinateList[position].number
                                coordinateList[position].has = 0
                            }
                        })
                    }.lparams(wrapContent, matchParent)
                    textView {
                        // number
                        id = R.id.number
                        text = coordinateList[position].number
                        padding = dip(5)
                        gravity = Gravity.CENTER
                    }.lparams(wrapContent, matchParent)
                    textView {
                        // reality
                        id = R.id.name
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
                    imageView {
                        // blandLogo
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
                    textView {
                        // type
                        id = R.id.type
                        padding = dip(5)
                        gravity = Gravity.LEFT
                        text = coordinateList[position].type
                    }.lparams(dip(100), wrapContent)
                }
                linearLayout {
                    // inner1 name
                    id = R.id.inner
                    padding = dip(10)
                    orientation = LinearLayout.HORIZONTAL
                    textView {
                        // name
                        id = R.id.name
                        text = coordinateList[position].name
                        padding = dip(5)
                        textSize = 16f
                        gravity = Gravity.LEFT
                    }.lparams(wrapContent, matchParent)
                }.lparams(matchParent, wrapContent)
                linearLayout {
                    // inner3
                    id = R.id.inner3
                    textView {
                        // category
                        id = R.id.category
                        padding = dip(5)
                        gravity = Gravity.LEFT
                        text = coordinateList[position].category
                    }.lparams(dip(100), wrapContent)
                    textView {
                        // iine
                        id = R.id.iine
                        padding = dip(5)
                        gravity = Gravity.LEFT
                        text = coordinateList[position].iine.toString() + "いいね★"
                    }.lparams(dip(100), wrapContent)
                    textView {
                        // color
                        id = R.id.color
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