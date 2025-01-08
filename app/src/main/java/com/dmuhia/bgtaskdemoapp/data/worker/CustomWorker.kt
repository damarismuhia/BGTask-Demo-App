package com.dmuhia.bgtaskdemoapp.data.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.dmuhia.bgtaskdemoapp.data.network.ApiService
import com.dmuhia.bgtaskdemoapp.utils.createNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.net.UnknownHostException

@HiltWorker
class CustomWorker @AssistedInject constructor(
    private val api: ApiService,
    @Assisted val context: Context,
    @Assisted val parameters: WorkerParameters):CoroutineWorker(context, parameters) {
    override suspend fun doWork(): Result {
        return try {
            val postResponse = api.getQuotes()
             if (postResponse.isSuccessful) {
                 setForeground(getForegroundInfo(applicationContext))
              //  Timber.e("CustomWorker Success:: ${postResponse.body()?.quotes} ")
                Result.success()
            } else {
                Timber.e("CustomWorker Retrying...")
                Result.retry()
            }
        }catch (e: Exception) {

            if (e is UnknownHostException) {
                Timber.e("CustomWorker Retrying...")
                Result.retry()
            } else {
                Timber.e("CustomWorker Error...${e.message}")
                Result.failure(Data.Builder().putString("error", e.message).build())
            }

        }

    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return getForegroundInfo(applicationContext)
    }
    private fun getForegroundInfo(context: Context): ForegroundInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(0,
                createNotification(context,"",""))
        } else {
            ForegroundInfo(0,
                createNotification(context,"",""))
        }

    }

}