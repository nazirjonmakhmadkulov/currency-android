package com.developer.currency.ui.setting

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
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
import com.developer.currency.BuildConfig.MARKET_URL
import com.developer.currency.R
import com.developer.currency.core.common.FAVORITE_VALUTE
import com.developer.currency.databinding.FragmentSettingBinding
import com.developer.currency.di.ValuteApp
import com.developer.currency.service.auto.AutoWorker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SettingsFragment : PreferenceFragmentCompat() {
    private val viewBinding by viewBinding(FragmentSettingBinding::bind)
    private val viewModel by viewModel<SettingsViewModel>()

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
        val app = "RuStore - https://www.rustore.ru/catalog/app/com.developer.valyutaapp" +
            "\n\nGoogle Play - https://play.google.com/store/apps/details?id=com.developer.valyutaapp" +
            "\n\nAppGallery - https://appgallery.huawei.ru/#/app/C109625991"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            action = Intent.ACTION_SEND
            type = "*/*"
            putExtra(Intent.EXTRA_TEXT, app)
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
    }

    private fun goToAppMarket() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, MARKET_URL.toUri()))
        } catch (_: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_URL)))
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val autoUpdate = findPreference<SwitchPreferenceCompat>("auto")
        val themeApp = findPreference<SwitchPreferenceCompat>("theme")
        val listPreference = findPreference<ListPreference>("key_language")
        val favorite = findPreference<Preference>("favorite")
        val widget = findPreference<Preference>("widget")

        if (viewModel.authUpdate == "1") {
            autoUpdate?.isChecked = true
        } else if (viewModel.authUpdate == "0") autoUpdate?.isChecked = false

        favorite?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val action = SettingsFragmentDirections.openEditFragment(FAVORITE_VALUTE)
            findNavController().navigate(action)
            true
        }

        widget?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val action = SettingsFragmentDirections.openWidgetFragment()
            findNavController().navigate(action)
            true
        }

        autoUpdate?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            if (autoUpdate?.isChecked == true) {
                autoUpdate.isChecked = true
                viewModel.authUpdate = "1"
                workerInit()
            } else {
                autoUpdate?.isChecked = false
                viewModel.authUpdate = "0"
                workerCancel()
            }
            false
        }

        themeApp?.isChecked = viewModel.theme
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
                viewModel.theme = true
            } else {
                themeApp?.isChecked = false
                viewModel.theme = false
            }
            false
        }

        listPreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(newValue.toString())
                // val entry = preference.entries[index]
                val charSequence = preference.entryValues[index]
                viewModel.appSettings.language = charSequence.toString()
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