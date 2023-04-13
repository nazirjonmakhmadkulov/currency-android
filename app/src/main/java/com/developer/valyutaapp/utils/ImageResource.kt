package com.developer.valyutaapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.developer.valyutaapp.R

object ImageResource {
    fun getImageRes(context: Context, img: String): Drawable? {
        var icon: Drawable? = null
        if (img == "USD") icon = ContextCompat.getDrawable(context, R.drawable.america)
        if (img == "EUR") icon = ContextCompat.getDrawable(context, R.drawable.european)
        if (img == "XDR") icon = ContextCompat.getDrawable(context, R.mipmap.xdr)
        if (img == "CNY") icon = ContextCompat.getDrawable(context, R.drawable.china)
        if (img == "CHF") icon = ContextCompat.getDrawable(context, R.drawable.switzerland)
        if (img == "RUB") icon = ContextCompat.getDrawable(context, R.drawable.russia)
        if (img == "UZS") icon = ContextCompat.getDrawable(context, R.drawable.uzbekistan)
        if (img == "KGS") icon = ContextCompat.getDrawable(context, R.drawable.kg)
        if (img == "KZT") icon = ContextCompat.getDrawable(context, R.drawable.kyrgyzstan)
        if (img == "BYN") icon = ContextCompat.getDrawable(context, R.drawable.belarus)
        if (img == "IRR") icon = ContextCompat.getDrawable(context, R.drawable.iran)
        if (img == "AFN") icon = ContextCompat.getDrawable(context, R.drawable.afghanistan)
        if (img == "PKR") icon = ContextCompat.getDrawable(context, R.drawable.pakistan)
        if (img == "TRY") icon = ContextCompat.getDrawable(context, R.drawable.turkey)
        if (img == "TMT") icon = ContextCompat.getDrawable(context, R.drawable.turkmenistan)
        if (img == "GBP") icon = ContextCompat.getDrawable(context, R.drawable.united_uingdom)
        if (img == "AUD") icon = ContextCompat.getDrawable(context, R.drawable.australia)
        if (img == "DKK") icon = ContextCompat.getDrawable(context, R.drawable.denmark)
        if (img == "ISK") icon = ContextCompat.getDrawable(context, R.drawable.iceland)
        if (img == "CAD") icon = ContextCompat.getDrawable(context, R.drawable.canada)
        if (img == "KWD") icon = ContextCompat.getDrawable(context, R.drawable.kuwait)
        if (img == "NOK") icon = ContextCompat.getDrawable(context, R.drawable.norway)
        if (img == "SGD") icon = ContextCompat.getDrawable(context, R.drawable.singapore)
        if (img == "SEK") icon = ContextCompat.getDrawable(context, R.drawable.sweden)
        if (img == "JPY") icon = ContextCompat.getDrawable(context, R.drawable.japan)
        if (img == "AZN") icon = ContextCompat.getDrawable(context, R.drawable.azerbaijan)
        if (img == "AMD") icon = ContextCompat.getDrawable(context, R.drawable.armenia)
        if (img == "GEL") icon = ContextCompat.getDrawable(context, R.drawable.georgia)
        if (img == "MDL") icon = ContextCompat.getDrawable(context, R.drawable.moldova)
        if (img == "UAH") icon = ContextCompat.getDrawable(context, R.drawable.ukraine)
        if (img == "AED") icon = ContextCompat.getDrawable(context, R.drawable.united_arabamirat)
        if (img == "SAR") icon = ContextCompat.getDrawable(context, R.drawable.saudi_arabi)
        if (img == "INR") icon = ContextCompat.getDrawable(context, R.drawable.india)
        if (img == "PLN") icon = ContextCompat.getDrawable(context, R.drawable.poland)
        if (img == "MYR") icon = ContextCompat.getDrawable(context, R.drawable.malaysia)
        if (img == "THB") icon = ContextCompat.getDrawable(context, R.drawable.thailand)
        if (img == "MXN") icon = ContextCompat.getDrawable(context, R.drawable.mexica)
        return icon
    }
}