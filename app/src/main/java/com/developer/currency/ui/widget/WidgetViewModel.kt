package com.developer.currency.ui.widget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.usecases.ValuteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WidgetViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
    fun getLocalValutes(): Flow<List<Valute>> = valuteUseCase.getLocalValutes()

    private val _getLocalValuteById = MutableStateFlow<Valute?>(null)
    val getLocalValuteById: StateFlow<Valute?> get() = _getLocalValuteById
    fun getLocalValuteById(valId: Int) = viewModelScope.launch {
        _getLocalValuteById.value = valuteUseCase.getLocalValuteById(valId)
    }
}