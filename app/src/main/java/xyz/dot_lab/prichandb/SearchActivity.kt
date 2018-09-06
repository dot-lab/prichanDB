package xyz.dot_lab.prichandb

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Spinner
import org.jetbrains.anko.*

class SearchActivity: AppCompatActivity() {
    private val REQUEST_CODE: Int = 1
    var radio1: RadioButton? = null
    var radio2: RadioButton? = null
    var radio3: RadioButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "コーデ検索"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        verticalLayout {
            textView {
                text = "検索対象"
            }
            spinner {
                val items = resources.getStringArray(R.array.GroupNames)
                val adapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, items)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                this.adapter = adapter
            }.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val groupNameSpinner: Spinner = parent as Spinner
                    val selected: String = groupNameSpinner.selectedItem.toString()
                    toast(selected)
                }
            }
            textView {
                text = "表示対象"
            }
            radioGroup {
                radio1 = radioButton {
                    text = "所持コーデのみ表示"
                }
                radio2 = radioButton {
                    text = "未所持コーデのみ表示"
                }
                radio3 = radioButton {
                    text = "すべて表示"
                }
                check(radio1!!.id)
            }
            button {
                text = "検索"
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