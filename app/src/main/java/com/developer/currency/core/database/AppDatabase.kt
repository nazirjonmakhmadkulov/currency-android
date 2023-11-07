package com.developer.currency.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developer.currency.data.local.HistoryDao
import com.developer.currency.data.local.ValuteDao
import com.developer.currency.domain.entities.History
import com.developer.currency.domain.entities.Valute

@Database(entities = [Valute::class, History::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun valuteDao(): ValuteDao
    abstract fun historyDao(): HistoryDao
}