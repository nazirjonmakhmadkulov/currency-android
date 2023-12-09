package com.developer.currency.ui.setting

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.developer.currency.core.database.boolean
import com.developer.currency.core.database.string
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel : ViewModel(), KoinComponent {
    private val preference: SharedPreferences by inject()
    var language by preference.string("ru")
    var authUpdate by preference.string()
    var theme by preference.boolean()
}