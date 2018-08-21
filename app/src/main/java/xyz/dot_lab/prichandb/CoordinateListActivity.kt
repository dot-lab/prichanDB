package xyz.dot_lab.prichandb

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import db.CoordinateDatabaseOpenHelper
import db.ItemDataParser
import entity.ItemData
import org.jetbrains.anko.db.*
import org.jetbrains.anko.listView
import org.jetbrains.anko.verticalLayout
import ui.CoordinateListAdapter

class CoordinateListActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val hasItemCounts = CoordinateListAdapter.checkedList.size
        val hasItems = CoordinateListAdapter.checkedList
        var hasItemNumber = mutableListOf<String>()
        for (i in hasItems) {
            hasItemNumber.add(i)
        }
        val helper = CoordinateDatabaseOpenHelper.getInstance(applicationContext)
        when (item?.itemId) {
            R.id.save -> {
                Log.d("save","チェックされた数：$hasItemCounts,チェックされたアイテムの番号：$hasItemNumber")
//                val db = helper.openWritableDatabase()
////                db.use {
////                    val c = it.rawQuery("select * from $selectedGroupName where numbers like $hasItemNumbers",null)
////                    if (c.moveToFirst()) {
////                        Log.d("optionItemSelected","条件に一致：${c.count}}件")
////                    }
////                }
            }
        }
        finish()
        return super.onOptionsItemSelected(item)
    }
    // コーデアイテムのリストを取得
    private fun getCoordinateList(groupName: String): List<ItemData> {
        val dbHelper = CoordinateDatabaseOpenHelper.getInstance(applicationContext)
        var coordinateList: List<ItemData> = listOf()
        dbHelper.openReadableDatabase()
        dbHelper.use {
           coordinateList = select(groupName).parseList(ItemDataParser())
        }
        return coordinateList
    }
}