package com.developer.designsystem.icons

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.developer.designsystem.R

fun Context.getImageRes(img: String): Drawable? = when (img) {
    "USD" -> ContextCompat.getDrawable(this, R.drawable.america)
    "EUR" -> ContextCompat.getDrawable(this, R.drawable.european)
    "CNY" -> ContextCompat.getDrawable(this, R.drawable.china)
    "CHF" -> ContextCompat.getDrawable(this, R.drawable.switzerland)
    "RUB" -> ContextCompat.getDrawable(this, R.drawable.russia)
    "UZS" -> ContextCompat.getDrawable(this, R.drawable.uzbekistan)
    "KGS" -> ContextCompat.getDrawable(this, R.drawable.kg)
    "KZT" -> ContextCompat.getDrawable(this, R.drawable.kyrgyzstan)
    "BYN" -> ContextCompat.getDrawable(this, R.drawable.belarus)
    "IRR" -> ContextCompat.getDrawable(this, R.drawable.iran)
    "AFN" -> ContextCompat.getDrawable(this, R.drawable.afghanistan)
    "PKR" -> ContextCompat.getDrawable(this, R.drawable.pakistan)
    "TRY" -> ContextCompat.getDrawable(this, R.drawable.turkey)
    "TMT" -> ContextCompat.getDrawable(this, R.drawable.turkmenistan)
    "GBP" -> ContextCompat.getDrawable(this, R.drawable.united_uingdom)
    "AUD" -> ContextCompat.getDrawable(this, R.drawable.australia)
    "DKK" -> ContextCompat.getDrawable(this, R.drawable.denmark)
    "ISK" -> ContextCompat.getDrawable(this, R.drawable.iceland)
    "CAD" -> ContextCompat.getDrawable(this, R.drawable.canada)
    "KWD" -> ContextCompat.getDrawable(this, R.drawable.kuwait)
    "NOK" -> ContextCompat.getDrawable(this, R.drawable.norway)
    "SGD" -> ContextCompat.getDrawable(this, R.drawable.singapore)
    "SEK" -> ContextCompat.getDrawable(this, R.drawable.sweden)
    "JPY" -> ContextCompat.getDrawable(this, R.drawable.japan)
    "AZN" -> ContextCompat.getDrawable(this, R.drawable.azerbaijan)
    "AMD" -> ContextCompat.getDrawable(this, R.drawable.armenia)
    "GEL" -> ContextCompat.getDrawable(this, R.drawable.georgia)
    "MDL" -> ContextCompat.getDrawable(this, R.drawable.moldova)
    "UAH" -> ContextCompat.getDrawable(this, R.drawable.ukraine)
    "AED" -> ContextCompat.getDrawable(this, R.drawable.united_arabamirat)
    "SAR" -> ContextCompat.getDrawable(this, R.drawable.saudi_arabi)
    "INR" -> ContextCompat.getDrawable(this, R.drawable.india)
    "PLN" -> ContextCompat.getDrawable(this, R.drawable.poland)
    "MYR" -> ContextCompat.getDrawable(this, R.drawable.malaysia)
    "THB" -> ContextCompat.getDrawable(this, R.drawable.thailand)
    "MXN" -> ContextCompat.getDrawable(this, R.drawable.mexica)
    else -> ContextCompat.getDrawable(this, R.drawable.tajikistan)
}