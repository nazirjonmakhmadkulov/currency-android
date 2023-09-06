package com.developer.valyutaapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.core.network.NetworkStatus
import com.developer.valyutaapp.core.network.NetworkStatusViewModel
import com.developer.valyutaapp.databinding.ActivityMainBinding
import com.developer.valyutaapp.di.ValuteApp
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.utils.Utils
import com.developer.valyutaapp.utils.Utils.setStatusBar
import com.developer.valyutaapp.utils.getActionBarHeight
import com.developer.valyutaapp.utils.getStatusBarHeight
import com.developer.valyutaapp.utils.launchAndCollectIn
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.yandex.mobile.ads.common.AdRequest
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewBinding by viewBinding(ActivityMainBinding::bind, R.id.container)
    private val viewModel by viewModel<MainViewModel>()
    private val networkStatusViewModel by viewModel<NetworkStatusViewModel>()
    private var snackBarNetwork: Snackbar? = null

    companion object {
        const val unitId1 = "R-M-2277119-1"
        const val unitId2 = "R-M-2277119-2"
    }

    private val pushNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            Timber.d("Permission POST_NOTIFICATION isGranted:$granted")
        }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { ValuteApp.localeManager.setLocale(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar(window)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main)
        val navController = navHostFragment?.findNavController()
        val bottomNavView: BottomNavigationView = viewBinding.bottomNavigation
        navController?.let { bottomNavView.setupWithNavController(it) }
        checkStatusPostNotification()
        setupSnackBar()
        setupViewModel()

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home, R.id.navigation_valutes -> setupAds(unitId1)
                R.id.navigation_converter, R.id.navigation_settings -> setupAds(unitId2)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupAds(unitId1)
    }

    private fun setupAds(unitId: String) {
        val mBannerAdView = viewBinding.adView
        val adRequest: AdRequest = AdRequest.Builder().build()
        mBannerAdView.setAdUnitId(unitId)
        // mBannerAdView.setAdSize(AdSize.inlineSize(getScreenWidth(), 50))
        mBannerAdView.loadAd(adRequest)
    }

    private fun setupSnackBar(): Snackbar {
        val snackBarNetwork = Snackbar
            .make(this.viewBinding.root, getString(R.string.not_connected), Snackbar.LENGTH_INDEFINITE)
            .setBackgroundTint(ContextCompat.getColor(this@MainActivity, R.color.peach))
        val view: View = snackBarNetwork.view
        val params: CoordinatorLayout.LayoutParams = view.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.translationY = getStatusBarHeight().toFloat() + getActionBarHeight().toFloat()
        view.layoutParams = params
        return snackBarNetwork
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
        viewModel.getRemoteValutes.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            subscribeValuteState(it)
        }
        networkStatusViewModel.state.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            when (it) {
                NetworkStatus.Available -> snackBarNetwork?.dismiss()
                NetworkStatus.Unavailable -> snackBarNetwork?.show()
            }
        }
    }

    private fun subscribeValuteState(result: Result<ValCurs>) {
        when (result) {
            is Result.Loading -> {}
            is Result.Success -> {}
            is Result.Error -> Timber.d("Error ${result.code} ${result.message}")
        }
    }
}
