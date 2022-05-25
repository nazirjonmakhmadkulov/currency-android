package com.developer.valyutaapp.data.local

import androidx.room.*
import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.entities.Valute
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getAllHistories(): Flow<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

    @Update
    suspend fun updateHistory(history: History)

    @Query("DELETE FROM history WHERE id=:valId")
    suspend fun deleteHistory(valId: Int)

    @Query("DELETE FROM history")
    suspend fun deleteAllHistories()
}