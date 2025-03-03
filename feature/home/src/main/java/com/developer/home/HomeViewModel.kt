package com.developer.home

import androidx.lifecycle.ViewModel
import com.developer.domain.model.Currency
import com.developer.domain.usecases.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) : ViewModel() {
    fun fetchFavoriteCurrencies(): Flow<List<Currency>> = currencyUseCase.getFavoriteLocalCurrencies()
}