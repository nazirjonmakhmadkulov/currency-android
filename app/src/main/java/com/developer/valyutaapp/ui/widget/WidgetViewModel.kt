package com.developer.valyutaapp.ui.widget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.domain.usecases.ValuteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WidgetViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
    fun getLocalValutes(): Flow<List<Valute>> = valuteUseCase.getLocalValutes()
    private val _getLocalValuteById = MutableLiveData<Valute>()
    val getLocalValuteById: LiveData<Valute> get() = _getLocalValuteById
    fun getLocalValuteById(valId: Int) = viewModelScope.launch {
        _getLocalValuteById.value = valuteUseCase.getLocalValuteById(valId)
    }
}