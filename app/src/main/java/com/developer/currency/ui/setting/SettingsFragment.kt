package com.developer.currency.ui.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.currency.BuildConfig
import com.developer.currency.R
import com.developer.currency.core.common.FAVORITE_VALUTE
import com.developer.currency.core.database.SharedPreference
import com.developer.currency.databinding.FragmentSettingBinding
import com.developer.currency.di.ValuteApp
import com.developer.currency.service.auto.AutoWorker
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class SettingsFragment : PreferenceFragmentCompat() {
    private val prefs: SharedPreference by inject()

    private val viewBinding by viewBinding(FragmentSettingBinding::bind)

    companion object {
        const val DURATION: Long = 1000
        const val REPEAT_INTERVAL: Long = 12
        const val UNIQUE_WORK_NAME: String = "auto-update-unique"
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        viewBinding.version.text = "Версия ${BuildConfig.VERSION_NAME}"
    }

    private fun setupToolbar() {
        viewBinding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.share -> shareDialog()
                R.id.other -> goToAppMarket()
            }
            true
        }
    }

    private fun shareDialog() {
        val app = "https://play.google.com/store/apps/details?id=com.developer.valyutaapp"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            action = Intent.ACTION_SEND
            type = "*/*"
            putExtra(Intent.EXTRA_TEXT, app)
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
    }

    private fun goToAppMarket() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/dev?id=5402022606902660683")
            )
        )
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val autoUpdate = findPreference<SwitchPreferenceCompat>("auto")
        val themeApp = findPreference<SwitchPreferenceCompat>("theme")
        val listPreference = findPreference<ListPreference>("key_language")
        val favorite = findPreference<Preference>("favorite")
        val widget = findPreference<Preference>("widget")

        if (prefs.getAutoUpdate() == "1") {
            autoUpdate?.isChecked = true
        } else if (prefs.getAutoUpdate() == "0") autoUpdate?.isChecked = false

        favorite?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val action = SettingsFragmentDirections.actionNavigationSettingsToEditFragment(FAVORITE_VALUTE)
            findNavController().navigate(action)
            true
        }

        widget?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val action = SettingsFragmentDirections.actionNavigationSettingsToWidgetFragment()
            findNavController().navigate(action)
            true
        }

        autoUpdate?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            if (autoUpdate?.isChecked == true) {
                autoUpdate.isChecked = true
                prefs.saveAutoUpdate("1")
                workerInit()
            } else {
                autoUpdate?.isChecked = false
                prefs.saveAutoUpdate("0")
                workerCancel()
            }
            false
        }

        themeApp?.isChecked = prefs.getTheme()
        themeApp?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )

                Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            }
            if (themeApp?.isChecked == true) {
                themeApp.isChecked = true
                prefs.saveTheme(theme = true)
            } else {
                themeApp?.isChecked = false
                prefs.saveTheme(theme = false)
            }
            false
        }

        listPreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(newValue.toString())
                // val entry = preference.entries[index]
                val charSequence = preference.entryValues[index]
                prefs.saveLang(charSequence.toString())
                ValuteApp.localeManager.setNewLocale(requireActivity(), charSequence.toString())
                requireActivity().recreate()
            }
            true
        }
    }

    private fun createConstraints() =
        Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED).setRequiresCharging(false)
            .setRequiresBatteryNotLow(true).setRequiredNetworkType(NetworkType.CONNECTED).build()

    private fun createWorkRequest() =
        PeriodicWorkRequestBuilder<AutoWorker>(REPEAT_INTERVAL, TimeUnit.HOURS).setConstraints(createConstraints())
            .setInitialDelay(DURATION, TimeUnit.MILLISECONDS).build()

    private fun workerInit() {
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, createWorkRequest()
        )
    }

    private fun workerCancel() {
        WorkManager.getInstance(requireContext()).cancelUniqueWork(UNIQUE_WORK_NAME)
    }
}