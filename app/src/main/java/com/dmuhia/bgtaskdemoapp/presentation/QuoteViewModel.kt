package com.dmuhia.bgtaskdemoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmuhia.bgtaskdemoapp.data.domain.GetAllQuotesUseCase
import com.dmuhia.bgtaskdemoapp.data.domain.GetQuoteUseCase
import com.dmuhia.bgtaskdemoapp.data.domain.GetupPeriodicWorkRequestUseCase
import com.dmuhia.bgtaskdemoapp.data.local.QuoteEntity
import com.dmuhia.bgtaskdemoapp.data.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val quoteUseCase: GetQuoteUseCase,
    allQuotesUseCase: GetAllQuotesUseCase,
    periodicWorkRequestUseCase: GetupPeriodicWorkRequestUseCase
):ViewModel() {
    val uiState = allQuotesUseCase.invoke() //returns  Flow<List<QuoteEntity>>
        .onStart { UiState(true, emptyList(),null) }
        .map {UiState(false,it,null)} //transform  Flow<List<QuoteEntity>> into a  List<UiState>
        .catch { UiState(false, emptyList(),it.message)}
        /**
         * stateIn -  convert the flow in to a Stateflow
         * viewModelScope - The flow will be collected within the lifecycle of the ViewModel.
          This means the flow will be automatically canceled when the ViewModel is cleared.
         * SharingStarted.Eagerly: This means the flow will start emitting values as soon as it’s collected, even if no one is actively observing it. It's useful for preloading or caching data.
         * UiState(emptyList()): This is the initial state of the StateFlow. It starts with an empty list of Quote objects.
         *
         * */

        .stateIn(viewModelScope, SharingStarted.Eagerly,UiState(true, emptyList(),null) )
    init {
        periodicWorkRequestUseCase.invoke()
    }

    fun fetchQuote(){
        viewModelScope.launch {
           quoteUseCase.invoke()
        }
    }
}
data class UiState(val loading:Boolean,val data: List<QuoteEntity>,val error:String?)