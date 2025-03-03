package com.developer.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.common.Utils
import com.developer.common.foreignValute
import com.developer.common.nationalValute
import com.developer.domain.model.Currency
import com.developer.domain.usecases.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) :
    ViewModel() {
    fun getConverterLocalCurrencies(): Flow<List<Currency>> = currencyUseCase.getConverterLocalCurrencies()
    private var currencies = listOf<Currency>()

    init {
        getConverterLocalCurrencies().onEach { currencies = it }.launchIn(viewModelScope)
    }

    val nationalValuteState = MutableSharedFlow<String>()
    val foreignCurrencyState = MutableSharedFlow<List<Currency>>()

    fun submitConverterInput(id: Int = 0, query: Double, value: Double) = viewModelScope.launch {
        val items = currencies.map { valute ->
            val sumNational = if (id == 0) nationalValute(valute.nominal.toDouble(), query, valute.value.toDouble())
            else {
                val national = if (query == 0.0) "0.0" else Utils.decFormat(query * value)
                nationalValuteState.emit(national)
                foreignValute(query, value, valute.value.toDouble())
            }
            val formatValue = if (sumNational == 0.0) "0.0" else Utils.decFormat(sumNational)
            Currency(
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
        foreignCurrencyState.emit(items)
    }
}