package com.developer.setting

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.common.FAVORITE_VALUTE
import com.developer.domain.LocaleManager
import com.developer.setting.databinding.FragmentSettingBinding
import com.developer.sync.initializers.Sync
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    private val viewBinding by viewBinding(FragmentSettingBinding::bind)
    private val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var localeManager: LocaleManager

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        viewBinding.version.text = "Версия ${viewModel.appVersion}"
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
            startActivity(Intent(Intent.ACTION_VIEW, viewModel.marketUrl.toUri()))
        } catch (_: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.marketUrl)))
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
            val action = SettingsFragmentDirections.openPagerFragment(FAVORITE_VALUTE)
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
                Sync.initialize(requireContext())
            } else {
                autoUpdate?.isChecked = false
                viewModel.authUpdate = "0"
                Sync.workerCancel(requireContext())
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
            requireActivity().recreate()
            false
        }

        listPreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(newValue.toString())
                // val entry = preference.entries[index]
                val charSequence = preference.entryValues[index]
                viewModel.language(charSequence.toString())
                localeManager.setNewLocale(requireActivity(), charSequence.toString())
                requireActivity().recreate()
            }
            true
        }
    }
}