package com.developer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.domain.model.Currency
import com.developer.domain.usecases.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) : ViewModel() {
    internal fun getRemoteCurrencies(date: String, exp: String) = viewModelScope.launch {
        currencyUseCase.getRemoteCurrencies(date, exp)
    }

    internal fun fetchFavoriteCurrencies(): Flow<List<Currency>> = currencyUseCase.getFavoriteLocalCurrencies()
}