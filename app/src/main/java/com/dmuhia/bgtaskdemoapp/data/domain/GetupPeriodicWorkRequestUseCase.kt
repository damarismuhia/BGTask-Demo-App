package com.dmuhia.bgtaskdemoapp.data.domain

import com.dmuhia.bgtaskdemoapp.data.local.repository.QuoteRepository
import javax.inject.Inject

class GetupPeriodicWorkRequestUseCase @Inject constructor(private val repo: QuoteRepository) {
    operator fun invoke() = repo.setupPeriodicWorkRequest()
}