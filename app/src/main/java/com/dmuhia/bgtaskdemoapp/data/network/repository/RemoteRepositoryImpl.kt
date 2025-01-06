package com.dmuhia.bgtaskdemoapp.data.network.repository

import com.dmuhia.bgtaskdemoapp.data.local.QuoteDao
import com.dmuhia.bgtaskdemoapp.data.mappers.toEntity
import com.dmuhia.bgtaskdemoapp.data.model.Quote
import com.dmuhia.bgtaskdemoapp.data.network.ApiService
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService,
                                               private val quoteDao: QuoteDao,
                                               ): RemoteRepository {
    override suspend fun getQuotes(workType:String): Quote {
        val response = apiService.getQuotes()
       return if (response.isSuccessful && response.body() != null){
            val quotes = response.body()!!
            quoteDao.insert(quotes.toEntity(workType))
             quotes
        } else {
            throw Exception(response.message())
        }
    }
}