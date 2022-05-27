package com.developer.valyutaapp.data.local

import androidx.room.*
import com.developer.valyutaapp.domain.entities.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history WHERE valId = :id")
    fun getAllHistories(id: Int): Flow<List<History>>

    @Query("SELECT EXISTS(SELECT * FROM history WHERE dates = :dates AND valId = :id)")
    suspend fun getValuteExist(dates: String, id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

    @Update
    suspend fun updateHistory(history: History)

    @Query("DELETE FROM history WHERE dates < datetime('now',  '-30 day') AND valId = :id")
    suspend fun deleteHistory(id: Int)

    @Query("DELETE FROM history")
    suspend fun deleteAllHistories()
}