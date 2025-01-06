package com.dmuhia.bgtaskdemoapp.data.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.dmuhia.bgtaskdemoapp.data.local.AppDatabase
import com.dmuhia.bgtaskdemoapp.data.local.QuoteDao
import com.dmuhia.bgtaskdemoapp.data.local.repository.QuoteRepository
import com.dmuhia.bgtaskdemoapp.data.local.repository.QuoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "quote_database"
        )
            .build()
    }

    @Provides
    fun provideWeatherDao(appDatabase: AppDatabase): QuoteDao {
        return appDatabase.quoteDao()
    }
    @Provides
    fun provideLocalRepository(workManager: WorkManager, quoteDao: QuoteDao): QuoteRepository {
        return QuoteRepositoryImpl(workManager, quoteDao)
    }




}

