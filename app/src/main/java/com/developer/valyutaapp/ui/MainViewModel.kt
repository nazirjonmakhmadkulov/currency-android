package com.developer.valyutaapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.usecases.ValuteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
    private val _getRemoteValutes = MutableStateFlow<Result<ValCurs>>(Result.Loading)
    val getRemoteValutes: StateFlow<Result<ValCurs>> get() = _getRemoteValutes
    fun getRemoteValutes(date: String, exp: String) = viewModelScope.launch {
        when (val result = valuteUseCase.getRemoteValutes(date, exp)) {
            is Result.Loading -> Result.Loading
            is Result.Success -> _getRemoteValutes.value = Result.Success(result.data)
            is Result.Error -> _getRemoteValutes.value =
                Result.Error(result.cause, result.code, result.message)
        }
    }

    fun getLocalValutes(): Flow<List<Valute>> = valuteUseCase.getLocalValutes()
    fun getFavoriteLocalValutes(): Flow<List<Valute>> = valuteUseCase.getFavoriteLocalValutes()
    fun getAllConverterLocalValutes(): Flow<List<Valute>> = valuteUseCase.getAllConverterLocalValutes()

    fun updateLocalValute(valute: Valute) = viewModelScope.launch {
        valuteUseCase.updateLocalValute(valute)
    }
}