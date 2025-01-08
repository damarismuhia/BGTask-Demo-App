package com.dmuhia.bgtaskdemoapp.data.worker

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmuhia.bgtaskdemoapp.data.local.QuoteEntity
import com.dmuhia.bgtaskdemoapp.utils.QUOTE_TAG
import com.dmuhia.bgtaskdemoapp.utils.createNotification
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private  val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val gson:Gson)
    :CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result {
        val quoteJson = workerParameters.inputData.getString(QUOTE_TAG)
        val quote = gson.fromJson(quoteJson, QuoteEntity::class.java)
        val notification = createNotification(context,"Quote",quote.quote.plus(quote.author))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(context,POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                NotificationManagerCompat.from(context).notify(0,notification)
            }else{
                return Result.failure()
            }
        } else {
            NotificationManagerCompat.from(context).notify(0,notification)
        }
        return Result.success()
    }
}