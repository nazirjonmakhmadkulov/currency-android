package com.developer.valyutaapp.service.auto

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.domain.repository.ValuteRemoteRepository
import com.developer.valyutaapp.utils.Notification
import com.developer.valyutaapp.utils.Utils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber


class AutoWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams), KoinComponent {

    companion object {
        const val WORK_RESULT = "work_result"
    }

    private val valuteRemoteRepository: ValuteRemoteRepository by inject()

    override suspend fun doWork(): Result {
        val outputData = Data.Builder().putString(WORK_RESULT, "Finished").build()
        return when (valuteRemoteRepository.getAllValutes(Utils.getDate(), PATH_EXP)) {
            is com.developer.valyutaapp.core.common.Result.Loading -> Result.success()
            is com.developer.valyutaapp.core.common.Result.Success -> {
                Notification.showNotification(applicationContext, applicationContext.getString(R.string.app_name), applicationContext.getString(R.string.update))
                Result.success(outputData)
            }
            is com.developer.valyutaapp.core.common.Result.Error -> Result.failure(outputData)
        }
    }
}