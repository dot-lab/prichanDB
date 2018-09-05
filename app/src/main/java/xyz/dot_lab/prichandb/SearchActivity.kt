package xyz.dot_lab.prichandb

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import org.jetbrains.anko.button
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class SearchActivity: AppCompatActivity() {
    val REQUEST_CODE: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "コーデ検索"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        verticalLayout {
            textView {
                text = "hello,world"
            }
            button {
                text = "CoordinateListActivity"
            }.setOnClickListener{
                val intent = Intent(applicationContext,CoordinateListActivity::class.java)
                startActivityForResult(intent,REQUEST_CODE)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}