package com.amicus.dz25_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {// класс не задействован, здесь для примера
    public DBHelper(@Nullable Context context ) {
        super(context,"booksDB",null,1);
    }
    final String LOG_TAG = "myLogs";
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG,"-----onCreate database-----");
        db.execSQL("create table booktable(" +"id integer primary key autoincrement," +"name text,"+"autor text"+","+"choice bool"+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
