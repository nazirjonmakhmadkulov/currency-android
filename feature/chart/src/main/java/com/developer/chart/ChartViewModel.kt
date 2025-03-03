package com.developer.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.domain.model.History
import com.developer.domain.model.Currency
import com.developer.domain.usecases.HistoryUseCase
import com.developer.domain.usecases.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase,
    private val historyUseCase: HistoryUseCase
) : ViewModel() {

    private val _getLocalCurrencyById = MutableStateFlow<Currency?>(null)
    val getLocalCurrencyById: StateFlow<Currency?> get() = _getLocalCurrencyById
    fun getLocalValuteById(valId: Int) = viewModelScope.launch {
        _getLocalCurrencyById.value = currencyUseCase.getLocalCurrencyById(valId)
    }

    fun getRemoteHistories(d1: String, d2: String, cn: Int, cs: String, exp: String) = viewModelScope.launch {
        historyUseCase.getRemoteHistories(d1, d2, cn, cs, exp)
    }

    fun getLocalHistories(valId: Int, day: Int): Flow<List<History>> =
        historyUseCase.getLocalHistories(valId, day).take(day + 1)
}