package db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import java.io.*

class CoordinateDatabaseOpenHelper(context: Context): ManagedSQLiteOpenHelper(context,"coordinate.db",null,8) {
    companion object {
        private const val DB_NAME = "coordinate.db"
        private const val DB_NAME_ASSET = "coordinate.db"
        private const val DB_VERSION = 8
        private lateinit var myContext: Context
        private lateinit var databasePath: File
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
        if (checkDataBaseExists()) {
            // データベースはすでに存在する
        }
        else {
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
        val checkDb: SQLiteDatabase
        try {
            checkDb = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
            // データベースは存在していない
            e.stackTrace
            Log.d("checkDataBaseExists()","データベースが存在しません")
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
        Log.d("checkDataBaseExists()","データベースが最新ではありませんでした")
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

    fun openReadableDatabase(): SQLiteDatabase {
        return readableDatabase
    }
    fun openWritableDatabase(): SQLiteDatabase {
        return writableDatabase
    }
}