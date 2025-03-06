package com.developer.currency

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.developer.domain.AppSettings
import com.developer.domain.LocaleManager
import com.google.firebase.FirebaseApp
import com.yandex.mobile.ads.common.MobileAds
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class CurrencyApp : Application() {

    companion object {
        lateinit var localeManager: LocaleManager
        private const val YANDEX_MOBILE_ADS_TAG = "YandexMobileAds"
    }

    @Inject
    lateinit var appSettings: AppSettings

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager.setLocale(this)
    }

    override fun onCreate() {
        super.onCreate()
        localeManager = LocaleManager(appSettings)
        FirebaseApp.initializeApp(this@CurrencyApp)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        MobileAds.initialize(this) {
            Timber.tag(YANDEX_MOBILE_ADS_TAG).d("SDK initialized")
        }

        if (appSettings.theme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}