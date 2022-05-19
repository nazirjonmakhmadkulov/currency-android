package com.developer.valyutaapp.ui.sort

import com.developer.valyutaapp.model.Valute

interface SortPresenterInterface {
    fun updateValute(valute: Valute)
    fun valutes()
}