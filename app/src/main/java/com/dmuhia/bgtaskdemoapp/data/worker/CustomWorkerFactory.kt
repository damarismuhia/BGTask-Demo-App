package com.dmuhia.bgtaskdemoapp.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.dmuhia.bgtaskdemoapp.data.network.ApiService
import java.util.concurrent.Executor
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(private val api: ApiService): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName) {
            CustomWorker::class.java.name -> {
                CustomWorker(api,appContext,workerParameters)
            } else ->{
                null // return null, so that the base class can delegate to the default workerFactory
            }
        }
    }

}
