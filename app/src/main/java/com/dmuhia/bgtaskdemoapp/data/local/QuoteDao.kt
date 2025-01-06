package com.dmuhia.bgtaskdemoapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmuhia.bgtaskdemoapp.data.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: QuoteEntity)

    @Query("SELECT * FROM QuoteEntity ORDER BY time DESC")
    fun getQuotesList(): Flow<List<QuoteEntity>>
}