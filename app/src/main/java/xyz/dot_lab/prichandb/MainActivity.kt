package xyz.dot_lab.prichandb

import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import db.CoordinateDatabaseOpenHelper
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import dialog.SearchDialog
import adapter.CoordinateGroupListAdapter
import android.app.Activity
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.custom.*
import kotlinx.android.synthetic.main.custom.view.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var dataBase: SQLiteDatabase
    private val REQUEST_CODE: Int = 1
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val groupNameList = getGroupNameList()
            frameLayout {
                listView {
                    adapter = CoordinateGroupListAdapter(context, groupNameList)
                    onItemClickListener = AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view1: View, position: Int, id: Long ->
                        // タップしたグループ名を取得
                        val groupName:String = adapterView.getItemAtPosition(position).toString()
                        val intent = Intent(applicationContext,CoordinateListActivity::class.java)
                        intent.putExtra("groupName",groupName)
                        startActivityForResult(intent,REQUEST_CODE)
                    }
                }
                floatingActionButton {
                    imageResource = android.R.drawable.ic_menu_search
                    size = dip(24)
                }.lparams {
                    wrapContent
                    margin = dip(10)
                    marginEnd = dip(16)
                    gravity = Gravity.BOTTOM or Gravity.END
                }.setOnClickListener {
                    val intent = Intent(applicationContext,SearchActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE)
                }
            }
        }
    // データベースを呼び出す
    private fun setDatabase(helper: CoordinateDatabaseOpenHelper) {
        try {
            helper.createEmptyDataBase()
            dataBase = helper.openReadableDatabase()
        } catch (e: IOException) {
            Log.d("setDatabase()","データベースの作成に失敗しました")
            e.stackTrace
        } catch (e: SQLiteException) {
            Log.d("setDatabase()","データベースを読み込めません")
            e.stackTrace
        }
    }
    // グループ名のリストを取得
    private fun getGroupNameList(): MutableList<String> {
        val groupNameList: MutableList<String> = mutableListOf()
        val dbHelper = CoordinateDatabaseOpenHelper.getInstance(this)
        setDatabase(dbHelper)
        dbHelper.use {
            val c: Cursor = rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name!='android_metadata' ORDER BY name DESC",null)
            var isEoF: Boolean = c.moveToFirst()
            while (isEoF) {
                groupNameList.add(c.getString(1))
                isEoF = c.moveToNext()
            }
            c.close()
        }
        return groupNameList
    }
}


