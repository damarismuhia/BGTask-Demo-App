package com.dmuhia.bgtaskdemoapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.dmuhia.bgtaskdemoapp.ui.theme.BgTaskDemoAppTheme
import com.dmuhia.bgtaskdemoapp.utils.Constants
import com.dmuhia.bgtaskdemoapp.data.worker.CustomWorker
import com.dmuhia.bgtaskdemoapp.data.worker.UploadWorker
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.concurrent.TimeUnit
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var workManager: WorkManager
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workManager = WorkManager.getInstance(applicationContext)
        enableEdgeToEdge()
        setContent {
            BgTaskDemoAppTheme {
                val permission = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
               LaunchedEffect(key1 = Unit) {
                   if (permission.status.isGranted) {
                       // val imageData = workDataOf(Constants.KEY_IMAGE_URI to im)
                       // Adding Constraints
                       val constraints = Constraints.Builder()
                           .setRequiresBatteryNotLow(true)
                           .setRequiresStorageNotLow(true)
                           .setRequiredNetworkType(NetworkType.CONNECTED)
                           .build()

                       //WorkRequest - OneTime WR
                     //  val workRequest = OneTimeWorkRequestBuilder<CustomWorker>()
                       val workRequest = PeriodicWorkRequestBuilder<CustomWorker>(repeatInterval = 1,
                           TimeUnit.MINUTES)
                           .setInitialDelay(
                               10,
                               TimeUnit.MILLISECONDS
                           )
                          // .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                           //  .setInputData(outPutData)
                           // .setConstraints(constraints)
                           .setBackoffCriteria(
                               BackoffPolicy.LINEAR,
                               15,
                               TimeUnit.MILLISECONDS)
                           .build()
                       workManager.enqueue(workRequest)
                       
                   } else {
                       permission.launchPermissionRequest()
                   }
               }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BgTaskDemoAppTheme {
        Greeting("Android")
    }
}