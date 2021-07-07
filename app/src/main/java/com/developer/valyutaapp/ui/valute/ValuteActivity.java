package com.developer.valyutaapp.ui.valute;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ValuteActivity extends AppCompatActivity implements ValuteViewInterface, DialogAdapter.ClickListener{

    AlertDialog.Builder alertdialog;
    AlertDialog dialog;
    ArrayList<Valute> valutes;
    private long valuteId = 0;
    DialogAdapter adapter;

    @BindView(R.id.iconValute1) ImageView iconValute1;
    @BindView(R.id.iconValute2) ImageView iconValute2;
    @BindView(R.id.clear) ImageView clear;
    @BindView(R.id.change) ImageView change;
    @BindView(R.id.edit1) EditText value1;
    @BindView(R.id.edit2) EditText value2;

    @BindView(R.id.name1) TextView code1;
    @BindView(R.id.name2) TextView code2;
    @Nullable
    @BindView(R.id.progressBar) ProgressBar progressBar;

    public static double nominal = 0;
    public static double value = 0;

    //LineChart chart;

    private ValutePresenter valutePresenter;

    private String TAG = "ValuteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valute);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valuteId = extras.getInt("id");
        }

        valutes = new ArrayList<>();

        setupMVP();
        getValuteById();
        getValuteList();

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value1.setText("");
                value2.setText("");
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value1.isEnabled() && hasWindowFocus()){
                    value2.requestFocus();
                }
                if (!value1.isEnabled() && !hasWindowFocus()){
                    value1.requestFocus();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (value1.isEnabled()) {
            value1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() == 0) {
                        return;
                    } else if (code2.getText().equals("TJS")) {
                        double sum = 0;
                        nominal = Double.parseDouble(String.valueOf(value1.getText()));
                        sum = Utils.mathNominal(nominal, value);
                        Log.d("sum", nominal + "*" + value + " = " + sum);
                        value2.setText(String.valueOf(sum));
                    }
                }
            });
        }else {
            value2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() == 0) {
                        return;
                    } else if (code2.getText().equals("TJS")) {
                        double sum = 0, valueDynamic;
                        valueDynamic = Double.parseDouble(String.valueOf(value2.getText()));
                        sum = Utils.mathValue(nominal, value, valueDynamic);
                        Log.d("sum", nominal + "*" + value + " = " + sum);
                        value1.setText(String.valueOf(sum));
                    }
                }
            });
        }
    }

    private void setupMVP() {
        valutePresenter = new ValutePresenter(this.getApplicationContext(), this);
    }

    private void getValuteById() {
        valutePresenter.getValuteById((int) valuteId);
    }

    private void getValuteList() {
        valutePresenter.getValutes();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(ValuteActivity.this,str,Toast.LENGTH_LONG).show();
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
        value = Double.parseDouble(valute.getValue());
        value1.setText(String.valueOf(valute.getNominal()));
        value2.setText(String.valueOf(valute.getValue()));
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
        adapter.setClickListener(this);
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