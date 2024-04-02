package com.developer.currency.ui.home

import androidx.lifecycle.ViewModel
import com.developer.currency.domain.entities.Valute
import com.developer.currency.domain.usecases.ValuteUseCase
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val valuteUseCase: ValuteUseCase) : ViewModel() {
    fun fetchFavoriteValutes(): Flow<List<Valute>> = valuteUseCase.getFavoriteLocalValutes()
}