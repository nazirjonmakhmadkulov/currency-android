package com.developer.valyutaapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.coroutineScope
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
import com.developer.valyutaapp.service.NotifyWorker
import com.developer.valyutaapp.service.auto.AutoService
import com.developer.valyutaapp.utils.Utils
import com.developer.valyutaapp.utils.Utils.setStatusBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewBinding by viewBinding(ActivityMainBinding::bind, R.id.container)
    private val prefs: SharedPreference by inject()
    private val viewModel by viewModel<MainViewModel>()

    companion object {
        const val MESSAGE_STATUS = "message_status"
    }

    private lateinit var worker: WorkManager
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
        initTheme()
        worker = WorkManager.getInstance()
        worker()
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

    private fun initTheme() {
        if (prefs.getAutoUpdate() == "1") {
            startService(Intent(this, AutoService::class.java))
        } else if (prefs.getAutoUpdate() == "0") {
            stopService(Intent(this, AutoService::class.java))
        }
    }

    private fun worker() {
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "Some-Unique-Name",
                ExistingPeriodicWorkPolicy.KEEP,
                createWorkRequest()
            )

        val powerConstraints = Constraints.Builder().setRequiresCharging(true).build()
        val taskData = Data.Builder().putString(MESSAGE_STATUS, "Notification Done.").build()
        val request = OneTimeWorkRequest.Builder(NotifyWorker::class.java)
            .setConstraints(powerConstraints).setInputData(taskData).build()
        worker.getWorkInfoByIdLiveData(request.id).observe(this) { workInfo ->
            workInfo.let {
                if (it.state.isFinished) {
                    val outputData = it.outputData
                    val taskResult = outputData.getString(NotifyWorker.WORK_RESULT)
                    if (taskResult != null) {
                        //textInput.value = taskResult
                    }
                } else {
                    val workStatus = workInfo.state
                    //textInput.value = workStatus.toString()
                }
            }
        }
    }

    private fun createConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .setRequiresCharging(false)
        .setRequiresBatteryNotLow(true)
        .build()

    private fun createWorkRequest() = PeriodicWorkRequestBuilder<NotifyWorker>(24, TimeUnit.HOURS)
        .setConstraints(createConstraints())
        .setInitialDelay(5000, TimeUnit.MILLISECONDS)
        .build()

    private fun setupViewModel() {
        viewModel.getRemoteValutes(Utils.getDate(), PATH_EXP)
        lifecycle.coroutineScope.launchWhenStarted {
            viewModel.getRemoteValutes.collect { subscribeValuteState(it) }
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