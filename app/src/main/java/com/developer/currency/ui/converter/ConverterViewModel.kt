package com.developer.currency.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.usecases.ValuteUseCase
import com.developer.currency.utils.Utils
import com.developer.currency.utils.nationalValute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ConverterViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
    fun getAllConverterLocalValutes(): Flow<List<Valute>> = valuteUseCase.getAllConverterLocalValutes()
    private var valutes = listOf<Valute>()

    init {
        getAllConverterLocalValutes().onEach { valutes = it }.launchIn(viewModelScope)
    }

    val valuteState = MutableSharedFlow<List<Valute>>()

    fun submitConverterInput(query: Double) = viewModelScope.launch {
        val items = valutes.map { valute ->
            val sumNational = nationalValute(valute.nominal.toDouble(), query, valute.value.toDouble())
            val formatSum = Utils.decFormat(sumNational)
            Valute(
                id = valute.id,
                valId = valute.valId,
                charCode = valute.charCode,
                nominal = valute.nominal,
                name = valute.name,
                value = formatSum,
                dates = valute.dates,
                favoritesValute = valute.favoritesValute,
                favoritesConverter = valute.favoritesConverter
            )
        }
        valuteState.emit(items)
    }
}