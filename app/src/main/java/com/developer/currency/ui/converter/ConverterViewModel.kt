package com.developer.currency.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.usecases.ValuteUseCase
import com.developer.currency.core.utils.Utils
import com.developer.currency.core.utils.foreignValute
import com.developer.currency.core.utils.nationalValute
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

    val nationalValuteState = MutableSharedFlow<String>()
    val foreignValuteState = MutableSharedFlow<List<Valute>>()

    fun submitConverterInput(id: Int = 0, query: Double, value: Double) = viewModelScope.launch {
        val items = valutes.map { valute ->
            val sumNational = if (id == 0) nationalValute(valute.nominal.toDouble(), query, valute.value.toDouble())
            else {
                val national = if (query == 0.0) "0.0" else Utils.decFormat(query * value)
                nationalValuteState.emit(national)
                foreignValute(query, value, valute.value.toDouble())
            }
            val formatValue = if (sumNational == 0.0) "0.0" else Utils.decFormat(sumNational)
            Valute(
                id = valute.id,
                valId = valute.valId,
                charCode = valute.charCode,
                nominal = valute.nominal,
                name = valute.name,
                value = formatValue,
                dates = valute.dates,
                favoritesValute = valute.favoritesValute,
                favoritesConverter = valute.favoritesConverter
            )
        }
        foreignValuteState.emit(items)
    }
}