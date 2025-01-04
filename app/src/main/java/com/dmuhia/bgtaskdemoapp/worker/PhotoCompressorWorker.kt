package com.dmuhia.bgtaskdemoapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

class PhotoCompressorWorker(private val context: Context,
                            private val params: WorkerParameters):CoroutineWorker(context,params) {
    /**This fun will be executed when we want to run our worker*/
    override suspend fun doWork(): Result {

    }

}