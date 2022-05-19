package com.developer.valyutaapp.data.local

import androidx.room.*
import com.developer.valyutaapp.domain.entities.Valute
import kotlinx.coroutines.flow.Flow

@Dao
interface ValuteDao {

    @Query("SELECT * FROM valute WHERE id=:valId")
    fun getValuteById(valId: Int): Valute

    @Query("SELECT count(*) FROM valute")
    fun getValuteCount(): Int

    @Query("SELECT * FROM valute ORDER BY sortValute DESC")
    fun getAllValutes(): Flow<List<Valute>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertValute(valutes: List<Valute>)

    @Update
    fun updateValute(valute: Valute)

    @Delete
    fun deleteValute(vararg valutes: Valute)

    @Query("DELETE FROM valute")
    fun deleteAllValutes()
}