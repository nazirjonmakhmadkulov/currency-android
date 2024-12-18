package com.developer.currency.domain

import android.content.SharedPreferences
import com.developer.currency.core.database.boolean
import com.developer.currency.core.database.string
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppSettings : KoinComponent {
    private val preference: SharedPreferences by inject()
    var theme by preference.boolean()
    var language by preference.string("ru")
}