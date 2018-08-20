package xyz.dot_lab.prichandb

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import db.CoordinateDatabaseOpenHelper
import db.ItemDataParser
import entity.ItemData
import org.jetbrains.anko.db.*
import org.jetbrains.anko.listView
import org.jetbrains.anko.verticalLayout
import ui.CoordinateListAdapter
import db.HasItemDatabaseHelper

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
        val helper = HasItemDatabaseHelper(applicationContext,db.HasItemDatabaseHelper.DB_NAME,null,db.HasItemDatabaseHelper.DB_VERSION)
        helper.checkFromDatabase("DW-01")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionmenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val helper = HasItemDatabaseHelper(applicationContext,db.HasItemDatabaseHelper.DB_NAME,null,db.HasItemDatabaseHelper.DB_VERSION)
        when (item?.itemId) {
            R.id.save -> {
                helper.writeToDatabase()
            }
        }
        finish()
        return super.onOptionsItemSelected(item)
    }
    // コーデアイテムのリストを取得
    private fun getCoordinateList(groupName: String): List<ItemData> {
        val dbHelper = CoordinateDatabaseOpenHelper.getInstance(applicationContext)
        var coordinateList: List<ItemData> = listOf()
        dbHelper.openDatabase()
        dbHelper.use {
           coordinateList = select(groupName).parseList(ItemDataParser())
        }
        return coordinateList
    }
}