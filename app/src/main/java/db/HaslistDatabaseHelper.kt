package db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable

class HaslistDatabaseHelper(context: Context): ManagedSQLiteOpenHelper(context,"hasList.db",null,1) {
    companion object {
        const val tableName = "hasList"
        private var instance: HaslistDatabaseHelper? = null
        fun getInstance(context: Context): HaslistDatabaseHelper {
            return instance ?: HaslistDatabaseHelper(context.applicationContext)!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.run {
            createTable(tableName,ifNotExists = true,columns = *arrayOf("number" to TEXT, "has" to INTEGER))
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}