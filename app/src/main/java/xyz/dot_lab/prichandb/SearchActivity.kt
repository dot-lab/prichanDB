package xyz.dot_lab.prichandb

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class SearchActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            textView {
                text = "hello,world"
            }
        }
    }
}