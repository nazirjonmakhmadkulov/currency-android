package com.developer.valyutaapp.ui

import androidx.appcompat.app.AppCompatActivity
import com.developer.valyutaapp.R
import android.os.Bundle
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.databinding.ActivityMainBinding
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.service.auto.AutoService
import com.developer.valyutaapp.utils.Utils
import com.developer.valyutaapp.utils.Utils.setStatusBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewBinding by viewBinding(ActivityMainBinding::bind, R.id.container)
    private val prefs: SharedPreference by inject()

    private val viewModel by viewModel<MainViewModel>()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar(window)
        if (prefs.getBool() == "1") {
            startService(Intent(this, AutoService::class.java))
        } else if (prefs.getBool() == "0") {
            stopService(Intent(this, AutoService::class.java))
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(viewBinding.toolbar)
        val bottomNavView: BottomNavigationView = viewBinding.bottomNavigation
        bottomNavView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_converter,
                R.id.navigation_valutes,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.getRemoteValutes(Utils.getDate(), PATH_EXP)
        viewModel.getRemoteValutes.observe(this) {
            subscribeValuteState(it)
        }
    }

    private fun subscribeValuteState(it: Result<ValCurs>) {
        when (it) {
            is Result.Loading -> {}
            is Result.Success -> {
            }
            is Result.Error -> {
                Log.d("Error ", it.code.toString() + " == " + it.errorMessage)
            }
        }
    }
}