package com.developer.currency.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.currency.core.dispatcher.launchIO
import com.developer.currency.domain.entities.ValCurs
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.usecases.ValuteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class MainViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
    private val _getRemoteValutes = MutableStateFlow<List<Valute>>(emptyList())
    val getRemoteValutes: StateFlow<List<Valute>> get() = _getRemoteValutes

    fun getRemoteValutes(date: String, exp: String) = viewModelScope.launchIO(
        safeAction = { _getRemoteValutes.value = valuteUseCase.getRemoteValutes(date, exp).valute },
        onError = Timber::e
    )

    fun getLocalValutes(): Flow<List<Valute>> = valuteUseCase.getLocalValutes()
    fun getFavoriteLocalValutes(): Flow<List<Valute>> = valuteUseCase.getFavoriteLocalValutes()
    fun getAllConverterLocalValutes(): Flow<List<Valute>> = valuteUseCase.getAllConverterLocalValutes()

    fun updateLocalValute(valute: Valute) = viewModelScope.launchIO(
        safeAction = { valuteUseCase.updateLocalValute(valute) },
        onError = Timber::e
    )
}