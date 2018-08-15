package xyz.dot_lab.prichandb

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import db.CoordinateDatabaseOpenHelper
import entity.ItemData
import org.jetbrains.anko.db.select
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var dataBase: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = CoordinateDatabaseOpenHelper.getInstance(this)
        setDatabase(dbHelper)
        dbHelper.use {
            val itemList = select("bland_gy").parseList(db.ItemDataParser())
            Log.d("onCreate()",itemList[0].name)
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
}


