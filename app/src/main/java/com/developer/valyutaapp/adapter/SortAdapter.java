package com.developer.valyutaapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.developer.valyutaapp.R;
import com.developer.valyutaapp.model.Valute;
import com.developer.valyutaapp.ui.sort.SortActivity;
import com.developer.valyutaapp.utils.ImageResource;

import java.util.List;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ValutesHolder> {

    List<Valute> valutes;
    Context context;
    private ClickListener clickListener;

    public SortAdapter(List<Valute> valutes, Context context) {
        this.valutes = valutes;
        this.context = context;
    }

    @Override
    public ValutesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sort_item, parent, false);
        ValutesHolder vh = new ValutesHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ValutesHolder holder, final int i) {
        Bitmap bt = ImageResource.getImageRes(context, String.valueOf(valutes.get(i).getCharCode()));
        holder.icon.setImageBitmap(bt);
        holder.tvName.setText(valutes.get(i).getCharCode());

        if (valutes.get(i).getSortValute() == 1){
            holder.checkSort.setChecked(true);
        }else {
            holder.checkSort.setChecked(false);
        }

        holder.checkSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.checkSort.isChecked()) {
                    holder.checkSort.setChecked(true);
                    clickListener.itemClicked(valutes.get(i), i, 1);
                } else {
                    holder.checkSort.setChecked(false);
                    clickListener.itemClicked(valutes.get(i), i, 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return valutes.size();
    }

    public class ValutesHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView icon;
        CheckBox checkSort;
        CardView cardView;

        public ValutesHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.iconValute);
            cardView = (CardView) v.findViewById(R.id.itemCard);
            tvName = (TextView) v.findViewById(R.id.nameValute);
            checkSort = (CheckBox) v.findViewById(R.id.checkSort);
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void itemClicked(Valute item, int position, int sortValute);
    }

}