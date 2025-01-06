package com.dmuhia.bgtaskdemoapp.data.network.repository

import com.dmuhia.bgtaskdemoapp.data.model.Quote

interface RemoteRepository {
   suspend fun getQuotes(workType:String): Quote
}