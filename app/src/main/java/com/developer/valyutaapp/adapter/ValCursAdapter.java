package com.developer.valyutaapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.developer.valyutaapp.R;
import com.developer.valyutaapp.model.Valute;
import com.developer.valyutaapp.utils.ImageResource;

import java.util.List;

public class ValCursAdapter extends RecyclerView.Adapter<ValCursAdapter.ValutesHolder> {

    List<Valute> valutes;
    Context context;
    private ClickListener clickListener;

    public ValCursAdapter(List<Valute> valutes, Context context) {
        this.valutes = valutes;
        this.context = context;
    }

    @Override
    public ValutesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        ValutesHolder vh = new ValutesHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ValutesHolder holder, final int i) {

        Bitmap bt = ImageResource.getImageRes(context, String.valueOf(valutes.get(i).getCharCode()));
        holder.icon.setImageBitmap(bt);
        holder.tvName.setText(valutes.get(i).getCharCode());
        holder.tvYear.setText(valutes.get(i).getName());
        holder.tvValue.setText(String.valueOf(valutes.get(i).getNominal()) + " " + valutes.get(i).getCharCode());
        holder.tvNominal.setText(String.valueOf(valutes.get(i).getValue()) + " TJS");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.itemClicked(valutes.get(i), i);
                Log.d("TAG","Completed adap = " + valutes.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return valutes.size();
    }

    public class ValutesHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvYear, tvValue, tvNominal;
        ImageView icon;
        CardView cardView;

        public ValutesHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.iconValute);
            cardView = (CardView) v.findViewById(R.id.cardId);
            tvName = (TextView) v.findViewById(R.id.name);
            tvYear = (TextView) v.findViewById(R.id.nameCountry);
            tvValue = (TextView) v.findViewById(R.id.pokupat);
            tvNominal = (TextView) v.findViewById(R.id.prodaj);
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void itemClicked(Valute item, int position);
    }
}
