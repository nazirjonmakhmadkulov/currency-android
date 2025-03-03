package com.developer.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.developer.common.PATH_EXP
import com.developer.common.Utils
import com.developer.domain.model.Currency
import com.developer.domain.repository.CurrencyRemoteRepository
import com.developer.notification.Notification
import com.developer.sync.R
import com.developer.sync.initializers.SyncConstraints
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@HiltWorker
internal class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val currencyRemoteRepository: CurrencyRemoteRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        val result = currencyRemoteRepository.getCurrencies(Utils.getDate(), PATH_EXP)
        if (result.isNotEmpty()) {
            showNotify(result)
            Result.success()
        } else {
            Result.retry()
        }
    }

    companion object {
        private const val DURATION: Long = 1000
        private const val REPEAT_INTERVAL: Long = 12

        fun startUpSyncWork() = PeriodicWorkRequestBuilder<DelegatingWorker>(REPEAT_INTERVAL, TimeUnit.HOURS)
            .setInitialDelay(DURATION, TimeUnit.MILLISECONDS)
            .setConstraints(SyncConstraints)
            .setInputData(SyncWorker::class.delegatedData())
            .build()
    }

    private suspend fun showNotify(data: List<Currency>) = withContext(Dispatchers.Main) {
        val info = data.joinToString(" ") { "${it.charCode} ${it.value}" }
        Notification.showNotification(appContext, appContext.getString(R.string.app_name), info)
    }
}
