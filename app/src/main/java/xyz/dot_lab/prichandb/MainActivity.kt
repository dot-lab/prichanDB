package xyz.dot_lab.prichandb

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import db.CoordinateDatabaseOpenHelper
import entity.ItemData
import org.jetbrains.anko.db.select
import org.jetbrains.anko.listView
import org.jetbrains.anko.verticalLayout
import ui.CoordinateGroupListAdapter
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var dataBase: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val groupNameList = getGroupNameList()
        verticalLayout {
            listView {
                adapter = CoordinateGroupListAdapter(context,groupNameList)
            }
        }
    }
    private fun setDatabase(helper: CoordinateDatabaseOpenHelper) {
        try {
            helper.createEmptyDataBase()
            dataBase = helper.openDatabase()
        } catch (e: IOException) {
            Log.d("setDatabase()","データベースの作成に失敗しました")
            e.stackTrace
        } catch (e: SQLiteException) {
            Log.d("setDatabase()","データベースを読み込めません")
            e.stackTrace
        }
    }
    private fun getGroupNameList(): MutableList<String> {
        val groupNameList: MutableList<String> = mutableListOf()
        val dbHelper = CoordinateDatabaseOpenHelper.getInstance(this)
        setDatabase(dbHelper)
        dbHelper.use {
            val c: Cursor = rawQuery("SELECT * FROM sqlite_master WHERE type='table' ORDER BY name DESC",null)
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


