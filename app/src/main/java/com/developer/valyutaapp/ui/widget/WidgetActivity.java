package com.developer.valyutaapp.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.developer.valyutaapp.R;
import com.developer.valyutaapp.adapter.DialogAdapter;
import com.developer.valyutaapp.model.Valute;
import com.developer.valyutaapp.utils.ImageResource;
import com.developer.valyutaapp.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class WidgetActivity extends AppCompatActivity implements WidgetViewInterface, DialogAdapter.ClickListener {

    AlertDialog.Builder alertdialog;
    AlertDialog dialog;
    ArrayList<Valute> valutes;
    private int valuteId = 840;
    DialogAdapter adapter;

    @BindView(R.id.iconValute1) ImageView iconValute1;
    @BindView(R.id.iconValute2) ImageView iconValute2;
    @BindView(R.id.tvNominal) TextView tvNominal;
    @BindView(R.id.tvValue) TextView tvValue;

    @BindView(R.id.name1) TextView code1;
    @BindView(R.id.name2) TextView code2;

    @BindView(R.id.saveWidget) Button saveWidget;

    @Nullable
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private WidgetPresenter widgetPresenter;
    private String TAG = "WidgetPresenter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        ButterKnife.bind(this);
        valutes = new ArrayList<>();

        setupMVP();
        getValuteById();
        getValuteList();

        saveWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val_dec;
                DecimalFormat decimalFormat = new DecimalFormat("#.####");
                String decimal = decimalFormat.format(Double.parseDouble(tvValue.getText().toString()));
                if (tvNominal.getText().toString().length() < 3){
                    val_dec = decimalFormat.format(Double.parseDouble(tvNominal.getText().toString()));
                }else {
                    val_dec = String.valueOf(tvNominal.getText());
                }
                Paper.init(WidgetActivity.this);
                Paper.book().write("charcode", code1.getText());
                Log.d("widget", " = " + code1.getText());
                Paper.book().write("charcode2", code2.getText());
                Log.d("widget", " = " + code2.getText());
                Paper.book().write("nominal", String.valueOf(val_dec));
                Log.d("widget", " = " + tvNominal.getText());
                Paper.book().write("value", String.valueOf(decimal));
                Log.d("widget", " = " +decimal);
                Paper.book().write("dat", Utils.getDate());
                Toast.makeText(WidgetActivity.this, "Через минуту он будет установлен", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setupMVP() {
        widgetPresenter = new WidgetPresenter(this.getApplicationContext(), this);
    }

    private void getValuteById() {
        widgetPresenter.getValuteById(valuteId);
    }

    private void getValuteList() {
        widgetPresenter.getValutes();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(WidgetActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayValuteWithId(Valute valute) {
        Bitmap bt = ImageResource.getImageRes(this, String.valueOf(valute.getCharCode()));
        iconValute1.setImageBitmap(bt);
        tvNominal.setText(String.valueOf(valute.getNominal()));
        tvValue.setText(String.valueOf(valute.getValue()));
        code1.setText(String.valueOf(valute.getCharCode()));
        code2.setText("TJS");
        iconValute2.setImageResource(R.drawable.tajikistan);
    }

    @Override
    public void displayValutes(List<Valute> valute) {
        valutes.addAll(valute);
    }

    @Override
    public void displayError(String e) {
        showToast(e);
    }

    public void dialogValutes(View v){
        alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Все валюты");
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_item, null);
        ListView listView = (ListView) row.findViewById(R.id.list_dialog);
        adapter = new DialogAdapter(this, valutes);
        adapter.setClickListener(WidgetActivity.this);
        listView.setAdapter(adapter);
        alertdialog.setView(row);
        dialog = alertdialog.create();
        dialog.show();
    }

    @Override
    public void itemClicked(Valute item, int position) {
        valuteId = item.getId();
        dialog.hide();
        getValuteById();
    }

}