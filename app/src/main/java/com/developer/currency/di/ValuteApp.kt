package com.developer.currency.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.developer.currency.BuildConfig
import com.developer.currency.core.database.boolean
import com.developer.currency.di.modules.apiModules
import com.developer.currency.di.modules.databaseModule
import com.developer.currency.di.modules.dispatcherProviders
import com.developer.currency.di.modules.netModule
import com.developer.currency.di.modules.remoteDataSources
import com.developer.currency.di.modules.repositoryModule
import com.developer.currency.di.modules.sharedPreference
import com.developer.currency.di.modules.useCasesModule
import com.developer.currency.di.modules.viewModelModule
import com.developer.currency.core.utils.LocaleManager
import com.developer.currency.domain.AppSettings
import com.google.firebase.FirebaseApp
import com.yandex.mobile.ads.common.MobileAds
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class ValuteApp : Application() {

    companion object {
        lateinit var localeManager: LocaleManager
        private const val YANDEX_MOBILE_ADS_TAG = "YandexMobileAds"
    }

    private val appSettings by inject<AppSettings>()

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
        FirebaseApp.initializeApp(this@ValuteApp)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@ValuteApp.applicationContext)
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    useCasesModule,
                    viewModelModule,
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

        MobileAds.initialize(this) {
            Timber.tag(YANDEX_MOBILE_ADS_TAG).d("SDK initialized")
        }

        if (appSettings.theme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}