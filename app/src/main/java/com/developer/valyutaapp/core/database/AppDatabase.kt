package com.developer.valyutaapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developer.valyutaapp.data.local.HistoryDao
import com.developer.valyutaapp.data.local.ValuteDao
import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.entities.Valute

@Database(entities = [Valute::class, History::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun valuteDao(): ValuteDao
    abstract fun historyDao(): HistoryDao
}