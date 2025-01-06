package com.dmuhia.bgtaskdemoapp.data.local.repository

import com.dmuhia.bgtaskdemoapp.data.local.QuoteEntity
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getQuote()

    fun getAllQuotes(): Flow<List<QuoteEntity>>

    fun setupPeriodicWorkRequest()
}