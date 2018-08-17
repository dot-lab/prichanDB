package xyz.dot_lab.prichandb

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

class CoordinateListActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent = intent
        val selectedGroupName = intent.getStringExtra("groupName")
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = selectedGroupName
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}