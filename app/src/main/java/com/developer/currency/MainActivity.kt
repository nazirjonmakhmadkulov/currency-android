package com.developer.currency

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.common.PATH_EXP
import com.developer.common.Utils
import com.developer.common.Utils.setStatusBar
import com.developer.currency.core.network.NetworkStatus
import com.developer.currency.core.network.NetworkStatusViewModel
import com.developer.currency.databinding.ActivityMainBinding
import com.developer.designsystem.getActionBarHeight
import com.developer.designsystem.getScreenWidth
import com.developer.designsystem.getStatusBarHeight
import com.developer.designsystem.launchAndCollectIn
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.common.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewBinding by viewBinding(ActivityMainBinding::bind, R.id.container)
    private val viewModel: MainViewModel by viewModels()
    private val networkStatusViewModel: NetworkStatusViewModel by viewModels()
    private var snackBar: Snackbar? = null

    companion object {
        const val UNIT_ID1 = "R-M-2277119-1"
        const val UNIT_ID2 = "R-M-2277119-2"
    }

    private val pushNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            Timber.d("Permission POST_NOTIFICATION isGranted:$granted")
        }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { CurrencyApp.localeManager.setLocale(it) })
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
                R.id.navigation_home, R.id.navigation_currencies -> setupAds(UNIT_ID1)
                R.id.navigation_converter, R.id.navigation_settings -> setupAds(UNIT_ID2)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupAds(UNIT_ID1)
    }

    private fun setupAds(unitId: String) {
        val mBannerAdView = viewBinding.adView
        val adRequest: AdRequest = AdRequest.Builder().build()
        mBannerAdView.setAdUnitId(unitId)
        mBannerAdView.setAdSize(BannerAdSize.inlineSize(this, getScreenWidth(), 60))
        mBannerAdView.loadAd(adRequest)
    }

    private fun setupSnackBar() {
        val snackBarNetwork = Snackbar
            .make(this.viewBinding.root, getString(R.string.not_connected), Snackbar.LENGTH_INDEFINITE)
            .setBackgroundTint(ContextCompat.getColor(this@MainActivity, R.color.peach))
        val view: View = snackBarNetwork.view
        val params: CoordinatorLayout.LayoutParams = view.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.translationY = getStatusBarHeight().toFloat() + getActionBarHeight().toFloat()
        view.layoutParams = params
        snackBar = snackBarNetwork
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
        networkStatusViewModel.state.launchAndCollectIn(this) {
            when (it) {
                NetworkStatus.Available -> snackBar?.dismiss()
                NetworkStatus.Unavailable -> snackBar?.show()
            }
        }
    }
}