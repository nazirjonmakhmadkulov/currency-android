package com.developer.valyutaapp.di

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.di.modules.apiModules
import com.developer.valyutaapp.di.modules.databaseModule
import com.developer.valyutaapp.di.modules.dispatcherProviders
import com.developer.valyutaapp.di.modules.netModule
import com.developer.valyutaapp.di.modules.remoteDataSources
import com.developer.valyutaapp.di.modules.repositoryModule
import com.developer.valyutaapp.di.modules.sharedPreference
import com.developer.valyutaapp.di.modules.useCasesModule
import com.developer.valyutaapp.di.modules.viewModelModule
import com.developer.valyutaapp.utils.LocaleManager
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

        if (prefs.getTheme()) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}