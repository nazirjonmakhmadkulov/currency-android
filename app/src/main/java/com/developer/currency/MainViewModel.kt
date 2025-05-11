package com.developer.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.domain.usecases.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase,
) : ViewModel() {
    fun getRemoteCurrencies(date: String, exp: String) = viewModelScope.launch {
        currencyUseCase.getRemoteCurrencies(date, exp)
    }

    private val _minuteChannel = Channel<Boolean>(Channel.CONFLATED)
    val minuteChannel = _minuteChannel.receiveAsFlow()
    private var isCorrect = false

    fun startMinuteTicker() = viewModelScope.launch {
        while (true) {
            isCorrect = !isCorrect
            _minuteChannel.send(isCorrect)
            delay(45_000L)
        }
    }
}