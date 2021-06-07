package com.developer.valyutaapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by User on 21.06.2018.
 */

public class DBSimple {

    public static long insertRow (Context context, ContentValues cv) {
        DatabaseHelper databaseHelper;
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database;
        database = databaseHelper.getWritableDatabase();
        long index;
        index = database.insert(DatabaseHelper.TABLE , null, cv);
        database.close();
        return index;
    }

    public static long updateRow (Context context, ContentValues cv) {
        DatabaseHelper databaseHelper;
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database;
        database = databaseHelper.getWritableDatabase();
        long index;
        index = database.update(DatabaseHelper.TABLE , cv, null, null);
        database.close();
        return index;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static long deleteRow(Context context){
        DatabaseHelper databaseHelper;
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database;
        database = databaseHelper.getWritableDatabase();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String datef = df.format(Calendar.getInstance().getTime());
        long index;
        String whereClause = "_date = ?";
        String[] whereArgs = new String[]{String.valueOf(datef)};
        index = database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
        database.close();
        Log.d("sending request", "delete" + index);
        return index;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static long deleteDate(Context context){
        DatabaseHelper databaseHelper;
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database;
        database = databaseHelper.getWritableDatabase();
        @SuppressLint({"NewApi", "LocalSuppress"}) DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String datef = df.format(Calendar.getInstance().getTime());
        long index;
        String whereClause = "_date = ?";
        String[] whereArgs = new String[]{String.valueOf(datef)};
        index = database.delete(DatabaseHelper.TABLE_DATE, whereClause, whereArgs);
        database.close();
        Log.d("sending request", "delete" + index);
        return index;
    }

    public static void deleteTable(Context context){
        DatabaseHelper databaseHelper;
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE " + DatabaseHelper.TABLE);
    }
}