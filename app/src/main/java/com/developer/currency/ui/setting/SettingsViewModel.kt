package com.developer.currency.ui.setting

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.developer.currency.core.database.boolean
import com.developer.currency.core.database.string
import com.developer.currency.domain.AppSettings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel : ViewModel(), KoinComponent {
    private val preference: SharedPreferences by inject()
    val appSettings: AppSettings by inject()
    var authUpdate by preference.string()
    var theme by preference.boolean()
}