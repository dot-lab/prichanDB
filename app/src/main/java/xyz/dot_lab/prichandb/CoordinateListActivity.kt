package xyz.dot_lab.prichandb

import android.content.DialogInterface
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import db.CoordinateDatabaseOpenHelper
import db.ItemDataParser
import entity.ItemData
import org.jetbrains.anko.*
import org.jetbrains.anko.db.*
import ui.ConfirmDialog
import adapter.CoordinateListAdapter
import ui.CoordinateListUI
import ui.CoordinateListUI.Companion.changedFlag

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
                adapter = CoordinateListAdapter(context, coordinateList, pref.getStringSet(getString(R.string.prefKey), setOf()))
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
                return if(CoordinateListUI.changedFlag) {
                    saveToPreference(checkedSet)
                    changedFlag = false
                    finish()
                    true
                } else {
                    toast("入手したコーデを選択してください")
                    true
                }
            }
            android.R.id.home -> {
                if(changedFlag) {
                    val dialog = ConfirmDialog()
                    dialog.onClickListener = DialogInterface.OnClickListener{
                        dialogInterface: DialogInterface, i: Int ->
                        changedFlag = false
                        finish()
                    }
                    dialog.show(supportFragmentManager,"confirm")
                } else {
                    finish()
                }
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
        toast("所持コーデ情報を更新しました")
    }
}