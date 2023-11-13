package com.developer.currency.ui.valutes

import androidx.lifecycle.ViewModel
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.usecases.ValuteUseCase
import kotlinx.coroutines.flow.Flow

class AllValutesViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
    fun getLocalValutes(): Flow<List<Valute>> = valuteUseCase.getLocalValutes()
}