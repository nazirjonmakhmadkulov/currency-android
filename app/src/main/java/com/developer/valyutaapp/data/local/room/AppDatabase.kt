package com.developer.valyutaapp.data.local.room

import androidx.room.*

@Database(entities = [ValuteDao::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val valuteDao: ValuteDao
}