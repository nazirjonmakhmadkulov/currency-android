package com.developer.domain.di

import android.content.Context
import android.content.SharedPreferences
import com.developer.datastore.getSharedPreference
import com.developer.domain.AppSettings
import com.developer.domain.LocaleManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    @Singleton
    fun sharedPreferences(@ApplicationContext context: Context):
        SharedPreferences = context.getSharedPreference()

    @Provides
    @Singleton
    fun appSettings(sharedPreferences: SharedPreferences):
        AppSettings = AppSettings(sharedPreferences)

    @Provides
    @Singleton
    fun localeManager(settings: AppSettings): LocaleManager = LocaleManager(settings)
}