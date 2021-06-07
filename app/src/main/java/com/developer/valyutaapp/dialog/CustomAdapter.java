package com.developer.valyutaapp.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.developer.valyutaapp.Model;
import com.developer.valyutaapp.R;

import java.util.ArrayList;


/**
 * Created by User on 25.06.2018.
 */

public class CustomAdapter extends BaseAdapter {

    private ClickListener clickListener;
    Context ctx;
    ArrayList<Model> objects;

    public CustomAdapter(Context ctx, ArrayList<Model> objects) {
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
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_listview, viewGroup, false);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.item_dialog);
        ListView listView = (ListView) v.findViewById(R.id.list_dialog);
        ImageView icon = (ImageView) v.findViewById(R.id.img_flag);
        TextView txName = (TextView) v.findViewById(R.id.name_currency);
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
            icon.setImageResource(R.drawable.afghanistan);//not
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


        txName.setText(String.valueOf(objects.get(i).getName()));



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
