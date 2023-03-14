package com.developer.valyutaapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.databinding.ActivityMainBinding
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.utils.Utils
import com.developer.valyutaapp.utils.Utils.setStatusBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewBinding by viewBinding(ActivityMainBinding::bind, R.id.container)
    private val prefs: SharedPreference by inject()
    private val viewModel by viewModel<MainViewModel>()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private val pushNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            Timber.d("Permission POST_NOTIFICATION isGranted:$granted")
        }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar(window)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(viewBinding.toolbar)
        val bottomNavView: BottomNavigationView = viewBinding.bottomNavigation
        bottomNavView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                //R.id.navigation_converter,
                R.id.navigation_valutes,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        setupViewModel()
        checkStatusPostNotification()
    }


    private fun checkStatusPostNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                        == PackageManager.PERMISSION_GRANTED -> Timber.d("Permission POST_NOTIFICATION GRANTED")
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) ->
                    Timber.d("Permission POST_NOTIFICATION blocked")
                else -> pushNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun setupViewModel() {
        viewModel.getRemoteValutes(Utils.getDate(), PATH_EXP)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getRemoteValutes.collect { subscribeValuteState(it) }
            }
        }
    }

    private fun subscribeValuteState(it: Result<ValCurs>) {
        when (it) {
            is Result.Loading -> {}
            is Result.Success -> {}
            is Result.Error -> Timber.d("Error ", "${it.code} = ${it.errorMessage}")
        }
    }
}