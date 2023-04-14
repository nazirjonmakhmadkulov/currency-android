package com.developer.valyutaapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.developer.valyutaapp.R

object ImageResource {
    fun getImageRes(context: Context, img: String): Drawable? {
        return when (img) {
            "USD" -> ContextCompat.getDrawable(context, R.drawable.america)
            "EUR" -> ContextCompat.getDrawable(context, R.drawable.european)
            "XDR" -> ContextCompat.getDrawable(context, R.mipmap.xdr)
            "CNY" -> ContextCompat.getDrawable(context, R.drawable.china)
            "CHF" -> ContextCompat.getDrawable(context, R.drawable.switzerland)
            "RUB" -> ContextCompat.getDrawable(context, R.drawable.russia)
            "UZS" -> ContextCompat.getDrawable(context, R.drawable.uzbekistan)
            "KGS" -> ContextCompat.getDrawable(context, R.drawable.kg)
            "KZT" -> ContextCompat.getDrawable(context, R.drawable.kyrgyzstan)
            "BYN" -> ContextCompat.getDrawable(context, R.drawable.belarus)
            "IRR" -> ContextCompat.getDrawable(context, R.drawable.iran)
            "AFN" -> ContextCompat.getDrawable(context, R.drawable.afghanistan)
            "PKR" -> ContextCompat.getDrawable(context, R.drawable.pakistan)
            "TRY" -> ContextCompat.getDrawable(context, R.drawable.turkey)
            "TMT" -> ContextCompat.getDrawable(context, R.drawable.turkmenistan)
            "GBP" -> ContextCompat.getDrawable(context, R.drawable.united_uingdom)
            "AUD" -> ContextCompat.getDrawable(context, R.drawable.australia)
            "DKK" -> ContextCompat.getDrawable(context, R.drawable.denmark)
            "ISK" -> ContextCompat.getDrawable(context, R.drawable.iceland)
            "CAD" -> ContextCompat.getDrawable(context, R.drawable.canada)
            "KWD" -> ContextCompat.getDrawable(context, R.drawable.kuwait)
            "NOK" -> ContextCompat.getDrawable(context, R.drawable.norway)
            "SGD" -> ContextCompat.getDrawable(context, R.drawable.singapore)
            "SEK" -> ContextCompat.getDrawable(context, R.drawable.sweden)
            "JPY" -> ContextCompat.getDrawable(context, R.drawable.japan)
            "AZN" -> ContextCompat.getDrawable(context, R.drawable.azerbaijan)
            "AMD" -> ContextCompat.getDrawable(context, R.drawable.armenia)
            "GEL" -> ContextCompat.getDrawable(context, R.drawable.georgia)
            "MDL" -> ContextCompat.getDrawable(context, R.drawable.moldova)
            "UAH" -> ContextCompat.getDrawable(context, R.drawable.ukraine)
            "AED" -> ContextCompat.getDrawable(context, R.drawable.united_arabamirat)
            "SAR" -> ContextCompat.getDrawable(context, R.drawable.saudi_arabi)
            "INR" -> ContextCompat.getDrawable(context, R.drawable.india)
            "PLN" -> ContextCompat.getDrawable(context, R.drawable.poland)
            "MYR" -> ContextCompat.getDrawable(context, R.drawable.malaysia)
            "THB" -> ContextCompat.getDrawable(context, R.drawable.thailand)
            "MXN" -> ContextCompat.getDrawable(context, R.drawable.mexica)
            else -> ContextCompat.getDrawable(context, R.drawable.tajikistan)
        }
    }
}