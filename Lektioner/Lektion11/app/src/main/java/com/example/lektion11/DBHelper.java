package com.example.lektion11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

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

    public boolean addValue(String inTitle, String inContent) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_VALUE_TITLE, inTitle);
        contentValues.put(COLUMN_NAME_VALUE_CONTENT, inContent);

        long value_id = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if (value_id == -1) {
            Log.d("Tester", "addValue: Something didn't work");
            return false;
        } else {
            Log.d("Tester", "addValue: Assimilation successful");
            return true;
        }

    }

    public void getTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String selectAll = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    int value_id = cursor.getInt(0);
                    String value_title = cursor.getString(1);
                    String value_content = cursor.getString(2);
                    Log.d("tester", "getTable: id: " + value_id + " value: " + value_title + " content: " + value_content);
                } catch (Exception e) {
                    Log.wtf("ERROR", "getTable: ", e);
                }
            } while (cursor.moveToNext());
            cursor.close();


        }
    }

}
