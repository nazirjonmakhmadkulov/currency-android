package com.developer.valyutaapp;

/**
 * Created by User on 13.06.2018.
 */

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "valute.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "valutes"; // название таблицы в бд
    static final String TABLE_DATE = "dates";
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "_date";
    public static final String COLUMN_VAL = "id_val";
    public static final String COLUMN_CHARCODE = "charcode";
    public static final String COLUMN_NOMINAL = "nominal";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_CHECKED = "checked";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("create table ", TABLE);

        db.execSQL("CREATE TABLE " + TABLE + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_VAL + " INTEGER, "
                + COLUMN_CHARCODE + " TEXT, " + COLUMN_NOMINAL + " INTEGER, "
                + COLUMN_NAME + " TEXT, " + COLUMN_VALUE + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_CHECKED + " INTEGER);");
        db.execSQL("CREATE TABLE " + TABLE_DATE + "(" + COLUMN_ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATE + " TEXT);");
        // добавление начальных данных
        Log.d("insert table ", TABLE);

       // db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_VAL + ", " + COLUMN_CHARCODE  + ", " + COLUMN_NOMINAL  +", " + COLUMN_NAME  +", " + COLUMN_VALUE + ", " + COLUMN_DATE + ", " + COLUMN_CHECKED + ") VALUES (100, 'USD', 1, 'Доллар США', '9.0774', '2018.06.25', 1);");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_VAL + ", " + COLUMN_CHARCODE  + ", " + COLUMN_NOMINAL  +", " + COLUMN_NAME  +", " + COLUMN_VALUE +", " + COLUMN_DATE + ", " + COLUMN_CHECKED + ") VALUES (100, 'EUR', 1, 'ЕВРО', '10.351', '2018.06.28', 1);");
        db.execSQL("INSERT INTO "+ TABLE_DATE + " (" + COLUMN_DATE + ") VALUES ('2018.06.28');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DATE);
        onCreate(db);
    }
}