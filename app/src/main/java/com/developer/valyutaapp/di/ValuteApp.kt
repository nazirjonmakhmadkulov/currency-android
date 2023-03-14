package com.developer.valyutaapp.di

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.developer.valyutaapp.di.modules.*
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.utils.LocaleManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ValuteApp : Application() {
    companion object {
        lateinit var localeManager: LocaleManager
    }
    private val prefs: SharedPreference by inject()

    override fun attachBaseContext(base: Context) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(localeManager.setLocale(base))
        MultiDex.install(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager.setLocale(this)
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ValuteApp.applicationContext)
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    useCasesModule,
                    viewModelModule,
                    connectionInternet,
                    dispatcherProviders,
                    remoteDataSources,
                    repositoryModule,
                    netModule,
                    apiModules,
                    databaseModule,
                    sharedPreference
                )
            )
        }
        if (prefs.getTheme())
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}