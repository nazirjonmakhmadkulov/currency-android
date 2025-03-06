package com.developer.currency.di

import com.developer.common.AppVersion
import com.developer.common.MarketUrl
import com.developer.currency.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @AppVersion
    fun provideAppString(): String = BuildConfig.VERSION_NAME

    @Provides
    @MarketUrl
    fun provideMarketUrl(): String = BuildConfig.MARKET_URL
}