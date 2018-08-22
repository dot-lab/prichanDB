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
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.listView
import org.jetbrains.anko.verticalLayout
import ui.CoordinateListAdapter
class CoordinateListActivity : AppCompatActivity() {

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

        // プリファレンスからひっぱる
        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        var checkedSet =  pref.getStringSet(getString(R.string.prefKey), mutableSetOf())

        // デバッグ用 セットの中身一覧
        for (i in checkedSet) {
            Log.d("checkedSet",i)
        }

        verticalLayout {
            listView {
                adapter = CoordinateListAdapter(coordinateList,context,checkedSet)
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
                // changedFlagが立っていれば
                if (CoordinateListAdapter.changedFlag) {
                    // TODO プリファレンスに書き込む
//                    defaultSharedPreferences.edit()
//                            .putStringSet(getString(R.string.prefKey), CoordinateListAdapter.checkedSet)
//                            .apply()
                    // 書き込んだらflagはfalseに戻す
                    Toast.makeText(applicationContext, "所持コーデ情報を保存しました", Toast.LENGTH_SHORT).show()
                    CoordinateListAdapter.changedFlag = false
                    finish()
                    return true
                } else {
                    Toast.makeText(applicationContext,"入手したコーデを選択してください",Toast.LENGTH_SHORT).show()
                }
            }
            android.R.id.home -> {
                if(CoordinateListAdapter.changedFlag) {
                    AlertDialog.Builder(this).apply {
                            setTitle("確認")
                            setMessage("変更があります。保存せずに戻りますか？")
                            setPositiveButton("保存しないでメインメニューに戻る",DialogInterface.OnClickListener{
                                _: DialogInterface, i: Int ->
                                // TODO メモリリーク起こってるので対処
                                Toast.makeText(applicationContext,"メインメニューに戻ります",Toast.LENGTH_SHORT).show()
                                CoordinateListAdapter.changedFlag = false
                                finish()
                            })
                            setNegativeButton("キャンセル",null)
                            show()
                    }
                }
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
}