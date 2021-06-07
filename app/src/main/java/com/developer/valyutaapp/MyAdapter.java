package com.developer.valyutaapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import io.paperdb.Paper;


/**
 * Created by User on 03.04.2018.
 */

public class MyAdapter extends BaseAdapter{

    private  ClickListener clickListener;
    Context ctx;
    ArrayList<Model> objects;



    public MyAdapter(Context ctx, ArrayList<Model> objects) {
        this.ctx = ctx;
        this.objects = objects;

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return objects.get(i).get_id();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(ctx, R.layout.item, null);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.linear);
        final ListView listView = (ListView) v.findViewById(R.id.list_item);
        final ImageView icon = (ImageView) v.findViewById(R.id.img);
        final TextView txName = (TextView) v.findViewById(R.id.name);
        final TextView txYear = (TextView) v.findViewById(R.id.nameCountry);
        final TextView value = (TextView) v.findViewById(R.id.pokupat);
        final TextView nominal = (TextView) v.findViewById(R.id.prodaj);

        if (String.valueOf(objects.get(i).getCharcode()).equals("USD")) {
            icon.setImageResource(R.drawable.america);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("EUR")) {
            icon.setImageResource(R.drawable.european);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("XDR")) {
            icon.setImageResource(R.mipmap.xdr);//not
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("CNY")) {
            icon.setImageResource(R.drawable.china);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("CHF")) {
            icon.setImageResource(R.drawable.switzerland);//not
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("RUB")) {
            icon.setImageResource(R.drawable.russia);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("UZS")) {
            icon.setImageResource(R.drawable.uzbekistan);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("KGS")) {//not
            icon.setImageResource(R.drawable.kyrgyzstan);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("KZT")) {
            icon.setImageResource(R.drawable.kazakhstan);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("BYN")) {
            icon.setImageResource(R.drawable.belarus);//not
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("IRR")) {
            icon.setImageResource(R.drawable.iran);//not
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("AFN")) {
            //icon.setImageResource(R.drawable.afghanistan);//not
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("PKR")) {
            icon.setImageResource(R.drawable.pakistan);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("TRY")) {
            icon.setImageResource(R.drawable.turkey);//not
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("TMT")) {
            icon.setImageResource(R.drawable.turkmenistan);//not
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("GBP")) {
            icon.setImageResource(R.drawable.united_uingdom);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("AUD")) {
            icon.setImageResource(R.drawable.australia);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("DKK")) {
            icon.setImageResource(R.drawable.denmark);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("ISK")) {
            icon.setImageResource(R.drawable.iceland);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("CAD")) {
            icon.setImageResource(R.drawable.canada);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("KWD")) {
            icon.setImageResource(R.drawable.kuwait);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("NOK")) {
            icon.setImageResource(R.drawable.norway);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("SGD")) {
            icon.setImageResource(R.drawable.singapore);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("SEK")) {
            icon.setImageResource(R.drawable.sweden);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("JPY")) {
            icon.setImageResource(R.drawable.japan);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("AZN")) {
            icon.setImageResource(R.drawable.azerbaijan);//not
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("AMD")) {//not
            icon.setImageResource(R.drawable.armenia);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("GEL")) {//not
            icon.setImageResource(R.drawable.georgia);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("MDL")) {
            icon.setImageResource(R.drawable.moldova);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("UAH")) {
            icon.setImageResource(R.drawable.ukraine);//not
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("AED")) {
            icon.setImageResource(R.drawable.denmark);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("SAR")) {
            icon.setImageResource(R.drawable.saudi_arabi);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("INR")) {
            icon.setImageResource(R.drawable.india);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("PLN")) {
            icon.setImageResource(R.drawable.poland);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("MYR")) {
            icon.setImageResource(R.drawable.malaysia);
        }
        if (String.valueOf(objects.get(i).getCharcode()).equals("THB")) {
            icon.setImageResource(R.drawable.thailand);//not
        }

        txName.setText(objects.get(i).getCharcode());
        txYear.setText(objects.get(i).getName());
        value.setText(String.valueOf(objects.get(i).getNominal()) + " " + objects.get(i).getCharcode());
        nominal.setText(String.valueOf(objects.get(i).getValue())+ " TJS");


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.itemClicked(objects.get(i), i);
            }
        });

        return v;
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public interface ClickListener{
        void itemClicked(Model item, int position);
        void itemLongClicked(Model item, int position);
    }
}
