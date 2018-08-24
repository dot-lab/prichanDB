package xyz.dot_lab.prichandb

import android.content.DialogInterface
import android.os.Bundle
import android.preference.DialogPreference
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import db.CoordinateDatabaseOpenHelper
import db.ItemDataParser
import entity.ItemData
import org.jetbrains.anko.db.*
import org.jetbrains.anko.listView
import org.jetbrains.anko.verticalLayout
import ui.CoordinateListAdapter
import ui.CoordinateListUI

class CoordinateListActivity : AppCompatActivity() {
    companion object {
        var checkedSet = mutableSetOf<String>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val selectedGroupName: String = intent.getStringExtra("groupName")
        val coordinateList: List<ItemData> = getCoordinateList(selectedGroupName)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = selectedGroupName
            actionBar.setDisplayHomeAsUpEnabled(true)

        }

        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        verticalLayout {
            listView {
                adapter = CoordinateListAdapter(context,coordinateList,pref.getStringSet(getString(R.string.prefKey), setOf()))
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // 保存ボタン
        when (item?.itemId) {
            R.id.save -> {
                saveToPreference(checkedSet)
                Toast.makeText(applicationContext,"入手コーデ情報を保存しました",Toast.LENGTH_SHORT).show()
                finish()
            }
            android.R.id.home -> {
                // TODO 戻るボタン
                finish()
            }
        }
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
    private fun saveToPreference(checkedItems: MutableSet<String>) {
        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        var hasItems = pref.getStringSet(getString(R.string.prefKey), mutableSetOf()) + checkedItems
        pref.edit()
                .putStringSet(getString(R.string.prefKey),hasItems)
                .apply()
    }
}