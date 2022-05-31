package com.developer.valyutaapp.service

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.developer.valyutaapp.ui.MainActivity
import com.developer.valyutaapp.utils.Notification


class NotifyWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    companion object{
        const val WORK_RESULT = "work_result"
    }

    override fun doWork(): Result {
        val taskData = inputData
        val taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS)

        Notification.showNotification(applicationContext,"Курс валют", taskDataString.toString())

        val outputData = Data.Builder().putString(WORK_RESULT, "Task Finished").build()

        return Result.success(outputData)
    }
}