package xyz.dot_lab.prichandb

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class CoordinateListActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent = intent
        val selectedGroupName = intent.getStringExtra("groupName")
        Log.d("CoordinateListActivity",selectedGroupName)
    }
}