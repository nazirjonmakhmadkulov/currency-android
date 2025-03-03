package com.developer.domain

import android.content.SharedPreferences
import com.developer.datastore.boolean
import com.developer.datastore.string
import javax.inject.Inject

class AppSettings @Inject constructor(
    private val preference: SharedPreferences
) {
    var theme by  preference.boolean()
    var language by preference.string("ru")
}