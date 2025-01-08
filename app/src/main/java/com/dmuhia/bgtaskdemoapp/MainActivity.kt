package com.dmuhia.bgtaskdemoapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dmuhia.bgtaskdemoapp.ui.theme.BgTaskDemoAppTheme
import com.dmuhia.bgtaskdemoapp.data.worker.CustomWorker
import com.dmuhia.bgtaskdemoapp.presentation.screens.QuoteScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BgTaskDemoAppTheme {
                QuoteScreen()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Greeting(context: Context, modifier: Modifier = Modifier) {
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
            val workRequest = PeriodicWorkRequestBuilder<CustomWorker>(repeatInterval = 15,
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
            WorkManager.getInstance(context).enqueue(workRequest)

        } else {
            permission.launchPermissionRequest()
        }
    }
}

