package com.developer.valyutaapp.core.database

import androidx.room.*
import com.developer.valyutaapp.data.local.FavoriteDao
import com.developer.valyutaapp.data.local.ValuteDao
import com.developer.valyutaapp.domain.entities.Favorite
import com.developer.valyutaapp.domain.entities.Valute

@Database(entities = [Valute::class, Favorite::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun valuteDao(): ValuteDao
    abstract fun favoriteDao(): FavoriteDao
}