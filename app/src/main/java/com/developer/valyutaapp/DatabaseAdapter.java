package com.developer.valyutaapp;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 13.06.2018.
 */

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_VAL, DatabaseHelper.COLUMN_CHARCODE,
        DatabaseHelper.COLUMN_NOMINAL, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_VALUE, DatabaseHelper.COLUMN_DATE};
        //String columns = "SELECT * FROM " + DatabaseHelper.TABLE + " ORDER BY id DESC limit 36";
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<Model> getUsers(){
        ArrayList<Model> users = new ArrayList<>();
        String columns = "SELECT * FROM " + DatabaseHelper.TABLE + " WHERE _date = '" + getDate().get_dates().toString() + "' ORDER BY " + DatabaseHelper.COLUMN_CHECKED;
        Cursor cursor = database.rawQuery(columns, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                int id_val = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VAL));
                String charcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHARCODE));
                int nominal = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMINAL));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                String value = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VALUE));
                String dates = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
                int selected = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKED));
                users.add(new Model(id, id_val, charcode, nominal, name, value, dates, selected));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  users;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public Model getUser(long id){
        Model user = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            int id_val = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VAL));
            String charcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHARCODE));
            int nominal = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMINAL));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            String value = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VALUE));
            String dates = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            int selected = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKED));
            user = new Model(id, id_val, charcode, nominal, name, value, dates, selected);
        }
        cursor.close();
        return  user;
    }
    public Model getDataDate(){
        database = dbHelper.getWritableDatabase();
        Model user = null;
        String query = "SELECT * FROM " + DatabaseHelper.TABLE + " ORDER BY _id DESC limit 1";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String dates = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            int id_val = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VAL));
            String charcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHARCODE));
            int nominal = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMINAL));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            String value = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VALUE));
            int selected = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKED));
            user = new Model(id, id_val, charcode, nominal, name, value, dates, selected);
        }
        cursor.close();
        return  user;
    }
    public boolean getDataNULL(){
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE, null);
            if (cursor.getCount() == 0) {
                return true;
            }else {
                return false;
            }
        } catch (SQLiteException Exp) {
            return false;
        } finally {
            if (database != null) database.close();
            if (cursor != null) cursor.close();
        }
    }

    public ModelDate getDate(){
        database = dbHelper.getWritableDatabase();
        ModelDate modelDate = null;
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_DATE + " ORDER BY _id DESC limit 1";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String dates = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            modelDate = new ModelDate(id, dates);
        }
        cursor.close();
        return modelDate;

    }
    public void selected(long id){
        database = dbHelper.getWritableDatabase();
        database.execSQL("UPDATE "+DatabaseHelper.TABLE+" SET checked = '1' WHERE _id=?", new String[]{ String.valueOf(id)});
        database.close();
    }
    public void unselected(long id){
        database = dbHelper.getWritableDatabase();
        database.execSQL("UPDATE "+DatabaseHelper.TABLE+" SET checked = '2' WHERE _id=?", new String[]{ String.valueOf(id)});
        database.close();
    }
}
