package com.dmuhia.bgtaskdemoapp.data.network

import com.dmuhia.bgtaskdemoapp.data.model.Quote
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("quotes/random")
    suspend fun getQuotes(
    ): Response<Quote>


}
