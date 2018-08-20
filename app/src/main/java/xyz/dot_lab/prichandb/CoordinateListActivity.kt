package xyz.dot_lab.prichandb

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import db.CoordinateDatabaseOpenHelper
import db.HaslistDatabaseHelper
import db.ItemDataParser
import entity.ItemData
import org.jetbrains.anko.db.*
import org.jetbrains.anko.listView
import org.jetbrains.anko.verticalLayout
import ui.CoordinateListAdapter


class CoordinateListActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val selectedGroupName = intent.getStringExtra("groupName")
        val coordinateList = getCoordinateList(selectedGroupName)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = selectedGroupName
            actionBar.setDisplayHomeAsUpEnabled(true)

        }

        verticalLayout {
            listView {
                adapter = CoordinateListAdapter(coordinateList,context)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionmenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save -> {
                writeToDataBase()
            }
        }
        finish()
        return super.onOptionsItemSelected(item)
    }


    private fun getCoordinateList(groupName: String): List<ItemData> {
        val dbHelper = CoordinateDatabaseOpenHelper.getInstance(applicationContext)
        var coordinateList: List<ItemData> = listOf()
        dbHelper.openDatabase()
        dbHelper.use {
           coordinateList = select(groupName).parseList(ItemDataParser())
        }
        return coordinateList
    }
    // DBに保存
    // チェックボックスを入れた項目をデータベースに書き込む
    // TODO createTable()を通過していない
    private fun writeToDataBase() {
        var nums = CoordinateListAdapter.checkedList.keys
        var helper = HaslistDatabaseHelper.getInstance(applicationContext)
        var db = helper.openDatabase()
        helper.use {
            db.createTable(HaslistDatabaseHelper.tableName,ifNotExists = true,columns = *arrayOf("number" to TEXT, "has" to INTEGER))
            for (num in nums) {
                insert(HaslistDatabaseHelper.tableName, "number" to num, "has" to 1)
                Log.d("writeToDataBase", "$num を 1 として書き込みました。")
            }
        }
    }
}