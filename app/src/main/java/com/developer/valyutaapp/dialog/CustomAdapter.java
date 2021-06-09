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
