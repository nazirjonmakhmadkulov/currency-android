package com.developer.valyutaapp.data.local

import androidx.room.*
import com.developer.valyutaapp.domain.entities.Valute
import kotlinx.coroutines.flow.Flow

@Dao
interface ValuteDao {

    @Query("SELECT * FROM valute WHERE id=:valId")
    suspend fun getValuteById(valId: Int): Valute

    @Query("SELECT count(*) FROM valute")
    suspend fun getValuteCount(): Int

    @Query("SELECT * FROM valute ORDER BY sortValute DESC")
    fun getAllValutes(): Flow<List<Valute>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertValute(valutes: List<Valute>)

    @Update
    suspend fun updateValute(valute: Valute)

    @Delete
    suspend fun deleteValute(vararg valutes: Valute)

    @Query("DELETE FROM valute")
    suspend fun deleteAllValutes()
}