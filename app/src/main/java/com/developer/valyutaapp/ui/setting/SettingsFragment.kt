package com.developer.valyutaapp.ui.setting

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import androidx.work.*
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.FAVORITE_VALUTE
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.service.auto.AutoWorker
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class SettingsFragment : PreferenceFragmentCompat() {
    private val prefs: SharedPreference by inject()
    companion object {
        const val MESSAGE_STATUS = "message_status"
        const val DURATION: Long = 1000
        const val REPEAT_INTERVAL: Long = 12
        const val UNIQUE_WORK_NAME: String = "auto-update-unique"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val autoUpdate: SwitchPreferenceCompat = findPreference("auto")!!
        val themeApp: SwitchPreferenceCompat = findPreference("theme")!!
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
                workerInit()
            } else {
                autoUpdate.isChecked = false
                prefs.saveAutoUpdate("0")
                workerCancel()
            }
            false
        }

        themeApp.isChecked = prefs.getTheme()
        themeApp.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            if (themeApp.isChecked) {
                themeApp.isChecked = true
                prefs.saveTheme(theme = true)
            } else {
                themeApp.isChecked = false
                prefs.saveTheme(theme = false)
            }
            false
        }
    }

    private fun createConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .setRequiresCharging(false)
        .setRequiresBatteryNotLow(true)
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private fun createWorkRequest() =
        PeriodicWorkRequestBuilder<AutoWorker>(REPEAT_INTERVAL, TimeUnit.HOURS)
            .setConstraints(createConstraints())
            .setInitialDelay(DURATION, TimeUnit.MILLISECONDS)
            .build()

    private fun workerInit() {
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, createWorkRequest()
        )
    }

    private fun workerCancel(){
        WorkManager.getInstance(requireContext()).cancelUniqueWork(UNIQUE_WORK_NAME)
    }
}