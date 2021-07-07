package com.developer.valyutaapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;

import com.developer.valyutaapp.R;
import com.developer.valyutaapp.model.Valute;
import com.developer.valyutaapp.utils.ImageResource;

import java.util.ArrayList;

public class DialogAdapter extends BaseAdapter {

    private ClickListener clickListener;
    Context context;
    ArrayList<Valute> valutes;

    public DialogAdapter(Context context, ArrayList<Valute> valutes) {
        this.context = context;
        this.valutes = valutes;
    }
    @Override
    public int getCount() {
        return valutes.size();
    }

    @Override
    public Object getItem(int i) {
        return valutes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return valutes.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_item, viewGroup, false);
        CardView cardView = (CardView) v.findViewById(R.id.item_dialog);
        ListView listView = (ListView) v.findViewById(R.id.list_dialog);
        ImageView icon = (ImageView) v.findViewById(R.id.img_flag);
        TextView txName = (TextView) v.findViewById(R.id.name_currency);

        Bitmap bt = ImageResource.getImageRes(context, String.valueOf(valutes.get(i).getCharCode()));
        icon.setImageBitmap(bt);
        txName.setText(String.valueOf(valutes.get(i).getName()));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.itemClicked(valutes.get(i), i);
            }
        });

        return v;
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public interface ClickListener{
        void itemClicked(Valute item, int position);
    }
}
