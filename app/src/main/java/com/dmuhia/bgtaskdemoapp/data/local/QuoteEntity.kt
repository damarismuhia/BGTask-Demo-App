package com.dmuhia.bgtaskdemoapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class QuoteEntity (
    val author: String,
    @PrimaryKey
    val id: Int,
    val quote: String,
    val workType: String = "",
    val time: Long = System.currentTimeMillis()
)