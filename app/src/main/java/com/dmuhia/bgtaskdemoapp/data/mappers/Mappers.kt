package com.dmuhia.bgtaskdemoapp.data.mappers

import com.dmuhia.bgtaskdemoapp.data.local.QuoteEntity
import com.dmuhia.bgtaskdemoapp.data.model.Quote
import com.dmuhia.bgtaskdemoapp.data.model.QuoteResponse
import com.dmuhia.bgtaskdemoapp.utils.ONE_TIME_WORK_REQUEST

fun Quote.toEntity(workType: String): QuoteEntity {
    return QuoteEntity(this.author,
        id, quote, workType)
}

fun String.getWorkRequestType():String {
    return if (this == ONE_TIME_WORK_REQUEST){
        "OneTime WorkRequest"
    } else {
        "Periodic WorkRequest"
    }
}