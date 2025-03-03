package com.developer.data.di

import com.developer.data.datasource.CurrencyLocalRepositoryImpl
import com.developer.data.datasource.CurrencyRemoteRepositoryImpl
import com.developer.data.datasource.HistoryLocalRepositoryImpl
import com.developer.domain.repository.CurrencyLocalRepository
import com.developer.domain.repository.CurrencyRemoteRepository
import com.developer.domain.repository.HistoryLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    fun bindHistoryLocalRepository(impl: HistoryLocalRepositoryImpl): HistoryLocalRepository

    @Binds
    fun bindCurrencyLocalRepository(impl: CurrencyLocalRepositoryImpl): CurrencyLocalRepository

    @Binds
    fun bindCurrencyRemoteRepository(impl: CurrencyRemoteRepositoryImpl): CurrencyRemoteRepository
}