package com.developer.valyutaapp.core.database

import androidx.room.*
import com.developer.valyutaapp.data.local.ValuteDao

@Database(entities = [ValuteDao::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val valuteDao: ValuteDao
}