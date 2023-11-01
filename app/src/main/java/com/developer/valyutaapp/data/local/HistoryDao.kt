package com.developer.valyutaapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.developer.valyutaapp.domain.entities.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history WHERE valId = :valId order by dates DESC LIMIT :limit")
    fun getAllHistories(valId: Int, limit: Int): Flow<List<History>>

    @Query("SELECT EXISTS(SELECT * FROM history WHERE dates = :dates AND valId = :id)")
    suspend fun getValuteExist(dates: String, id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

    @Update
    suspend fun updateHistory(history: History)

    @Query("DELETE FROM history WHERE dates < datetime('now',  '-365 day') AND valId = :id")
    suspend fun deleteHistory(id: Int)

    @Query("DELETE FROM history")
    suspend fun deleteAllHistories()
}