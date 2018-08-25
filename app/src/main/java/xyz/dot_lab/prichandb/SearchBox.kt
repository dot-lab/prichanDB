package xyz.dot_lab.prichandb

import android.database.Cursor
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import db.CoordinateDatabaseOpenHelper
import org.jetbrains.anko.*
import db.*

class SearchBox: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        relativeLayout {
            spinner {
//                adapter = ArrayAdapter(
//                        applicationContext,
//                        android.R.layout.simple_spinner_dropdown_item,
//                        getGroupNames())
            }
            switch {
                text = "所持しているコーデは表示しない"
            }
        }
    }
//    private fun getGroupNames(): Array<String> {
//        var groupNames = emptyArray<String>()
//        val dbHelper = CoordinateDatabaseOpenHelper.getInstance(applicationContext)
//        dbHelper.use {
//            val c: Cursor = rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name!='android_metadata' ORDER BY name DESC",null)
//            var isEoF: Boolean = c.moveToFirst()
//            while (isEoF) {
//                groupNames.plus(c.getString(1))
//                isEoF = c.moveToNext()
//            }
//            c.close()
//        }
//        return groupNames
//    }
}