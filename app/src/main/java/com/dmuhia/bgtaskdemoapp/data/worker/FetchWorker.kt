package com.dmuhia.bgtaskdemoapp.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.dmuhia.bgtaskdemoapp.data.local.QuoteDao
import com.dmuhia.bgtaskdemoapp.data.network.ApiService
import com.dmuhia.bgtaskdemoapp.data.network.repository.RemoteRepository
import com.dmuhia.bgtaskdemoapp.utils.ONE_TIME_WORK_REQUEST
import com.dmuhia.bgtaskdemoapp.utils.QUOTE_TAG
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.io.IOException


@HiltWorker
class FetchWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val repo: RemoteRepository,
    private val gson: Gson
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
           val quote = repo.getQuotes(ONE_TIME_WORK_REQUEST)
            Timber.e("Quote is: ${gson.toJson(quote)}")
            val data = Data.Builder()
                .putString(QUOTE_TAG, gson.toJson(quote)).build()
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