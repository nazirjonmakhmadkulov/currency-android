package com.developer.valyutaapp.ui.sort

import com.developer.valyutaapp.model.Valute

interface SortViewInterface {
    fun showToast(s: String)
    fun showProgressBar()
    fun hideProgressBar()
    fun displayValutes(valute: List<Valute>)
    fun displayError(s: String)
}