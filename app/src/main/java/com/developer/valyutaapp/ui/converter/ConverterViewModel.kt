package com.developer.valyutaapp.ui.converter

import androidx.lifecycle.ViewModel
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.usecases.ValuteUseCase
import kotlinx.coroutines.flow.Flow

class ConverterViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
    fun getAllConverterLocalValutes(): Flow<List<Valute>> = valuteUseCase.getAllConverterLocalValutes()
}