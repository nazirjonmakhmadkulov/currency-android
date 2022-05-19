package com.developer.valyutaapp.ui.sort

import com.developer.valyutaapp.domain.entities.Valute

interface SortPresenterInterface {
    fun updateValute(valute: Valute)
    fun valutes()
}