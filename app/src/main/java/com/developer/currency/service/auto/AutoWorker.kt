package com.developer.currency.service.auto

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.developer.currency.R
import com.developer.currency.core.common.PATH_EXP
import com.developer.currency.core.common.Result.Error
import com.developer.currency.core.common.Result.Loading
import com.developer.currency.core.common.Result.Success
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.repository.ValuteRemoteRepository
import com.developer.currency.utils.Notification
import com.developer.currency.utils.Utils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AutoWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams),
    KoinComponent {

    companion object {
        const val WORK_RESULT = "work_result"
    }

    private val valuteRemoteRepository: ValuteRemoteRepository by inject()

    override suspend fun doWork(): Result {
        val outputData = Data.Builder().putString(WORK_RESULT, "Finished").build()
        return when (val result = valuteRemoteRepository.getAllValutes(Utils.getDate(), PATH_EXP)) {
            is Loading -> Result.success()
            is Success -> {
                showNotify(result.data.valute)
                Result.success(outputData)
            }
            is Error -> Result.failure(outputData)
        }
    }

    private fun showNotify(data: List<Valute>) {
        var info = ""
        data.forEach {
            if (it.valId == 840 || it.valId == 978 || it.valId == 810) {
                info += "${it.charCode} ${it.value}  "
            }
        }
        Notification.showNotification(applicationContext, applicationContext.getString(R.string.app_name), info)
    }
}