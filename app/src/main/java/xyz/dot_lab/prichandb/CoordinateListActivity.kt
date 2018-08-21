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

class CoordinateListActivity : AppCompatActivity() {
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
                adapter = CoordinateListAdapter(coordinateList, context)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save -> {
                writeToDatabase("なかよしチャンネル")
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

    private fun writeToDatabase(groupName: String) {
        val helper = CoordinateDatabaseOpenHelper.getInstance(applicationContext)
        val hasItemsCounts = CoordinateListAdapter.checkedList.size
        val hasItems = CoordinateListAdapter.checkedList
        var hasItemsNumbers = mutableListOf<String>()
        for (i in hasItems) {
            hasItemsNumbers.add(i)
        }
        val database = helper.openWritableDatabase()
        database.use {
            val c = it.rawQuery("select number from $groupName", null)
            if (c.moveToFirst()) {
                Log.d("writeToDB", "$groupName" + "内の所持コーデアイテムは"+"$hasItemsCounts”+件")
                // TODO DB書き込み
            }
        }
    }
}