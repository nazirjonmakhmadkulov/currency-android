package com.developer.valyutaapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.developer.valyutaapp.R

object ImageResource {
    fun getImageRes(context: Context, img: String): Bitmap? {
        var icon: Bitmap? = null
        if (img == "USD") icon = BitmapFactory.decodeResource(context.resources, R.drawable.america)
        if (img == "EUR") icon = BitmapFactory.decodeResource(context.resources, R.drawable.european)
        if (img == "XDR") icon = BitmapFactory.decodeResource(context.resources, R.mipmap.xdr)
        if (img == "CNY") icon = BitmapFactory.decodeResource(context.resources, R.drawable.china)
        if (img == "CHF") icon = BitmapFactory.decodeResource(context.resources, R.drawable.switzerland)
        if (img == "RUB") icon = BitmapFactory.decodeResource(context.resources, R.drawable.russia)
        if (img == "UZS") icon = BitmapFactory.decodeResource(context.resources, R.drawable.uzbekistan)
        if (img == "KGS") icon = BitmapFactory.decodeResource(context.resources, R.drawable.kyrgyzstan)
        if (img == "KZT") icon = BitmapFactory.decodeResource(context.resources, R.drawable.kazakhstan)
        if (img == "BYN") icon = BitmapFactory.decodeResource(context.resources, R.drawable.belarus)
        if (img == "IRR") icon = BitmapFactory.decodeResource(context.resources, R.drawable.iran)
        if (img == "AFN") icon = BitmapFactory.decodeResource(context.resources, R.drawable.afghanistan)
        if (img == "PKR") icon = BitmapFactory.decodeResource(context.resources, R.drawable.pakistan)
        if (img == "TRY") icon = BitmapFactory.decodeResource(context.resources, R.drawable.turkey)
        if (img == "TMT") icon = BitmapFactory.decodeResource(context.resources, R.drawable.turkmenistan)
        if (img == "GBP") icon = BitmapFactory.decodeResource(context.resources, R.drawable.united_uingdom)
        if (img == "AUD") icon = BitmapFactory.decodeResource(context.resources, R.drawable.australia)
        if (img == "DKK") icon = BitmapFactory.decodeResource(context.resources, R.drawable.denmark)
        if (img == "ISK") icon = BitmapFactory.decodeResource(context.resources, R.drawable.iceland)
        if (img == "CAD") icon = BitmapFactory.decodeResource(context.resources, R.drawable.canada)
        if (img == "KWD") icon = BitmapFactory.decodeResource(context.resources, R.drawable.kuwait)
        if (img == "NOK") icon = BitmapFactory.decodeResource(context.resources, R.drawable.norway)
        if (img == "SGD") icon = BitmapFactory.decodeResource(context.resources, R.drawable.singapore)
        if (img == "SEK") icon = BitmapFactory.decodeResource(context.resources, R.drawable.sweden)
        if (img == "JPY") icon = BitmapFactory.decodeResource(context.resources, R.drawable.japan)
        if (img == "AZN") icon = BitmapFactory.decodeResource(context.resources, R.drawable.azerbaijan)
        if (img == "AMD") icon = BitmapFactory.decodeResource(context.resources, R.drawable.armenia)
        if (img == "GEL") icon = BitmapFactory.decodeResource(context.resources, R.drawable.georgia)
        if (img == "MDL") icon = BitmapFactory.decodeResource(context.resources, R.drawable.moldova)
        if (img == "UAH") icon = BitmapFactory.decodeResource(context.resources, R.drawable.ukraine)
        if (img == "AED") icon = BitmapFactory.decodeResource(context.resources, R.drawable.denmark)
        if (img == "SAR") icon = BitmapFactory.decodeResource(context.resources, R.drawable.saudi_arabi)
        if (img == "INR") icon = BitmapFactory.decodeResource(context.resources, R.drawable.india)
        if (img == "PLN") icon = BitmapFactory.decodeResource(context.resources, R.drawable.poland)
        if (img == "MYR") icon = BitmapFactory.decodeResource(context.resources, R.drawable.malaysia)
        if (img == "THB") icon = BitmapFactory.decodeResource(context.resources, R.drawable.thailand)
        return icon
    }
}