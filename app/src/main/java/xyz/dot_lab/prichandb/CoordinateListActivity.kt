package xyz.dot_lab.prichandb

<<<<<<< HEAD
import android.app.Dialog
import android.content.DialogInterface
=======
>>>>>>> parent of 99e37a8... なんかいろいろ進んだ
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
        val selectedGroupName: String = intent.getStringExtra("groupName")
        val coordinateList: List<ItemData> = getCoordinateList(selectedGroupName)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = selectedGroupName
            actionBar.setDisplayHomeAsUpEnabled(true)

        }
<<<<<<< HEAD

=======
>>>>>>> parent of 99e37a8... なんかいろいろ進んだ
        verticalLayout {
            listView {
                adapter = CoordinateListAdapter(coordinateList,context)
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
<<<<<<< HEAD
                // changedFlagが立っていれば
                if (CoordinateListAdapter.changedFlag) {
                    // TODO 保存
                    Toast.makeText(applicationContext, "所持コーデ情報を更新しました", Toast.LENGTH_SHORT).show()
                    // 書き込んだらflagはfalseに戻す
                    CoordinateListAdapter.changedFlag = false
                    finish()
                    return true
                } else {
                    Toast.makeText(applicationContext,"入手したコーデを選択してください",Toast.LENGTH_SHORT).show()
                }
            }
            android.R.id.home -> {
                if(CoordinateListAdapter.changedFlag) {
                    // TODO ダイアログ表示（保存してないけどいいの？）
                }
                CoordinateListAdapter.changedFlag = false
                finish()
=======
                // プリファレンスに書き込む

>>>>>>> parent of 99e37a8... なんかいろいろ進んだ
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
//    private fun saveToPreference(prefSet: MutableSet<String>,checked: MutableSet<String>): MutableSet<String> {
//        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().putStringSet(
//                getString(R.string.prefKey),checked
//        ).apply()
//        prefSet.plus(checked)
//        Log.d("saveToPref","$prefSet")
//        return prefSet
//    }
}