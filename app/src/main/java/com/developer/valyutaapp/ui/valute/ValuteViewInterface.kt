package com.developer.valyutaapp.ui.valute

import com.developer.valyutaapp.model.Valute

interface ValuteViewInterface {
    fun showToast(s: String)
    fun showProgressBar()
    fun hideProgressBar()
    fun displayValuteWithId(valute: Valute)
    fun displayValutes(valute: List<Valute>)
    fun displayError(s: String)
}