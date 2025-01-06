package com.dmuhia.bgtaskdemoapp.data.local.repository

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dmuhia.bgtaskdemoapp.data.local.QuoteDao
import com.dmuhia.bgtaskdemoapp.data.local.QuoteEntity
import com.dmuhia.bgtaskdemoapp.data.worker.FetchWorker
import com.dmuhia.bgtaskdemoapp.data.worker.PeriodicWorker
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(private val workManager: WorkManager,private val quoteDao: QuoteDao): QuoteRepository {
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    override fun getQuote() {
        val oneTimeFetchWR = OneTimeWorkRequestBuilder<FetchWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR,15,TimeUnit.SECONDS)
            .build()
        workManager.enqueue(oneTimeFetchWR)

    }

    override fun getAllQuotes(): Flow<List<QuoteEntity>> = quoteDao.getQuotesList()

    override fun setupPeriodicWorkRequest() {
        val periodicWorkRequest = PeriodicWorkRequestBuilder<PeriodicWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR,15,TimeUnit.SECONDS)
            .build()
        workManager.enqueueUniquePeriodicWork("quotes-p-work",ExistingPeriodicWorkPolicy.UPDATE,periodicWorkRequest)

    }
}