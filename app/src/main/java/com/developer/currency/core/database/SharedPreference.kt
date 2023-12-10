package com.developer.currency.core.database

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Context.getSharedPreference(name: String? = "currency"): SharedPreferences =
    this.getSharedPreferences(name, Context.MODE_PRIVATE)

fun SharedPreferences.string(default: String? = null, key: String? = null) = object : ReadWriteProperty<Any, String?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String? = getString(key ?: property.name, default)
    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) = edit {
        when (value) {
            null -> remove(key ?: property.name)
            else -> putString(key ?: property.name, value)
        }
    }
}

fun SharedPreferences.boolean(default: Boolean = false, key: String? = null) =
    object : ReadWriteProperty<Any, Boolean> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean =
            getBoolean(key ?: property.name, default)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) =
            edit { putBoolean(key ?: property.name, value) }
    }