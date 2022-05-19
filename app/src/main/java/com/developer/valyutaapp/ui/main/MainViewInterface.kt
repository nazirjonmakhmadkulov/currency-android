package com.developer.valyutaapp.ui.main

import com.developer.valyutaapp.domain.entities.Valute

interface MainViewInterface {
    fun showToast(s: String?)
    fun showProgressBar()
    fun hideProgressBar()
    fun displayValutes(valutes: List<Valute>?)
    fun displayError(s: String?)
}