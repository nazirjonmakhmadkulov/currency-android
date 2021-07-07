package com.developer.valyutaapp.data.local_data_source.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.developer.valyutaapp.model.Valute;


@Database(entities = {Valute.class}, version = ValuteDatabase.DATABASE_VERSION, exportSchema = false)
public abstract class ValuteDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dataValute";

    public abstract ValuteDAO valuteDAO();
    private static ValuteDatabase mInstance;

    public static ValuteDatabase getInstance(Context context){
        if (mInstance == null){
            mInstance = Room.databaseBuilder(context, ValuteDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return mInstance;
    }
}
