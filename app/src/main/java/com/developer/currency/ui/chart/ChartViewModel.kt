package com.developer.currency.ui.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.currency.core.common.Result
import com.developer.currency.domain.entities.History
import com.developer.currency.domain.entities.ValHistory
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.usecases.HistoryUseCase
import com.developer.currency.domain.usecases.ValuteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChartViewModel(private val valuteUseCase: ValuteUseCase, private val historyUseCase: HistoryUseCase) :
    ViewModel() {

    private val _getLocalValuteById = MutableStateFlow<Valute?>(null)
    val getLocalValuteById: StateFlow<Valute?> get() = _getLocalValuteById
    fun getLocalValuteById(valId: Int) = viewModelScope.launch {
        _getLocalValuteById.value = valuteUseCase.getLocalValuteById(valId)
    }

    // history
    private val _getRemoteHistories = MutableStateFlow<Result<ValHistory>>(Result.Loading)
    val getRemoteHistories: StateFlow<Result<ValHistory>> get() = _getRemoteHistories
    fun getRemoteHistories(d1: String, d2: String, cn: Int, cs: String, exp: String) = viewModelScope.launch {
        when (val result = historyUseCase.getRemoteHistories(d1, d2, cn, cs, exp)) {
            is Result.Loading -> Result.Loading
            is Result.Success -> _getRemoteHistories.value = Result.Success(result.data)
            is Result.Error -> _getRemoteHistories.value = Result.Error(result.cause, result.code, result.message)
        }
    }

    fun getLocalHistories(valId: Int, day: Int): Flow<List<History>> = historyUseCase.getLocalHistories(valId, day)
}