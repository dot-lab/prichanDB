package db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable

class HaslistDatabaseHelper(context: Context): ManagedSQLiteOpenHelper(context,"hasList.db",null,1) {
    companion object {
        const val tableName = "list"
        private var instance: HaslistDatabaseHelper? = null

        fun getInstance(context: Context): HaslistDatabaseHelper {
            return instance ?: HaslistDatabaseHelper(context.applicationContext)!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("onCreate","onCreate通過")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    fun openDatabase():SQLiteDatabase {
        return writableDatabase
    }
}