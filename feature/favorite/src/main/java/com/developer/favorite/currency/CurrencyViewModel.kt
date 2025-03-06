package com.developer.favorite.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.domain.model.Currency
import com.developer.domain.usecases.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) : ViewModel() {
    fun fetchCurrencies(): Flow<List<Currency>> = currencyUseCase.getLocalCurrencies()
    fun updateLocalCurrency(currency: Currency) = viewModelScope.launch {
        currencyUseCase.updateLocalCurrency(currency)
    }
}