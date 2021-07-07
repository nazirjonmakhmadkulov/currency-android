package com.developer.valyutaapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.developer.valyutaapp.R;


public class ImageResource {

    public static Bitmap getImageRes(Context context, String img){
        Bitmap icon = null;
        if (img.equals("USD")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.america);
        }
        if (img.equals("EUR")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.european);
        }
        if (img.equals("XDR")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.xdr);
        }
        if (img.equals("CNY")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.china);
        }
        if (img.equals("CHF")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.switzerland);
        }
        if (img.equals("RUB")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.russia);
        }
        if (img.equals("UZS")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.uzbekistan);
        }
        if (img.equals("KGS")) {//not
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.kyrgyzstan);
        }
        if (img.equals("KZT")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.kazakhstan);
        }
        if (img.equals("BYN")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.belarus);
        }
        if (img.equals("IRR")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.iran);
        }
        if (img.equals("AFN")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.afghanistan);
        }
        if (img.equals("PKR")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pakistan);
        }
        if (img.equals("TRY")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.turkey);
        }
        if (img.equals("TMT")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.turkmenistan);
        }
        if (img.equals("GBP")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.united_uingdom);
        }
        if (img.equals("AUD")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.australia);
        }
        if (img.equals("DKK")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.denmark);
        }
        if (img.equals("ISK")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.iceland);
        }
        if (img.equals("CAD")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.canada);
        }
        if (img.equals("KWD")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.kuwait);
        }
        if (img.equals("NOK")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.norway);
        }
        if (img.equals("SGD")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.singapore);
        }
        if (img.equals("SEK")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sweden);
        }
        if (img.equals("JPY")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.japan);
        }
        if (img.equals("AZN")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.azerbaijan);
        }
        if (img.equals("AMD")) {//not
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.armenia);
        }
        if (img.equals("GEL")) {//not
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.georgia);
        }
        if (img.equals("MDL")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.moldova);
        }
        if (img.equals("UAH")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ukraine);
        }
        if (img.equals("AED")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.denmark);
        }
        if (img.equals("SAR")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.saudi_arabi);
        }
        if (img.equals("INR")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.india);
        }
        if (img.equals("PLN")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.poland);
        }
        if (img.equals("MYR")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.malaysia);
        }
        if (img.equals("THB")) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.thailand);
        }
        return icon;
    }
}
