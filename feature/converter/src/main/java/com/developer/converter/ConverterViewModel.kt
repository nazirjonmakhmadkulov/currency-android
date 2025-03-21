package com.developer.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.common.Utils
import com.developer.domain.model.Currency
import com.developer.domain.usecases.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) :
    ViewModel() {
    private val _currencies = MutableStateFlow<List<Currency>>(emptyList())
    private val currencies: StateFlow<List<Currency>> = _currencies

    val nationalCurrencyState = MutableSharedFlow<String>(replay = 1)
    val foreignCurrencyState = MutableSharedFlow<List<Currency>>(replay = 1)

    fun getConverterLocalCurrencies(): Flow<List<Currency>> =
        currencyUseCase.getConverterLocalCurrencies()
            .onEach { _currencies.value = it }


    fun submitConverterInput(id: Int = 0, amount: Double,nominal: Int, value: Double) = viewModelScope.launch {
        val currentCurrencies = currencies.value
        if (currentCurrencies.isEmpty()) return@launch
        val items = currentCurrencies.map { currency ->
            val sumNational = if (id == 0) {
                convertAnyCurrencyToDollar(amount, currency.nominal.toDouble(), currency.value.toDouble())
            } else {
                if (amount == 0.0) {
                    nationalCurrencyState.emit("0.0")
                    return@map currency.copy(value = "0.0")
                }
                nationalCurrencyState.emit(Utils.decFormat(convertDollarToAnyCurrency(amount, nominal.toDouble(), value)))
                convertCurrency(amount, nominal.toDouble(), value, currency.nominal.toDouble(), currency.value.toDouble())
            }
            currency.copy(value = Utils.decFormat(sumNational))
        }
        foreignCurrencyState.emit(items)
    }

    private fun convertCurrency(
        amount: Double, nomDollar: Double, valueDollar: Double, nominalAny: Double, valueAny: Double
    ): Double {
        val amountDollar = convertDollarToAnyCurrency(amount, nomDollar, valueDollar)
        return convertAnyCurrencyToDollar(amountDollar, nominalAny, valueAny)
    }

    private fun convertDollarToAnyCurrency(amount: Double, nomDollar: Double, valueDollar: Double): Double {
        return (amount * valueDollar) / nomDollar
    }

    private fun convertAnyCurrencyToDollar(amount: Double, nominal: Double, value: Double): Double {
        return (amount * nominal) / value
    }
}