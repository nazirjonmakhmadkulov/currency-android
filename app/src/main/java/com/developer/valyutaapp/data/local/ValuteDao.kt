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

    @Query("SELECT * FROM valute WHERE dates = (SELECT MAX(dates) FROM valute)")
    fun getAllValutes(): Flow<List<Valute>>

    @Query("SELECT * FROM valute INNER JOIN favorite ON valute.id = favorite.id")
    fun getAllFavoritesValutes(): Flow<List<Valute>>

    @Query("SELECT * FROM valute ORDER BY favoritesConverter DESC")
    fun getAllConverterValutes(): Flow<List<Valute>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertValute(valute: Valute)

    @Update
    suspend fun updateValute(valute: Valute)

    @Delete
    suspend fun deleteValute(valutes: Valute)

    @Query("DELETE FROM valute")
    suspend fun deleteAllValutes()
}