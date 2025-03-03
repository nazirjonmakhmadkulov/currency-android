package com.developer.currency.core.network

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class NetworkStatusViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel() {
    private val networkStatusTracker = NetworkStatusTracker(context)

    @OptIn(FlowPreview::class)
    val state = this.networkStatusTracker.networkStatus.map(
        onAvailable = { NetworkStatus.Available },
        onUnavailable = { NetworkStatus.Unavailable }
    ).flowOn(Dispatchers.IO)
}