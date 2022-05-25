package com.developer.valyutaapp.data.local

import androidx.room.*
import com.developer.valyutaapp.domain.entities.Valute
import kotlinx.coroutines.flow.Flow

@Dao
interface ValuteDao {

    @Query("SELECT * FROM valute WHERE id=:valId")
    suspend fun getValuteById(valId: Int): Valute

    @Query("SELECT EXISTS(SELECT * FROM valute WHERE dates = :dates)")
    suspend fun getValuteExist(dates: String): Boolean

    @Query("SELECT * FROM valute")
    fun getAllValutes(): Flow<List<Valute>>

    @Query("SELECT * FROM valute WHERE favoritesConverter = 1")
    fun getAllConverterValutes(): Flow<List<Valute>>

    @Query("SELECT * FROM valute WHERE favoritesValute = 1")
    fun getAllFavoritesValutes(): Flow<List<Valute>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertValute(valute: Valute)

    @Update
    suspend fun updateValute(valutes: Valute)

    @Query("UPDATE valute SET charCode=:code, nominal=:nomi, name=:name, value=:value, dates=:dates WHERE valId=:id")
    suspend fun updateValuteFromRemote(code: String, nomi: Int, name: String, value: String, dates: String, id: Int)

    @Delete
    suspend fun deleteValute(valutes: Valute)

    @Query("DELETE FROM valute")
    suspend fun deleteAllValutes()
}