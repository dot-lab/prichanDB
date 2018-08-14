package db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

class CoordinateDatabaseOpenHelper(context: Context):ManagedSQLiteOpenHelper(context,"coordinate.db",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.run {
            db.execSQL("select name from sqlite_master where type='table';")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}