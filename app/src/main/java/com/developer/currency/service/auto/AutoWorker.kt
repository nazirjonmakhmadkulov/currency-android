package com.developer.currency.service.auto

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.developer.currency.R
import com.developer.currency.core.common.PATH_EXP
import com.developer.currency.core.dispatcher.launchIO
import com.developer.currency.core.dispatcher.withMain
import com.developer.currency.core.utils.Notification
import com.developer.currency.core.utils.Utils
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.repository.ValuteRemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class AutoWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams), KoinComponent, CoroutineScope {

    companion object {
        const val WORK_RESULT = "work_result"
    }

    private val valuteRemoteRepository: ValuteRemoteRepository by inject()

    override suspend fun doWork(): Result {
        val outputData = Data.Builder().putString(WORK_RESULT, "Finished").build()
        coroutineScope {
            launchIO(
                safeAction = {
                    val result = valuteRemoteRepository.getAllValutes(Utils.getDate(), PATH_EXP)
                    withMain { showNotify(result.valute) }
                },
                onError = Timber::e
            )
        }
        return Result.success(outputData)
    }

    private fun showNotify(data: List<Valute>) {
        val info = data.filter { it.favoritesValute == 1 }
            .joinToString(" ") { "${it.charCode} ${it.value}" }
        Notification.showNotification(applicationContext, applicationContext.getString(R.string.app_name), info)
    }
}