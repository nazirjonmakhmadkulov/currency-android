package com.developer.sync.initializers

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.WorkManager
import com.developer.sync.workers.SyncWorker

object Sync {
    fun initialize(context: Context) {
        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork(
                SYNC_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                SyncWorker.startUpSyncWork(),
            )
        }
    }

    fun workerCancel(context: Context) {
        WorkManager.getInstance(context)
            .cancelUniqueWork(SYNC_WORK_NAME)
    }
}

internal const val SYNC_WORK_NAME = "SyncWorkCurrency"

val SyncConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .setRequiresCharging(false)
        .setRequiresBatteryNotLow(true)
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
