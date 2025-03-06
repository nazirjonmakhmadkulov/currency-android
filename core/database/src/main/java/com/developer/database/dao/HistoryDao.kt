package com.developer.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.developer.database.entities.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history WHERE valId = :valId order by dates DESC LIMIT :limit")
    fun getHistories(valId: Int, limit: Int): Flow<List<HistoryEntity>>

    @Query("SELECT EXISTS(SELECT * FROM history WHERE dates = :dates AND valId = :id)")
    suspend fun isCurrencyExist(dates: String, id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)

    @Update
    suspend fun updateHistory(history: HistoryEntity)

    @Query("DELETE FROM history WHERE dates < datetime('now',  '-365 day') AND valId = :id")
    suspend fun deleteHistory(id: Int)

    @Query("DELETE FROM history")
    suspend fun deleteHistories()
}