package com.developer.currency.ui.valutes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.currency.domain.entities.ValCurs
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.usecases.ValuteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllValutesViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
    fun getLocalValutes(): Flow<List<Valute>> = valuteUseCase.getLocalValutes()
}