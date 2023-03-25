package com.developer.valyutaapp.core.network

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkStatusViewModel : ViewModel(), KoinComponent {
    val context by lazy { getKoin().get<Context>() }
    private val networkStatusTracker = NetworkStatusTracker(context)
    @OptIn(FlowPreview::class)
    val state = networkStatusTracker.networkStatus.map(
        onAvailable = { NetworkStatus.Available },
        onUnavailable = { NetworkStatus.Unavailable },
    ).flowOn(Dispatchers.IO)
}