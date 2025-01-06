package com.dmuhia.bgtaskdemoapp.data.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.dmuhia.bgtaskdemoapp.utils.Constants.Companion.KEY_IMAGE_URI
import okhttp3.Response

/**Implement a worker that uploads an Image*/
class UploadWorker(private val context: Context,
                   private val params: WorkerParameters):Worker(context,params) {
    /**This fun will be executed when we want to run our worker*/
    override fun doWork(): Result {
        try {
            //Get the input - Both inputs and outputs are represented as key, value pairs.
            val imageUriInput = inputData.getString(KEY_IMAGE_URI)

            //Do the work
            val response = upload(imageUriInput)

            // create the output of the work
            val imageResponse = response.body
            val imageLink = imageResponse
            val outPutData = workDataOf(KEY_IMAGE_URI to imageLink)


            return Result.success(outPutData)

        }catch (e: Exception) {
           return Result.failure()
        }

    }

    private fun upload(imageUri: String?): Response {
        TODO()

    }

}