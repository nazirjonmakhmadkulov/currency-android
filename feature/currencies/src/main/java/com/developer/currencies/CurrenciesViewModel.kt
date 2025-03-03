package com.developer.currencies

import androidx.lifecycle.ViewModel
import com.developer.domain.model.Currency
import com.developer.domain.usecases.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) : ViewModel() {
    fun fetchLocalCurrencies(): Flow<List<Currency>> = currencyUseCase.getLocalCurrencies()
}