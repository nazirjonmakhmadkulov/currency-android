package com.developer.database.di

import com.developer.database.CurrencyDatabase
import com.developer.database.dao.HistoryDao
import com.developer.database.dao.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesHistoryDao(database: CurrencyDatabase): HistoryDao = database.historyDao()

    @Provides
    fun providesValuteDao(database: CurrencyDatabase): CurrencyDao = database.valuteDao()
}
