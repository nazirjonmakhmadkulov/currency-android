package com.developer.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.domain.usecases.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) : ViewModel() {
    fun getRemoteCurrencies(date: String, exp: String) = viewModelScope.launch {
        currencyUseCase.getRemoteCurrencies(date, exp)
    }
}