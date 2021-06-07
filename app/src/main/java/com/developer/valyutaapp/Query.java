package com.developer.valyutaapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 27.06.2018.
 */

public class Query {

    Context context;
    DatabaseAdapter adapter;
    SQLiteDatabase sqLiteDatabase;
    public Query(Context context) {
        this.context = context;
    }

    public List<Model> getListTJS(){
        Model model = null;
        List<Model> currencyList = new ArrayList<>();
        adapter.open();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE + " WHERE " + DatabaseHelper.COLUMN_CHARCODE + " = TJS ORDER BY " +DatabaseHelper.COLUMN_ID + " DESC LIMIT 2", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
           // model = new Model();
            currencyList.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        adapter.open();
        return currencyList;
    }


    public Model method(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ DatabaseHelper.TABLE + "  order by _id desc limit 1", null);
        Model user = null;
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String dates = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            int id_val = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VAL));
            String charcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHARCODE));
            int nominal = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMINAL));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            String value = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VALUE));
            int selec = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKED));
            user = new Model(id, id_val, charcode, nominal, name, value, dates, selec);
        }
        cursor.close();
        return  user;
    }

    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }
}
