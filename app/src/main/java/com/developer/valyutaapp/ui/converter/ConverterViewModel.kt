package com.developer.valyutaapp.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.usecases.ValuteUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

class ConverterViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
    fun getAllConverterLocalValutes(): Flow<List<Valute>> = valuteUseCase.getAllConverterLocalValutes()
    private val valutes = getAllConverterLocalValutes().shareIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly
    )
    private val searchInput = MutableSharedFlow<String>()
    private val valuteState = MutableStateFlow<List<Valute>?>(null)

    @OptIn(FlowPreview::class)
    val searchResults = searchInput.debounce(500)
        .map { submitSearchInput(it) }
        .shareIn(viewModelScope, SharingStarted.Eagerly)

    private suspend fun submitSearchInput(query: String) {
        val items = mutableListOf<Valute>()
        valutes.collectLatest { valutesList ->
            items.clear()
            valutesList.forEachIndexed { index, valute ->
                val value = valute.value.trim().toDouble()
                val sum = value * query.trim().toDouble()
                items.add(Valute(
                    id = valute.id,
                    valId = valute.valId,
                    charCode = valute.charCode,
                    nominal = valute.nominal,
                    name = valute.name,
                    value = sum.toString(),
                    dates = valute.dates,
                    favoritesValute = valute.favoritesValute,
                    favoritesConverter = valute.favoritesConverter,
                ))
            }
        }
        valuteState.value = items
    }
}