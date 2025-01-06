package com.dmuhia.bgtaskdemoapp.presentation.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dmuhia.bgtaskdemoapp.data.mappers.getWorkRequestType
import com.dmuhia.bgtaskdemoapp.presentation.QuoteViewModel
import com.dmuhia.bgtaskdemoapp.utils.formatTimestampToDMY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(){
    val viewModel = hiltViewModel<QuoteViewModel>()
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Quote's") }, actions = {
            IconButton(onClick = { viewModel.fetchQuote() }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
        })
    }, content = {
        Column(modifier = Modifier.padding(it)) {
            QuoteCell(viewModel)
        }
    })

}
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