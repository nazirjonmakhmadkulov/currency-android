package com.developer.valyutaapp.ui.valutes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.usecases.ValuteUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class AllValutesViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
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
}