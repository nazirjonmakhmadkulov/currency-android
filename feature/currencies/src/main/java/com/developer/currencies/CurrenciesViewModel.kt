package com.developer.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.domain.model.Currency
import com.developer.domain.usecases.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) : ViewModel() {
    internal fun getRemoteCurrencies(date: String, exp: String) = viewModelScope.launch {
        currencyUseCase.getRemoteCurrencies(date, exp)
    }

    internal fun fetchLocalCurrencies(): Flow<List<Currency>> = currencyUseCase.getLocalCurrencies()
}