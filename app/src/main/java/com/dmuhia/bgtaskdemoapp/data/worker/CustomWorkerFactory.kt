package com.dmuhia.bgtaskdemoapp.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.dmuhia.bgtaskdemoapp.data.network.repository.RemoteRepository
import com.google.gson.Gson
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(private val repo:RemoteRepository, private val gson:Gson): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName) {

            FetchWorker::class.java.name -> {
                FetchWorker(appContext,workerParameters,repo,gson)
            }
            PeriodicWorker::class.java.name -> {
                PeriodicWorker(appContext,workerParameters,repo,gson)
            } else ->{
                null // return null, so that the base class can delegate to the default workerFactory
            }
        }
    }

}
