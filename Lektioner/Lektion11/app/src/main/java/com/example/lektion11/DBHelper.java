package com.example.lektion11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "value.db";
    public static final String TABLE_NAME = "value_table";
    public static final String COLUMN_NAME_VALUE_TITLE = "value_title";
    public static final String COLUMN_NAME_VALUE_CONTENT = "value_content";

    public DBHelper(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createQuery = "CREATE TABLE "
                + TABLE_NAME
                + " (value_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_VALUE_TITLE + " TEXT, "
                + COLUMN_NAME_VALUE_CONTENT + " TEXT);";
        Log.d("tester", "onCreate: " + createQuery);
        sqLiteDatabase.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
