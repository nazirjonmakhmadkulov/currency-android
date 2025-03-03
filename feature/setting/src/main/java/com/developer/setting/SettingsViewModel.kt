package com.developer.setting

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.developer.common.AppVersion
import com.developer.datastore.boolean
import com.developer.datastore.string
import com.developer.domain.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preference: SharedPreferences,
    private val appSettings: AppSettings,
    @AppVersion val appVersion: String
) : ViewModel() {
    var authUpdate by preference.string()
    var theme by preference.boolean()

    fun language(charSequence: String) {
        appSettings.language = charSequence
    }
}