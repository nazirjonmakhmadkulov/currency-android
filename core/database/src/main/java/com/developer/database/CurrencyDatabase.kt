package com.developer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developer.database.dao.HistoryDao
import com.developer.database.dao.CurrencyDao
import com.developer.database.entities.HistoryEntity
import com.developer.database.entities.CurrencyEntity

@Database(
    entities = [CurrencyEntity::class, HistoryEntity::class],
    version = 2,
    exportSchema = false
)
internal abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun valuteDao(): CurrencyDao
    abstract fun historyDao(): HistoryDao
}
