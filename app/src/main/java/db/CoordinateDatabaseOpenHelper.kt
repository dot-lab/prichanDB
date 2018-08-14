package db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import java.io.*

class CoordinateDatabaseOpenHelper(context: Context): ManagedSQLiteOpenHelper(context,"coordinate.db",null,1) {
    companion object {
        private val DB_NAME = "coordinate.db"
        private val DB_NAME_ASSET = "coordinate.db"
        private val DB_VERSION = 2
        private lateinit var myContext: Context
        private lateinit var databasePath: File
        private lateinit var myDatabase: SQLiteDatabase
        private var instance: CoordinateDatabaseOpenHelper? = null

        fun getInstance(context: Context): CoordinateDatabaseOpenHelper {
            myContext = context
            databasePath = context.getDatabasePath(DB_NAME)
            return instance ?: CoordinateDatabaseOpenHelper(context.applicationContext)
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun createEmptyDataBase() {
        if (!checkDataBaseExists()) {
            readableDatabase
            try {
                // データベースのコピー
                copyDataBaseFromAsset()
                val dbPath = databasePath.absolutePath
                var checkDb: SQLiteDatabase? = null
                try {
                    checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE)
                } catch (e: SQLiteException) {
                    e.stackTrace
                }
                if (checkDb != null) {
                    checkDb.version = DB_VERSION
                    checkDb.close()
                }
            } catch (e: IOException){
                e.stackTrace
            }
        }
    }
    private fun checkDataBaseExists(): Boolean {
        val dbPath = databasePath.absolutePath
        var checkDb: SQLiteDatabase
        try {
            checkDb = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
            // データベースは存在していない
            e.stackTrace
            return false
        }
        // データベースが存在しなければfalseを返す
        if (checkDb == null) return false

        val oldVersion = checkDb.version
        val newVersion = DB_VERSION

        // データベースが存在していて,最新
        if (oldVersion == newVersion ) {
            Log.d("checkDataBaseExists()","データベースは最新です。version："+checkDb.version)
            checkDb.close()
            return true
        }

        // データベースが存在していて,最新ではない
        val f = File(dbPath)
        f.delete()
        return false
    }
    private fun copyDataBaseFromAsset() {
        val iStrm: InputStream = myContext.assets.open(DB_NAME_ASSET)
        val oStrm: OutputStream = FileOutputStream(databasePath)

        iStrm.use {
            it.copyTo(oStrm)
        }

        oStrm.flush()
        oStrm.close()
        iStrm.close()
    }

    fun openDatabase(): SQLiteDatabase {
        return readableDatabase
    }
}