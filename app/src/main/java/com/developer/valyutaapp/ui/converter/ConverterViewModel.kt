package com.developer.valyutaapp.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.usecases.ValuteUseCase
import com.developer.valyutaapp.utils.foreignValute
import com.developer.valyutaapp.utils.nationalValute
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import timber.log.Timber

class ConverterViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
    fun getAllConverterLocalValutes(): Flow<List<Valute>> = valuteUseCase.getAllConverterLocalValutes()
    private var valutes = listOf<Valute>()

    init {
        getAllConverterLocalValutes()
            .onEach { valutes = it }
            .launchIn(viewModelScope)
    }

    private val searchInput = MutableSharedFlow<String>()

    val valuteState = MutableStateFlow<List<Valute>?>(null)

    @OptIn(FlowPreview::class)
    val searchResults = searchInput.debounce(500)
        .map { submitSearchInput(it) }
        .shareIn(viewModelScope, SharingStarted.Eagerly)

    fun submitSearchInput(query: String) {
        Timber.d("valuteState query ${query}")
        valuteState.value = valutes.map { valute ->
            val sumNational = nationalValute(query.toDouble(), valute.value.toDouble())
            val sum = foreignValute(query.toDouble(), query.toDouble(), valute.value.toDouble())
            Valute(
                id = valute.id,
                valId = valute.valId,
                charCode = valute.charCode,
                nominal = valute.nominal,
                name = valute.name,
                value = sum.toString(),
                dates = valute.dates,
                favoritesValute = valute.favoritesValute,
                favoritesConverter = valute.favoritesConverter,
            )
        }
        Timber.d("valuteState items ${valuteState.value}")
    }
}