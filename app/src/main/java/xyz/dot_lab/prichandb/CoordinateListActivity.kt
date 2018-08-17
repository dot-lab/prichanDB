package xyz.dot_lab.prichandb

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import db.CoordinateDatabaseOpenHelper
import db.ItemDataParser
import entity.ItemData
import org.jetbrains.anko.db.select


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

        Log.d("CoordinateListActivity",coordinateList[0].name)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun getCoordinateList(groupName: String): List<ItemData> {
        val dbHelper = CoordinateDatabaseOpenHelper.getInstance(applicationContext)
        var coordinateList: List<ItemData> = mutableListOf()
        dbHelper.openDatabase()
        dbHelper.use {
           coordinateList = select(groupName).parseList(ItemDataParser())
        }
        return coordinateList
    }
}