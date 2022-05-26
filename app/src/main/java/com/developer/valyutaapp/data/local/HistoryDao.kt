package com.developer.valyutaapp.data.local

import androidx.room.*
import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.entities.Valute
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getAllHistories(): Flow<List<History>>

    @Query("SELECT EXISTS(SELECT * FROM valute WHERE dates = :dates)")
    suspend fun getValuteExist(dates: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

    @Update
    suspend fun updateHistory(history: History)

    @Query("DELETE FROM history WHERE dates = :dates")
    suspend fun deleteHistory(dates: String)

    @Query("DELETE FROM history")
    suspend fun deleteAllHistories()
}