package com.developer.valyutaapp.ui.widget

import com.developer.valyutaapp.domain.entities.Valute

interface WidgetViewInterface {
    fun showToast(s: String)
    fun showProgressBar()
    fun hideProgressBar()
    fun displayValuteWithId(valute: Valute)
    fun displayValutes(valute: List<Valute>)
    fun displayError(s: String)
}