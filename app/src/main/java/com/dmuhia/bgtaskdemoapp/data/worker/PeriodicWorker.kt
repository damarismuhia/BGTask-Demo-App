package com.dmuhia.bgtaskdemoapp.data.worker

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.dmuhia.bgtaskdemoapp.data.local.QuoteDao
import com.dmuhia.bgtaskdemoapp.data.network.ApiService
import com.dmuhia.bgtaskdemoapp.data.network.repository.RemoteRepository
import com.dmuhia.bgtaskdemoapp.utils.ONE_TIME_WORK_REQUEST
import com.dmuhia.bgtaskdemoapp.utils.PERIODIC_WORK_REQUEST
import com.dmuhia.bgtaskdemoapp.utils.QUOTE_TAG
import com.dmuhia.bgtaskdemoapp.utils.createNotification
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.io.IOException


@HiltWorker
class PeriodicWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val repo: RemoteRepository,
    private val gson: Gson
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
           val quote = repo.getQuotes(PERIODIC_WORK_REQUEST)
            val data = Data.Builder()
                .putString(QUOTE_TAG, gson.toJson(quote)).build()

            val notification = createNotification(context,"Quote", quote.quote.plus("-${quote.author}"))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                if (ContextCompat.checkSelfPermission(context,
                        POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED) {
                    NotificationManagerCompat.from(context).notify(0,notification)
                }else{
                    return Result.failure()
                }
            } else {
                NotificationManagerCompat.from(context).notify(0,notification)
            }
            Timber.e("${this.javaClass.simpleName}-->${Result.success(data)}")
            Result.success(data)

        } catch (e: IOException) {
            Timber.e("Retrying on $this... ")
            Result.retry()
        }catch (e: Exception) {
            Timber.e("ERROR on ${e.message}... ")
            Result.failure(workDataOf("error" to e.message))
        }
    }
}