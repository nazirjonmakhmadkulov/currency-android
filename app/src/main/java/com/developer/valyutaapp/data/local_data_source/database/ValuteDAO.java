package com.developer.valyutaapp.data.local_data_source.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.developer.valyutaapp.model.Valute;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ValuteDAO {

    @Query("SELECT * FROM valute WHERE id=:valId")
    Flowable<Valute> getValuteById(int valId);

    @Query("SELECT count(*) FROM valute")
    Single<Integer> getValuteCount();

    @Query("SELECT * FROM valute ORDER BY sortValute DESC")
    Flowable<List<Valute>> getAllValutes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertValute(List<Valute> valutes);

    @Update
    void updateValute(List<Valute> valutes);

    @Update
    void updateValuteStatus(Valute valute);

    @Delete
    void deleteValute(Valute... valutes);

    @Query("DELETE FROM valute")
    void deleteAllValutes();

}
