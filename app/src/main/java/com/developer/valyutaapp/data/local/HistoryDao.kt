package com.developer.valyutaapp.data.local

import androidx.room.*
import com.developer.valyutaapp.domain.entities.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history WHERE valId = :valId")
    fun getAllHistories(valId: Int): Flow<List<History>>

    @Query("SELECT EXISTS(SELECT * FROM history WHERE dates = :dates AND valId = :id)")
    fun getValuteExist(dates: String, id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)

    @Update
    fun updateHistory(history: History)

    @Query("DELETE FROM history WHERE dates < datetime('now',  '-30 day') AND valId = :id")
    fun deleteHistory(id: Int)

    @Query("DELETE FROM history")
    fun deleteAllHistories()
}