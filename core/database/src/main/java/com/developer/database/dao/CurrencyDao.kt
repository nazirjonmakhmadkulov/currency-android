package com.developer.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.developer.database.entities.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM valute WHERE valId=:valId")
    suspend fun getCurrencyById(valId: Int): CurrencyEntity

    @Query("SELECT EXISTS(SELECT * FROM valute WHERE valId = :valId)")
    suspend fun isCurrencyExist(valId: Int): Boolean

    @Query("SELECT * FROM valute")
    fun getCurrencies(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM valute WHERE favoritesConverter = 1")
    fun getConverterCurrencies(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM valute WHERE favoritesValute = 1")
    fun getFavoritesCurrencies(): Flow<List<CurrencyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(valute: CurrencyEntity)

    @Update
    suspend fun updateCurrency(valutes: CurrencyEntity)

    @Query("UPDATE valute SET charCode=:code, nominal=:nomi, name=:name, value=:value, dates=:dates WHERE valId=:id")
    suspend fun updateCurrency(code: String, nomi: Int, name: String, value: String, dates: String, id: Int)

    @Delete
    suspend fun deleteCurrency(valutes: CurrencyEntity)

    @Query("DELETE FROM valute")
    suspend fun deleteCurrencies()
}