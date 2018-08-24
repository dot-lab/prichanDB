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
import xyz.dot_lab.prichandb.CoordinateListActivity
import xyz.dot_lab.prichandb.R
import java.io.IOException
import java.io.InputStream

class CoordinateListAdapter(private val coordinateList: List<ItemData>, private val context: Context): BaseAdapter() {
    companion object {
        // チェックボックスの値が変更されたかどうか
        var changedFlag: Boolean = false
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
        // できればここは別ファイルにわけたい
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
                        // すでに持っている場合は最初からチェックされている
                        // isChecked = alreadyHasItem(coordinateList[position].number)
                        // すでにチェックされている場合は無効化
                        if(isChecked) isEnabled = false
                        setOnClickListener {
                            changedFlag = true
                            // TODO チェック入れたとき
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
                                text = getString(R.string.n)
                            }
                            2 -> {
                                text = getString(R.string.r)
                            }
                            3 -> {
                                text = getString(R.string.sr)
                            }
                            4 -> {
                                text = getString(R.string.pr)
                            }
                            5 -> {
                                text = getString(R.string.kr)
                            }
                        }
                        padding = dip(5)
                        gravity = Gravity.CENTER
                    }.lparams(wrapContent, matchParent)
                    imageView { // bland
                        when (coordinateList[position].bland) {
                            getString(R.string.sh) -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-sweethoney.png"))
                            }
                            getString(R.string.gy) -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-girlsyell.png"))
                            }
                            getString(R.string.sa) -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-secretalice.png"))
                            }
                            getString(R.string.dw) -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-dollywaltz.png"))
                            }
                            getString(R.string.rb) -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-romancebeat.png"))
                            }
                            getString(R.string.ps) -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo_prismstone.png"))
                            }
                            getString(R.string.tr) -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-twinkleribbon.png"))
                            }
                            getString(R.string.ld) -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-lovedevi.png"))
                            }
                            getString(R.string.bm) -> {
                                setImageBitmap(getResourceFromAssets("blandLogos/logo-babymonster.png"))
                            }
                            getString(R.string.bp) -> {
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
                        text = "${coordinateList[position].iine}${getString(R.string.iine)}"
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
//    // 引数で渡すNumberがプリファレンスに保存済みかどうか＝持っているかどうか
//    private fun alreadyHasItem(itemNumber: String): Boolean {
//        if (hasItem.contains(itemNumber)) return true
//        return false
//    }
}