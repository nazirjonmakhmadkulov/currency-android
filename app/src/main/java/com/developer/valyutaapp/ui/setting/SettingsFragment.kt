package com.developer.valyutaapp.ui.setting

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.FAVORITE_VALUTE
import com.developer.valyutaapp.core.database.SharedPreference
import org.koin.android.ext.android.inject

class SettingsFragment : PreferenceFragmentCompat() {

    private val prefs: SharedPreference by inject()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val autoUpdate: SwitchPreferenceCompat = findPreference("auto")!!
        val favorite: Preference = findPreference("favorite")!!

        if (prefs.getAutoUpdate() == "1") {
            autoUpdate.isChecked = true
        } else if (prefs.getAutoUpdate() == "0") {
            autoUpdate.isChecked = false
        }

        favorite.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val action =
                SettingsFragmentDirections.actionNavigationSettingsToEditFragment(FAVORITE_VALUTE)
            findNavController().navigate(action)
            true
        }

        autoUpdate.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            if (autoUpdate.isChecked) {
                autoUpdate.isChecked = true
                prefs.saveAutoUpdate("1")
                //startService(Intent(this@SettingActivity, AutoService::class.java))
            } else {
                autoUpdate.isChecked = false
                prefs.saveAutoUpdate("0")
                //stopService(Intent(this@SettingActivity, AutoService::class.java))
            }
            false
        }
    }
}