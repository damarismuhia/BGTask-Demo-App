package com.dmuhia.bgtaskdemoapp.presentation.screens

import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.WorkInfo
import com.dmuhia.bgtaskdemoapp.data.mappers.getWorkRequestType
import com.dmuhia.bgtaskdemoapp.presentation.QuoteViewModel
import com.dmuhia.bgtaskdemoapp.utils.QUOTE_TAG
import com.dmuhia.bgtaskdemoapp.utils.formatTimestampToDMY
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun QuoteScreen(){
    val viewModel = hiltViewModel<QuoteViewModel>()
    val permission = rememberPermissionState(POST_NOTIFICATIONS)

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Quote's") }, actions = {
            IconButton(onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    if (permission.status.isGranted) {
                        viewModel.fetchQuote()
                    }else{
                        permission.launchPermissionRequest()
                    }
                }else{
                    viewModel.fetchQuote()
                }

            }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
        })
    }, content = {
        Column(modifier = Modifier.padding(it)) {
            QuoteCell(viewModel)
        }
    })

}
//https://flexiple.com/android/android-workmanager-tutorial-getting-started
//fun observeQuote(state:WorkInfo?){
//    when(state?.state) {
//        WorkInfo.State.ENQUEUED -> {
//            Timber.e("Waiting for the task to start...")
//        }
//        WorkInfo.State.SUCCEEDED -> {
//            Log.e("","WorkInfo State Is: ${state.outputData.getString(QUOTE_TAG)}")
//        } else ->{
//         Timber.e("WorkInfo State Is: $state")
//        }
//    }
//
//}
@Composable
fun QuoteCell(viewModel: QuoteViewModel) {
    val quoteState by viewModel.uiState.collectAsState()
    if (quoteState.loading) {
        CircularProgressIndicator()
    } else {
        if (quoteState.error != null) {
            Text(quoteState.error ?: "Unknown Error",
                modifier = Modifier.padding(start = 16.dp),
                style = TextStyle(color = Color.Red,fontSize = 18.sp))
        } else{
            if (quoteState.data.isNotEmpty()) {
                Column {
                    Text("Total: ${quoteState.data.count()}",
                        modifier = Modifier.padding(start = 16.dp),
                        color =MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp)
                    LazyColumn(modifier = Modifier
                        .fillMaxSize()){
                        items(quoteState.data){
                            Card(modifier = Modifier
                                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
                                .fillMaxWidth()) {
                                Column(modifier = Modifier
                                    .padding(8.dp)) {
                                    Text(text = it.quote, color = Color.Black)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = "Author: ${it.author}", color = Color.Gray)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Date Fetched: ${formatTimestampToDMY(it.time)}",
                                        color = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = "Work Type: ${it.workType.getWorkRequestType()}",
                                        color = Color.Gray)

                                }
                            }
                        }
                    }
                }
            }else{
                Text(
                    "No data Found",
                    modifier = Modifier.padding(start = 16.dp))
            }
        }
    }
}
@Preview
@Composable
fun QuoteScreenPreview(){
    QuoteScreen()
}