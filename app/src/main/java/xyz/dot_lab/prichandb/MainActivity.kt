package xyz.dot_lab.prichandb

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import entity.ItemData
import org.jetbrains.anko.ctx
import org.jetbrains.anko.listView
import org.jetbrains.anko.verticalLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            listView {

            }
        }
    }
}


