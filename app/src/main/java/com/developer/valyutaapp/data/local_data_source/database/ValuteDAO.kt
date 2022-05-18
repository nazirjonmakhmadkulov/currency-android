package com.developer.valyutaapp.data.local_data_source.database

import androidx.room.*
import io.reactivex.Flowable
import com.developer.valyutaapp.model.Valute
import io.reactivex.Single

@Dao
interface ValuteDAO {
    @Query("SELECT * FROM valute WHERE id=:valId")
    fun getValuteById(valId: Int): Flowable<Valute?>?

    @get:Query("SELECT count(*) FROM valute")
    val valuteCount: Single<Int?>?

    @get:Query("SELECT * FROM valute ORDER BY sortValute DESC")
    val allValutes: Flowable<List<Valute?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertValute(valutes: List<Valute?>?)

    @Update
    fun updateValute(valutes: List<Valute?>?)

    @Update
    fun updateValuteStatus(valute: Valute?)

    @Delete
    fun deleteValute(vararg valutes: Valute?)

    @Query("DELETE FROM valute")
    fun deleteAllValutes()
}