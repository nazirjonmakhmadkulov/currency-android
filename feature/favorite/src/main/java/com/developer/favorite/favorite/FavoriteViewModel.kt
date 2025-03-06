package com.developer.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.domain.model.Currency
import com.developer.domain.usecases.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) : ViewModel() {
    fun fetchFavorites(): Flow<List<Currency>> = currencyUseCase.getFavoriteLocalCurrencies()
    fun fetchConverters(): Flow<List<Currency>> = currencyUseCase.getConverterLocalCurrencies()
    fun updateLocalCurrency(currency: Currency) = viewModelScope.launch {
        currencyUseCase.updateLocalCurrency(currency)
    }
}