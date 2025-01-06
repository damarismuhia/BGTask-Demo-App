package com.dmuhia.bgtaskdemoapp.data.model

data class QuoteResponse(
    val total: Int,
    val limit: Int,
    val quotes:List<Quote>
)
data class Quote(
    val id: Int,
    val quote: String,
    val author: String,
)

