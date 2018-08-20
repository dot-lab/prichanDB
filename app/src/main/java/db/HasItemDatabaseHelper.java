package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import ui.CoordinateListAdapter;

public class HasItemDatabaseHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "hasItem";
    public static final String DB_NAME = "hasItems3.db";
    public static int DB_VERSION = 3;
    public HasItemDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + TABLE_NAME
                + " (number TEXT, has INTEGER)"
        );
        Log.d("onCreate","通過！");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int ordVer, int newVer) {
        if (ordVer < newVer) {
            sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
    public void writeToDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        List<String> list = CoordinateListAdapter.Companion.getCheckedList();
        Cursor c = db.rawQuery("select * from sqlite_master where type='table' and name = "+"'"+TABLE_NAME+"'",null);
        if(c.moveToFirst()) Log.d("writeToDatabase","テーブルは存在した！");
        for(String nums : list) {
            ContentValues values = new ContentValues();
            String s = "'" +nums+"'";
            Log.d("writeToDatabase",s);
            values.put("number",s);
            values.put("has",1);
            try{
                long result = db.insert(TABLE_NAME,null,values);
                if(result == -1) {
                    Log.d("writeToDatabase","書き込み失敗！");
                }
            } finally {
                db.close();
            }
        }
    }
    public boolean checkFromDatabase(String num) {
        SQLiteDatabase db = getReadableDatabase();
        String s = "'" + num + "'";
        Log.d("checkFromDatabase",s);
        Cursor c = db.rawQuery("select * from "+TABLE_NAME+" where number = " + s,null);
        if(c.moveToFirst()) {
            Log.d("checkFromDatabase",c.getString(c.getColumnIndex("number")));
            c.close();
            return true;
        } else {
            Log.d("checkFromDatabase","データベース内に"+s+"のデータがない！");
            c.close();
            return false;
        }
    }
}
