package com.developer.valyutaapp;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.valyutaapp.dialog.CustomAdapter;
import com.github.mikephil.charting.charts.LineChart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SettingWidget extends AppCompatActivity implements CustomAdapter.ClickListener {

    DatabaseHelper databaseHelper;
    AlertDialog.Builder alertdialog;
    AlertDialog dialog;
    List<Model> users;
    DatabaseAdapter databaseAdapter;
    private long userId = 1;
    CustomAdapter adapter;
    private ImageView icon, icon2;
    private TextView  value1, value2;
    private TextView charcode, charcode2, nominal1, nominal2, idval;
    LineChart chart;
    Button save;
    private String text = "";
    String left, right, perchar, pernom, perval;
    String nomi1, valu1, nomi2, valu2, charc1, charc2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_widget);
        icon = (ImageView) findViewById(R.id.image);
        chart = (LineChart) findViewById(R.id.barchar);

        save = (Button) findViewById(R.id.saveWidget);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        value1 = (TextView) findViewById(R.id.edit1);
        charcode = (TextView) findViewById(R.id.name);
        nominal1 = (TextView) findViewById(R.id.nomin1);


        icon2 = (ImageView) findViewById(R.id.image1);
        value2 = (TextView) findViewById(R.id.edit2);
        charcode2 = (TextView) findViewById(R.id.name1);
        nominal2 = (TextView) findViewById(R.id.nomin2);
        idval = (TextView) findViewById(R.id.idval);
        nominal1.setVisibility(View.GONE);
        nominal2.setVisibility(View.GONE);
        idval.setVisibility(View.GONE);

        databaseHelper = new DatabaseHelper(this);

        databaseAdapter = new DatabaseAdapter(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            databaseAdapter.open();
            final Model user = databaseAdapter.getUser(userId);
            Log.d("ssss", String.valueOf(user.getCharcode().equals("USD")));
            if (String.valueOf(user.getCharcode()).equals("USD")) {
                icon.setImageResource(R.drawable.america);
            }
            if (String.valueOf(user.getCharcode()).equals("EUR")) {
                icon.setImageResource(R.drawable.european);
            }
            if (String.valueOf(user.getCharcode()).equals("XDR")) {
                icon.setImageResource(R.mipmap.xdr);//not
            }
            if (String.valueOf(user.getCharcode()).equals("CNY")) {
                icon.setImageResource(R.drawable.china);
            }
            if (String.valueOf(user.getCharcode()).equals("CHF")) {
                icon.setImageResource(R.drawable.switzerland);//not
            }
            if (String.valueOf(user.getCharcode()).equals("RUB")) {
                icon.setImageResource(R.drawable.russia);
            }
            if (String.valueOf(user.getCharcode()).equals("UZS")) {
                icon.setImageResource(R.drawable.uzbekistan);
            }
            if (String.valueOf(user.getCharcode()).equals("KGS")) {//not
                icon.setImageResource(R.drawable.kyrgyzstan);
            }
            if (String.valueOf(user.getCharcode()).equals("KZT")) {
                icon.setImageResource(R.drawable.kazakhstan);
            }
            if (String.valueOf(user.getCharcode()).equals("BYN")) {
                icon.setImageResource(R.drawable.belarus);//not
            }
            if (String.valueOf(user.getCharcode()).equals("IRR")) {
                icon.setImageResource(R.drawable.iran);//not
            }
            if (String.valueOf(user.getCharcode()).equals("AFN")) {
                icon.setImageResource(R.drawable.afghanistan);//not
            }
            if (String.valueOf(user.getCharcode()).equals("PKR")) {
                icon.setImageResource(R.drawable.pakistan);
            }
            if (String.valueOf(user.getCharcode()).equals("TRY")) {
                icon.setImageResource(R.drawable.turkey);//not
            }
            if (String.valueOf(user.getCharcode()).equals("TMT")) {
                icon.setImageResource(R.drawable.turkmenistan);//not
            }
            if (String.valueOf(user.getCharcode()).equals("GBP")) {
                icon.setImageResource(R.drawable.united_uingdom);
            }
            if (String.valueOf(user.getCharcode()).equals("AUD")) {
                icon.setImageResource(R.drawable.australia);
            }
            if (String.valueOf(user.getCharcode()).equals("DKK")) {
                icon.setImageResource(R.drawable.denmark);
            }
            if (String.valueOf(user.getCharcode()).equals("ISK")) {
                icon.setImageResource(R.drawable.iceland);
            }
            if (String.valueOf(user.getCharcode()).equals("CAD")) {
                icon.setImageResource(R.drawable.canada);
            }
            if (String.valueOf(user.getCharcode()).equals("KWD")) {
                icon.setImageResource(R.drawable.kuwait);
            }
            if (String.valueOf(user.getCharcode()).equals("NOK")) {
                icon.setImageResource(R.drawable.norway);
            }
            if (String.valueOf(user.getCharcode()).equals("SGD")) {
                icon.setImageResource(R.drawable.singapore);
            }
            if (String.valueOf(user.getCharcode()).equals("SEK")) {
                icon.setImageResource(R.drawable.sweden);
            }
            if (String.valueOf(user.getCharcode()).equals("JPY")) {
                icon.setImageResource(R.drawable.japan);
            }
            if (String.valueOf(user.getCharcode()).equals("AZN")) {
                icon.setImageResource(R.drawable.azerbaijan);//not
            }
            if (String.valueOf(user.getCharcode()).equals("AMD")) {//not
                icon.setImageResource(R.drawable.armenia);
            }
            if (String.valueOf(user.getCharcode()).equals("GEL")) {//not
                icon.setImageResource(R.drawable.georgia);
            }
            if (String.valueOf(user.getCharcode()).equals("MDL")) {
                icon.setImageResource(R.drawable.moldova);
            }
            if (String.valueOf(user.getCharcode()).equals("UAH")) {
                icon.setImageResource(R.drawable.ukraine);//not
            }
            if (String.valueOf(user.getCharcode()).equals("AED")) {
                icon.setImageResource(R.drawable.denmark);
            }
            if (String.valueOf(user.getCharcode()).equals("SAR")) {
                icon.setImageResource(R.drawable.saudi_arabi);
            }
            if (String.valueOf(user.getCharcode()).equals("INR")) {
                icon.setImageResource(R.drawable.india);
            }
            if (String.valueOf(user.getCharcode()).equals("PLN")) {
                icon.setImageResource(R.drawable.poland);
            }
            if (String.valueOf(user.getCharcode()).equals("MYR")) {
                icon.setImageResource(R.drawable.malaysia);
            }
            if (String.valueOf(user.getCharcode()).equals("THB")) {
                icon.setImageResource(R.drawable.thailand);//not
            }

            value1.setText(String.valueOf(user.getNominal()));
            value2.setText(String.valueOf(user.getValue()));
            charcode.setText(String.valueOf(user.getCharcode()));
            idval.setText(String.valueOf(user.getId_val()));
            charcode2.setText("TJS");
            icon2.setImageResource(R.drawable.tajikistan);
            nomi1 = String.valueOf(user.getNominal());
            valu1 = String.valueOf(user.getValue());
            nomi2 = String.valueOf(user.getNominal());
            valu2 = String.valueOf(user.getValue());
            charc1 = String.valueOf(user.getCharcode());
            nominal1.setText(String.valueOf(user.getValue()));
            nominal2.setText(String.valueOf(user.getNominal()));
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val_dec;
                DecimalFormat decimalFormat = new DecimalFormat("#.####");
                String decimal = decimalFormat.format(Double.parseDouble(value2.getText().toString()));
                if (value1.getText().toString().length() < 3){
                    val_dec = decimalFormat.format(Double.parseDouble(value1.getText().toString()));
                }else {
                    val_dec = String.valueOf(value1.getText());
                }
                Paper.init(SettingWidget.this);
                Paper.book().write("charcode", charcode.getText());
                Log.d("widget", " = " + charcode.getText());
                Paper.book().write("charcode2", charcode2.getText());
                Log.d("widget", " = " + charcode2.getText());
                Paper.book().write("nominal", String.valueOf(val_dec));
                Log.d("widget", " = " + value1.getText());
                Paper.book().write("value", String.valueOf(decimal));
                Log.d("widget", " = " +decimal);
                Paper.book().write("dat", databaseAdapter.getDate().get_dates());
                Log.d("widget", " = " +  databaseAdapter.getDate().get_dates());
                Toast.makeText(SettingWidget.this, "Через минуту он будет установлен", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pereData(View view){
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        view.startAnimation(animAlpha);
        left = value1.getText().toString();
        right = value2.getText().toString();
        if (charcode2.getText().equals("TJS")) {
            perchar = charcode2.getText().toString();
            perval = value2.getText().toString();
            pernom = nominal2.getText().toString();

            charcode2.setText(charcode.getText().toString());
            nominal2.setText(nominal1.getText().toString());
            value2.setText(value1.getText().toString());

            charcode.setText(perchar);
            nominal1.setText(pernom);
            value1.setText(perval);

            if (charcode.getText().equals("USD")) {
                icon.setImageResource(R.drawable.america);
            }
            if (charcode.getText().equals("EUR")) {
                icon.setImageResource(R.drawable.european);
            }
            if (charcode.getText().equals("XDR")) {
                icon.setImageResource(R.mipmap.xdr);//not
            }
            if (charcode.getText().equals("CNY")) {
                icon.setImageResource(R.drawable.china);
            }
            if (charcode.getText().equals("CHF")) {
                icon.setImageResource(R.drawable.switzerland);//not
            }
            if (charcode.getText().equals("RUB")) {
                icon.setImageResource(R.drawable.russia);
            }
            if (charcode.getText().equals("UZS")) {
                icon.setImageResource(R.drawable.uzbekistan);
            }
            if (charcode.getText().equals("KGS")) {//not
                icon.setImageResource(R.drawable.kyrgyzstan);
            }
            if (charcode.getText().equals("KZT")) {
                icon.setImageResource(R.drawable.kazakhstan);
            }
            if (charcode.getText().equals("BYN")) {
                icon.setImageResource(R.drawable.belarus);//not
            }
            if (charcode.getText().equals("IRR")) {
                icon.setImageResource(R.drawable.iran);//not
            }
            if (charcode.getText().equals("AFN")) {
                icon.setImageResource(R.drawable.afghanistan);//not
            }
            if (charcode.getText().equals("PKR")) {
                icon.setImageResource(R.drawable.pakistan);
            }
            if (charcode.getText().equals("TRY")) {
                icon.setImageResource(R.drawable.turkey);//not
            }
            if (charcode.getText().equals("TMT")) {
                icon.setImageResource(R.drawable.turkmenistan);//not
            }
            if (charcode.getText().equals("GBP")) {
                icon.setImageResource(R.drawable.united_uingdom);
            }
            if (charcode.getText().equals("AUD")) {
                icon.setImageResource(R.drawable.australia);
            }
            if (charcode.getText().equals("DKK")) {
                icon.setImageResource(R.drawable.denmark);
            }
            if (charcode.getText().equals("ISK")) {
                icon.setImageResource(R.drawable.iceland);
            }
            if (charcode.getText().equals("CAD")) {
                icon.setImageResource(R.drawable.canada);
            }
            if (charcode.getText().equals("KWD")) {
                icon.setImageResource(R.drawable.kuwait);
            }
            if (charcode.getText().equals("NOK")) {
                icon.setImageResource(R.drawable.norway);
            }
            if (charcode.getText().equals("SGD")) {
                icon.setImageResource(R.drawable.singapore);
            }
            if (charcode.getText().equals("SEK")) {
                icon.setImageResource(R.drawable.sweden);
            }
            if (charcode.getText().equals("JPY")) {
                icon.setImageResource(R.drawable.japan);
            }
            if (charcode.getText().equals("AZN")) {
                icon.setImageResource(R.drawable.azerbaijan);//not
            }
            if (charcode.getText().equals("AMD")) {//not
                icon.setImageResource(R.drawable.armenia);
            }
            if (charcode.getText().equals("GEL")) {//not
                icon.setImageResource(R.drawable.georgia);
            }
            if (charcode.getText().equals("MDL")) {
                icon.setImageResource(R.drawable.moldova);
            }
            if (charcode.getText().equals("UAH")) {
                icon.setImageResource(R.drawable.ukraine);//not
            }
            if (charcode.getText().equals("AED")) {
                icon.setImageResource(R.drawable.denmark);
            }
            if (charcode.getText().equals("SAR")) {
                icon.setImageResource(R.drawable.saudi_arabi);
            }
            if (charcode.getText().equals("INR")) {
                icon.setImageResource(R.drawable.india);
            }
            if (charcode.getText().equals("PLN")) {
                icon.setImageResource(R.drawable.poland);
            }
            if (charcode.getText().equals("MYR")) {
                icon.setImageResource(R.drawable.malaysia);
            }
            if (charcode.getText().equals("THB")) {
                icon.setImageResource(R.drawable.thailand);//not
            }
            if (charcode.getText().equals("TJS")) {
                icon.setImageResource(R.drawable.tajikistan);
            }


            if (charcode2.getText().equals("USD")) {
                icon2.setImageResource(R.drawable.america);
            }
            if (charcode2.getText().equals("EUR")) {
                icon2.setImageResource(R.drawable.european);
            }
            if (charcode2.getText().equals("XDR")) {
                icon2.setImageResource(R.mipmap.xdr);//not
            }
            if (charcode2.getText().equals("CNY")) {
                icon2.setImageResource(R.drawable.china);
            }
            if (charcode2.getText().equals("CHF")) {
                icon2.setImageResource(R.drawable.switzerland);//not
            }
            if (charcode2.getText().equals("RUB")) {
                icon2.setImageResource(R.drawable.russia);
            }
            if (charcode2.getText().equals("UZS")) {
                icon2.setImageResource(R.drawable.uzbekistan);
            }
            if (charcode2.getText().equals("KGS")) {//not
                icon2.setImageResource(R.drawable.kyrgyzstan);
            }
            if (charcode2.getText().equals("KZT")) {
                icon2.setImageResource(R.drawable.kazakhstan);
            }
            if (charcode2.getText().equals("BYN")) {
                icon2.setImageResource(R.drawable.belarus);//not
            }
            if (charcode2.getText().equals("IRR")) {
                icon2.setImageResource(R.drawable.iran);//not
            }
            if (charcode2.getText().equals("AFN")) {
                icon2.setImageResource(R.drawable.afghanistan);//not
            }
            if (charcode2.getText().equals("PKR")) {
                icon2.setImageResource(R.drawable.pakistan);
            }
            if (charcode2.getText().equals("TRY")) {
                icon2.setImageResource(R.drawable.turkey);//not
            }
            if (charcode2.getText().equals("TMT")) {
                icon2.setImageResource(R.drawable.turkmenistan);//not
            }
            if (charcode2.getText().equals("GBP")) {
                icon2.setImageResource(R.drawable.united_uingdom);
            }
            if (charcode2.getText().equals("AUD")) {
                icon2.setImageResource(R.drawable.australia);
            }
            if (charcode2.getText().equals("DKK")) {
                icon2.setImageResource(R.drawable.denmark);
            }
            if (charcode2.getText().equals("ISK")) {
                icon2.setImageResource(R.drawable.iceland);
            }
            if (charcode.getText().equals("CAD")) {
                icon2.setImageResource(R.drawable.canada);
            }
            if (charcode2.getText().equals("KWD")) {
                icon2.setImageResource(R.drawable.kuwait);
            }
            if (charcode2.getText().equals("NOK")) {
                icon2.setImageResource(R.drawable.norway);
            }
            if (charcode2.getText().equals("SGD")) {
                icon2.setImageResource(R.drawable.singapore);
            }
            if (charcode2.getText().equals("SEK")) {
                icon2.setImageResource(R.drawable.sweden);
            }
            if (charcode2.getText().equals("JPY")) {
                icon2.setImageResource(R.drawable.japan);
            }
            if (charcode2.getText().equals("AZN")) {
                icon2.setImageResource(R.drawable.azerbaijan);//not
            }
            if (charcode2.getText().equals("AMD")) {//not
                icon2.setImageResource(R.drawable.armenia);
            }
            if (charcode2.getText().equals("GEL")) {//not
                icon2.setImageResource(R.drawable.georgia);
            }
            if (charcode2.getText().equals("MDL")) {
                icon2.setImageResource(R.drawable.moldova);
            }
            if (charcode2.getText().equals("UAH")) {
                icon2.setImageResource(R.drawable.ukraine);//not
            }
            if (charcode2.getText().equals("AED")) {
                icon2.setImageResource(R.drawable.denmark);
            }
            if (charcode2.getText().equals("SAR")) {
                icon2.setImageResource(R.drawable.saudi_arabi);
            }
            if (charcode2.getText().equals("INR")) {
                icon2.setImageResource(R.drawable.india);
            }
            if (charcode2.getText().equals("PLN")) {
                icon2.setImageResource(R.drawable.poland);
            }
            if (charcode2.getText().equals("MYR")) {
                icon2.setImageResource(R.drawable.malaysia);
            }
            if (charcode2.getText().equals("THB")) {
                icon2.setImageResource(R.drawable.thailand);//not
            }

        }else if (charcode.getText().equals("TJS")){
            perchar = charcode2.getText().toString();
            perval = value2.getText().toString();
            pernom = nominal2.getText().toString();

            charcode2.setText(charcode.getText().toString());
            nominal2.setText(nominal1.getText().toString());
            value2.setText(value1.getText().toString());

            charcode.setText(perchar);
            nominal1.setText(pernom);
            value1.setText(perval);

            if (charcode.getText().equals("USD")) {
                icon.setImageResource(R.drawable.america);
            }
            if (charcode.getText().equals("EUR")) {
                icon.setImageResource(R.drawable.european);
            }
            if (charcode.getText().equals("XDR")) {
                icon.setImageResource(R.mipmap.xdr);//not
            }
            if (charcode.getText().equals("CNY")) {
                icon.setImageResource(R.drawable.china);
            }
            if (charcode.getText().equals("CHF")) {
                icon.setImageResource(R.drawable.switzerland);//not
            }
            if (charcode.getText().equals("RUB")) {
                icon.setImageResource(R.drawable.russia);
            }
            if (charcode.getText().equals("UZS")) {
                icon.setImageResource(R.drawable.uzbekistan);
            }
            if (charcode.getText().equals("KGS")) {//not
                icon.setImageResource(R.drawable.kyrgyzstan);
            }
            if (charcode.getText().equals("KZT")) {
                icon.setImageResource(R.drawable.kazakhstan);
            }
            if (charcode.getText().equals("BYN")) {
                icon.setImageResource(R.drawable.belarus);//not
            }
            if (charcode.getText().equals("IRR")) {
                icon.setImageResource(R.drawable.iran);//not
            }
            if (charcode.getText().equals("AFN")) {
                icon.setImageResource(R.drawable.afghanistan);//not
            }
            if (charcode.getText().equals("PKR")) {
                icon.setImageResource(R.drawable.pakistan);
            }
            if (charcode.getText().equals("TRY")) {
                icon.setImageResource(R.drawable.turkey);//not
            }
            if (charcode.getText().equals("TMT")) {
                icon.setImageResource(R.drawable.turkmenistan);//not
            }
            if (charcode.getText().equals("GBP")) {
                icon.setImageResource(R.drawable.united_uingdom);
            }
            if (charcode.getText().equals("AUD")) {
                icon.setImageResource(R.drawable.australia);
            }
            if (charcode.getText().equals("DKK")) {
                icon.setImageResource(R.drawable.denmark);
            }
            if (charcode.getText().equals("ISK")) {
                icon.setImageResource(R.drawable.iceland);
            }
            if (charcode.getText().equals("CAD")) {
                icon.setImageResource(R.drawable.canada);
            }
            if (charcode.getText().equals("KWD")) {
                icon.setImageResource(R.drawable.kuwait);
            }
            if (charcode.getText().equals("NOK")) {
                icon.setImageResource(R.drawable.norway);
            }
            if (charcode.getText().equals("SGD")) {
                icon.setImageResource(R.drawable.singapore);
            }
            if (charcode.getText().equals("SEK")) {
                icon.setImageResource(R.drawable.sweden);
            }
            if (charcode.getText().equals("JPY")) {
                icon.setImageResource(R.drawable.japan);
            }
            if (charcode.getText().equals("AZN")) {
                icon.setImageResource(R.drawable.azerbaijan);//not
            }
            if (charcode.getText().equals("AMD")) {//not
                icon.setImageResource(R.drawable.armenia);
            }
            if (charcode.getText().equals("GEL")) {//not
                icon.setImageResource(R.drawable.georgia);
            }
            if (charcode.getText().equals("MDL")) {
                icon.setImageResource(R.drawable.moldova);
            }
            if (charcode.getText().equals("UAH")) {
                icon.setImageResource(R.drawable.ukraine);//not
            }
            if (charcode.getText().equals("AED")) {
                icon.setImageResource(R.drawable.denmark);
            }
            if (charcode.getText().equals("SAR")) {
                icon.setImageResource(R.drawable.saudi_arabi);
            }
            if (charcode.getText().equals("INR")) {
                icon.setImageResource(R.drawable.india);
            }
            if (charcode.getText().equals("PLN")) {
                icon.setImageResource(R.drawable.poland);
            }
            if (charcode.getText().equals("MYR")) {
                icon.setImageResource(R.drawable.malaysia);
            }
            if (charcode.getText().equals("THB")) {
                icon.setImageResource(R.drawable.thailand);//not
            }



            if (charcode2.getText().equals("USD")) {
                icon2.setImageResource(R.drawable.america);
            }
            if (charcode2.getText().equals("EUR")) {
                icon2.setImageResource(R.drawable.european);
            }
            if (charcode2.getText().equals("XDR")) {
                icon2.setImageResource(R.mipmap.xdr);//not
            }
            if (charcode2.getText().equals("CNY")) {
                icon2.setImageResource(R.drawable.china);
            }
            if (charcode2.getText().equals("CHF")) {
                icon2.setImageResource(R.drawable.switzerland);//not
            }
            if (charcode2.getText().equals("RUB")) {
                icon2.setImageResource(R.drawable.russia);
            }
            if (charcode2.getText().equals("UZS")) {
                icon2.setImageResource(R.drawable.uzbekistan);
            }
            if (charcode2.getText().equals("KGS")) {//not
                icon2.setImageResource(R.drawable.kyrgyzstan);
            }
            if (charcode2.getText().equals("KZT")) {
                icon2.setImageResource(R.drawable.kazakhstan);
            }
            if (charcode2.getText().equals("BYN")) {
                icon2.setImageResource(R.drawable.belarus);//not
            }
            if (charcode2.getText().equals("IRR")) {
                icon2.setImageResource(R.drawable.iran);//not
            }
            if (charcode2.getText().equals("AFN")) {
                icon2.setImageResource(R.drawable.afghanistan);//not
            }
            if (charcode2.getText().equals("PKR")) {
                icon2.setImageResource(R.drawable.pakistan);
            }
            if (charcode2.getText().equals("TRY")) {
                icon2.setImageResource(R.drawable.turkey);//not
            }
            if (charcode2.getText().equals("TMT")) {
                icon2.setImageResource(R.drawable.turkmenistan);//not
            }
            if (charcode2.getText().equals("GBP")) {
                icon2.setImageResource(R.drawable.united_uingdom);
            }
            if (charcode2.getText().equals("AUD")) {
                icon2.setImageResource(R.drawable.australia);
            }
            if (charcode2.getText().equals("DKK")) {
                icon2.setImageResource(R.drawable.denmark);
            }
            if (charcode2.getText().equals("ISK")) {
                icon2.setImageResource(R.drawable.iceland);
            }
            if (charcode.getText().equals("CAD")) {
                icon2.setImageResource(R.drawable.canada);
            }
            if (charcode2.getText().equals("KWD")) {
                icon2.setImageResource(R.drawable.kuwait);
            }
            if (charcode2.getText().equals("NOK")) {
                icon2.setImageResource(R.drawable.norway);
            }
            if (charcode2.getText().equals("SGD")) {
                icon2.setImageResource(R.drawable.singapore);
            }
            if (charcode2.getText().equals("SEK")) {
                icon2.setImageResource(R.drawable.sweden);
            }
            if (charcode2.getText().equals("JPY")) {
                icon2.setImageResource(R.drawable.japan);
            }
            if (charcode2.getText().equals("AZN")) {
                icon2.setImageResource(R.drawable.azerbaijan);//not
            }
            if (charcode2.getText().equals("AMD")) {//not
                icon2.setImageResource(R.drawable.armenia);
            }
            if (charcode2.getText().equals("GEL")) {//not
                icon2.setImageResource(R.drawable.georgia);
            }
            if (charcode2.getText().equals("MDL")) {
                icon2.setImageResource(R.drawable.moldova);
            }
            if (charcode2.getText().equals("UAH")) {
                icon2.setImageResource(R.drawable.ukraine);//not
            }
            if (charcode2.getText().equals("AED")) {
                icon2.setImageResource(R.drawable.denmark);
            }
            if (charcode2.getText().equals("SAR")) {
                icon2.setImageResource(R.drawable.saudi_arabi);
            }
            if (charcode2.getText().equals("INR")) {
                icon2.setImageResource(R.drawable.india);
            }
            if (charcode2.getText().equals("PLN")) {
                icon2.setImageResource(R.drawable.poland);
            }
            if (charcode2.getText().equals("MYR")) {
                icon2.setImageResource(R.drawable.malaysia);
            }
            if (charcode2.getText().equals("THB")) {
                icon2.setImageResource(R.drawable.thailand);//not
            }
            if (charcode2.getText().equals("TJS")) {
                icon2.setImageResource(R.drawable.tajikistan);
            }

        }else {
            perchar = charcode2.getText().toString();
            perval = value2.getText().toString();
            pernom = nominal2.getText().toString();

            charcode2.setText(charcode.getText().toString());
            nominal2.setText(nominal1.getText().toString());
            value2.setText(value1.getText().toString());

            charcode.setText(perchar);
            nominal1.setText(pernom);
            value1.setText(perval);
            left = value1.getText().toString();
            right = value2.getText().toString();
            if (charcode.getText().equals("USD")) {
                icon.setImageResource(R.drawable.america);
            }
            if (charcode.getText().equals("EUR")) {
                icon.setImageResource(R.drawable.european);
            }
            if (charcode.getText().equals("XDR")) {
                icon.setImageResource(R.mipmap.xdr);//not
            }
            if (charcode.getText().equals("CNY")) {
                icon.setImageResource(R.drawable.china);
            }
            if (charcode.getText().equals("CHF")) {
                icon.setImageResource(R.drawable.switzerland);//not
            }
            if (charcode.getText().equals("RUB")) {
                icon.setImageResource(R.drawable.russia);
            }
            if (charcode.getText().equals("UZS")) {
                icon.setImageResource(R.drawable.uzbekistan);
            }
            if (charcode.getText().equals("KGS")) {//not
                icon.setImageResource(R.drawable.kyrgyzstan);
            }
            if (charcode.getText().equals("KZT")) {
                icon.setImageResource(R.drawable.kazakhstan);
            }
            if (charcode.getText().equals("BYN")) {
                icon.setImageResource(R.drawable.belarus);//not
            }
            if (charcode.getText().equals("IRR")) {
                icon.setImageResource(R.drawable.iran);//not
            }
            if (charcode.getText().equals("AFN")) {
                icon.setImageResource(R.drawable.afghanistan);//not
            }
            if (charcode.getText().equals("PKR")) {
                icon.setImageResource(R.drawable.pakistan);
            }
            if (charcode.getText().equals("TRY")) {
                icon.setImageResource(R.drawable.turkey);//not
            }
            if (charcode.getText().equals("TMT")) {
                icon.setImageResource(R.drawable.turkmenistan);//not
            }
            if (charcode.getText().equals("GBP")) {
                icon.setImageResource(R.drawable.united_uingdom);
            }
            if (charcode.getText().equals("AUD")) {
                icon.setImageResource(R.drawable.australia);
            }
            if (charcode.getText().equals("DKK")) {
                icon.setImageResource(R.drawable.denmark);
            }
            if (charcode.getText().equals("ISK")) {
                icon.setImageResource(R.drawable.iceland);
            }
            if (charcode.getText().equals("CAD")) {
                icon.setImageResource(R.drawable.canada);
            }
            if (charcode.getText().equals("KWD")) {
                icon.setImageResource(R.drawable.kuwait);
            }
            if (charcode.getText().equals("NOK")) {
                icon.setImageResource(R.drawable.norway);
            }
            if (charcode.getText().equals("SGD")) {
                icon.setImageResource(R.drawable.singapore);
            }
            if (charcode.getText().equals("SEK")) {
                icon.setImageResource(R.drawable.sweden);
            }
            if (charcode.getText().equals("JPY")) {
                icon.setImageResource(R.drawable.japan);
            }
            if (charcode.getText().equals("AZN")) {
                icon.setImageResource(R.drawable.azerbaijan);//not
            }
            if (charcode.getText().equals("AMD")) {//not
                icon.setImageResource(R.drawable.armenia);
            }
            if (charcode.getText().equals("GEL")) {//not
                icon.setImageResource(R.drawable.georgia);
            }
            if (charcode.getText().equals("MDL")) {
                icon.setImageResource(R.drawable.moldova);
            }
            if (charcode.getText().equals("UAH")) {
                icon.setImageResource(R.drawable.ukraine);//not
            }
            if (charcode.getText().equals("AED")) {
                icon.setImageResource(R.drawable.denmark);
            }
            if (charcode.getText().equals("SAR")) {
                icon.setImageResource(R.drawable.saudi_arabi);
            }
            if (charcode.getText().equals("INR")) {
                icon.setImageResource(R.drawable.india);
            }
            if (charcode.getText().equals("PLN")) {
                icon.setImageResource(R.drawable.poland);
            }
            if (charcode.getText().equals("MYR")) {
                icon.setImageResource(R.drawable.malaysia);
            }
            if (charcode.getText().equals("THB")) {
                icon.setImageResource(R.drawable.thailand);//not
            }




            if (charcode2.getText().equals("USD")) {
                icon2.setImageResource(R.drawable.america);
            }
            if (charcode2.getText().equals("EUR")) {
                icon2.setImageResource(R.drawable.european);
            }
            if (charcode2.getText().equals("XDR")) {
                icon2.setImageResource(R.mipmap.xdr);//not
            }
            if (charcode2.getText().equals("CNY")) {
                icon2.setImageResource(R.drawable.china);
            }
            if (charcode2.getText().equals("CHF")) {
                icon2.setImageResource(R.drawable.switzerland);//not
            }
            if (charcode2.getText().equals("RUB")) {
                icon2.setImageResource(R.drawable.russia);
            }
            if (charcode2.getText().equals("UZS")) {
                icon2.setImageResource(R.drawable.uzbekistan);
            }
            if (charcode2.getText().equals("KGS")) {//not
                icon2.setImageResource(R.drawable.kyrgyzstan);
            }
            if (charcode2.getText().equals("KZT")) {
                icon2.setImageResource(R.drawable.kazakhstan);
            }
            if (charcode2.getText().equals("BYN")) {
                icon2.setImageResource(R.drawable.belarus);//not
            }
            if (charcode2.getText().equals("IRR")) {
                icon2.setImageResource(R.drawable.iran);//not
            }
            if (charcode2.getText().equals("AFN")) {
                icon2.setImageResource(R.drawable.afghanistan);//not
            }
            if (charcode2.getText().equals("PKR")) {
                icon2.setImageResource(R.drawable.pakistan);
            }
            if (charcode2.getText().equals("TRY")) {
                icon2.setImageResource(R.drawable.turkey);//not
            }
            if (charcode2.getText().equals("TMT")) {
                icon2.setImageResource(R.drawable.turkmenistan);//not
            }
            if (charcode2.getText().equals("GBP")) {
                icon2.setImageResource(R.drawable.united_uingdom);
            }
            if (charcode2.getText().equals("AUD")) {
                icon2.setImageResource(R.drawable.australia);
            }
            if (charcode2.getText().equals("DKK")) {
                icon2.setImageResource(R.drawable.denmark);
            }
            if (charcode2.getText().equals("ISK")) {
                icon2.setImageResource(R.drawable.iceland);
            }
            if (charcode.getText().equals("CAD")) {
                icon2.setImageResource(R.drawable.canada);
            }
            if (charcode2.getText().equals("KWD")) {
                icon2.setImageResource(R.drawable.kuwait);
            }
            if (charcode2.getText().equals("NOK")) {
                icon2.setImageResource(R.drawable.norway);
            }
            if (charcode2.getText().equals("SGD")) {
                icon2.setImageResource(R.drawable.singapore);
            }
            if (charcode2.getText().equals("SEK")) {
                icon2.setImageResource(R.drawable.sweden);
            }
            if (charcode2.getText().equals("JPY")) {
                icon2.setImageResource(R.drawable.japan);
            }
            if (charcode2.getText().equals("AZN")) {
                icon2.setImageResource(R.drawable.azerbaijan);//not
            }
            if (charcode2.getText().equals("AMD")) {//not
                icon2.setImageResource(R.drawable.armenia);
            }
            if (charcode2.getText().equals("GEL")) {//not
                icon2.setImageResource(R.drawable.georgia);
            }
            if (charcode2.getText().equals("MDL")) {
                icon2.setImageResource(R.drawable.moldova);
            }
            if (charcode2.getText().equals("UAH")) {
                icon2.setImageResource(R.drawable.ukraine);//not
            }
            if (charcode2.getText().equals("AED")) {
                icon2.setImageResource(R.drawable.denmark);
            }
            if (charcode2.getText().equals("SAR")) {
                icon2.setImageResource(R.drawable.saudi_arabi);
            }
            if (charcode2.getText().equals("INR")) {
                icon2.setImageResource(R.drawable.india);
            }
            if (charcode2.getText().equals("PLN")) {
                icon2.setImageResource(R.drawable.poland);
            }
            if (charcode2.getText().equals("MYR")) {
                icon2.setImageResource(R.drawable.malaysia);
            }
            if (charcode2.getText().equals("THB")) {
                icon2.setImageResource(R.drawable.thailand);//not
            }

        }
    }

    public void getData() {
        if (userId > 0) {
            // получаем элемент по id из бд
            databaseAdapter.open();
            final Model user = databaseAdapter.getUser(userId);
            if (text == "open_one") {
                if (String.valueOf(user.getCharcode()).equals("USD")) {
                    icon.setImageResource(R.drawable.america);
                }
                if (String.valueOf(user.getCharcode()).equals("EUR")) {
                    icon.setImageResource(R.drawable.european);
                }
                if (String.valueOf(user.getCharcode()).equals("XDR")) {
                    icon.setImageResource(R.mipmap.xdr);//not
                }
                if (String.valueOf(user.getCharcode()).equals("CNY")) {
                    icon.setImageResource(R.drawable.china);
                }
                if (String.valueOf(user.getCharcode()).equals("CHF")) {
                    icon.setImageResource(R.drawable.switzerland);//not
                }
                if (String.valueOf(user.getCharcode()).equals("RUB")) {
                    icon.setImageResource(R.drawable.russia);
                }
                if (String.valueOf(user.getCharcode()).equals("UZS")) {
                    icon.setImageResource(R.drawable.uzbekistan);
                }
                if (String.valueOf(user.getCharcode()).equals("KGS")) {//not
                    icon.setImageResource(R.drawable.kyrgyzstan);
                }
                if (String.valueOf(user.getCharcode()).equals("KZT")) {
                    icon.setImageResource(R.drawable.kazakhstan);
                }
                if (String.valueOf(user.getCharcode()).equals("BYN")) {
                    icon.setImageResource(R.drawable.belarus);//not
                }
                if (String.valueOf(user.getCharcode()).equals("IRR")) {
                    icon.setImageResource(R.drawable.iran);//not
                }
                if (String.valueOf(user.getCharcode()).equals("AFN")) {
                    icon.setImageResource(R.drawable.afghanistan);//not
                }
                if (String.valueOf(user.getCharcode()).equals("PKR")) {
                    icon.setImageResource(R.drawable.pakistan);
                }
                if (String.valueOf(user.getCharcode()).equals("TRY")) {
                    icon.setImageResource(R.drawable.turkey);//not
                }
                if (String.valueOf(user.getCharcode()).equals("TMT")) {
                    icon.setImageResource(R.drawable.turkmenistan);//not
                }
                if (String.valueOf(user.getCharcode()).equals("GBP")) {
                    icon.setImageResource(R.drawable.georgia);
                }
                if (String.valueOf(user.getCharcode()).equals("AUD")) {
                    icon.setImageResource(R.drawable.australia);
                }
                if (String.valueOf(user.getCharcode()).equals("DKK")) {
                    icon.setImageResource(R.drawable.denmark);
                }
                if (String.valueOf(user.getCharcode()).equals("ISK")) {
                    icon.setImageResource(R.drawable.iceland);
                }
                if (String.valueOf(user.getCharcode()).equals("CAD")) {
                    icon.setImageResource(R.drawable.canada);
                }
                if (String.valueOf(user.getCharcode()).equals("KWD")) {
                    icon.setImageResource(R.drawable.kuwait);
                }
                if (String.valueOf(user.getCharcode()).equals("NOK")) {
                    icon.setImageResource(R.drawable.norway);
                }
                if (String.valueOf(user.getCharcode()).equals("SGD")) {
                    icon.setImageResource(R.drawable.singapore);
                }
                if (String.valueOf(user.getCharcode()).equals("SEK")) {
                    icon.setImageResource(R.drawable.sweden);
                }
                if (String.valueOf(user.getCharcode()).equals("JPY")) {
                    icon.setImageResource(R.drawable.japan);
                }
                if (String.valueOf(user.getCharcode()).equals("AZN")) {
                    icon.setImageResource(R.drawable.azerbaijan);//not
                }
                if (String.valueOf(user.getCharcode()).equals("AMD")) {//not
                    icon.setImageResource(R.drawable.armenia);
                }
                if (String.valueOf(user.getCharcode()).equals("GEL")) {//not
                    icon.setImageResource(R.drawable.georgia);
                }
                if (String.valueOf(user.getCharcode()).equals("MDL")) {
                    icon.setImageResource(R.drawable.moldova);
                }
                if (String.valueOf(user.getCharcode()).equals("UAH")) {
                    icon.setImageResource(R.drawable.ukraine);//not
                }
                if (String.valueOf(user.getCharcode()).equals("AED")) {
                    icon.setImageResource(R.drawable.denmark);
                }
                if (String.valueOf(user.getCharcode()).equals("SAR")) {
                    icon.setImageResource(R.drawable.saudi_arabi);
                }
                if (String.valueOf(user.getCharcode()).equals("INR")) {
                    icon.setImageResource(R.drawable.india);
                }
                if (String.valueOf(user.getCharcode()).equals("PLN")) {
                    icon.setImageResource(R.drawable.poland);
                }
                if (String.valueOf(user.getCharcode()).equals("MYR")) {
                    icon.setImageResource(R.drawable.malaysia);
                }
                if (String.valueOf(user.getCharcode()).equals("THB")) {
                    icon.setImageResource(R.drawable.thailand);//not
                }
                if (charcode.getText().equals("TJS")){
                    icon.setImageResource(R.drawable.tajikistan);
                }

                value1.setText(String.valueOf(user.getNominal()));
                nominal1.setText(String.valueOf(user.getValue()));
                charcode.setText(String.valueOf(user.getCharcode()));
                nomi1 = String.valueOf(user.getNominal());
                valu1 = String.valueOf(user.getValue());
                charc1 = String.valueOf(user.getCharcode());
               // valu2 = String.valueOf(user.getValue());
                if (charcode2.getText().equals("TJS")) {
                    value2.setText(user.getValue());
                }
                if (charcode2.getText().equals("TJS")) {

                }else {
                    double nom1 = 0, nom2 = 0, val1 = 0, val2 = 0, sum = 1;
                    nom1 = Double.parseDouble(value1.getText().toString());
                    nom2 = Double.parseDouble(nomi2);
                    val1 = Double.parseDouble(nominal1.getText().toString());
                    val2 = Double.parseDouble(valu2);
                    sum = (nom1 * val1 * nom2) /val2;
                    value2.setText("" + sum);
                }


            } else if (text.equals("open_two")) {

                if (String.valueOf(user.getCharcode()).equals("USD")) {
                    icon2.setImageResource(R.drawable.america);
                }
                if (String.valueOf(user.getCharcode()).equals("EUR")) {
                    icon2.setImageResource(R.drawable.european);
                }
                if (String.valueOf(user.getCharcode()).equals("XDR")) {
                    icon2.setImageResource(R.mipmap.xdr);//not
                }
                if (String.valueOf(user.getCharcode()).equals("CNY")) {
                    icon2.setImageResource(R.drawable.china);
                }
                if (String.valueOf(user.getCharcode()).equals("CHF")) {
                    icon2.setImageResource(R.drawable.switzerland);//not
                }
                if (String.valueOf(user.getCharcode()).equals("RUB")) {
                    icon2.setImageResource(R.drawable.russia);
                }
                if (String.valueOf(user.getCharcode()).equals("UZS")) {
                    icon2.setImageResource(R.drawable.uzbekistan);
                }
                if (String.valueOf(user.getCharcode()).equals("KGS")) {//not
                    icon2.setImageResource(R.drawable.kyrgyzstan);
                }
                if (String.valueOf(user.getCharcode()).equals("KZT")) {
                    icon2.setImageResource(R.drawable.kazakhstan);
                }
                if (String.valueOf(user.getCharcode()).equals("BYN")) {
                    icon2.setImageResource(R.drawable.belarus);//not
                }
                if (String.valueOf(user.getCharcode()).equals("IRR")) {
                    icon2.setImageResource(R.drawable.iran);//not
                }
                if (String.valueOf(user.getCharcode()).equals("AFN")) {
                    icon2.setImageResource(R.drawable.afghanistan);//not
                }
                if (String.valueOf(user.getCharcode()).equals("PKR")) {
                    icon2.setImageResource(R.drawable.pakistan);
                }
                if (String.valueOf(user.getCharcode()).equals("TRY")) {
                    icon2.setImageResource(R.drawable.turkey);//not
                }
                if (String.valueOf(user.getCharcode()).equals("TMT")) {
                    icon2.setImageResource(R.drawable.turkmenistan);//not
                }
                if (String.valueOf(user.getCharcode()).equals("GBP")) {
                    icon2.setImageResource(R.drawable.georgia);
                }
                if (String.valueOf(user.getCharcode()).equals("AUD")) {
                    icon2.setImageResource(R.drawable.australia);
                }
                if (String.valueOf(user.getCharcode()).equals("DKK")) {
                    icon2.setImageResource(R.drawable.denmark);
                }
                if (String.valueOf(user.getCharcode()).equals("ISK")) {
                    icon2.setImageResource(R.drawable.iceland);
                }
                if (String.valueOf(user.getCharcode()).equals("CAD")) {
                    icon2.setImageResource(R.drawable.canada);
                }
                if (String.valueOf(user.getCharcode()).equals("KWD")) {
                    icon2.setImageResource(R.drawable.kuwait);
                }
                if (String.valueOf(user.getCharcode()).equals("NOK")) {
                    icon2.setImageResource(R.drawable.norway);
                }
                if (String.valueOf(user.getCharcode()).equals("SGD")) {
                    icon2.setImageResource(R.drawable.singapore);
                }
                if (String.valueOf(user.getCharcode()).equals("SEK")) {
                    icon2.setImageResource(R.drawable.sweden);
                }
                if (String.valueOf(user.getCharcode()).equals("JPY")) {
                    icon2.setImageResource(R.drawable.japan);
                }
                if (String.valueOf(user.getCharcode()).equals("AZN")) {
                    icon2.setImageResource(R.drawable.azerbaijan);//not
                }
                if (String.valueOf(user.getCharcode()).equals("AMD")) {//not
                    icon2.setImageResource(R.drawable.armenia);
                }
                if (String.valueOf(user.getCharcode()).equals("GEL")) {//not
                    icon2.setImageResource(R.drawable.georgia);
                }
                if (String.valueOf(user.getCharcode()).equals("MDL")) {
                    icon2.setImageResource(R.drawable.moldova);
                }
                if (String.valueOf(user.getCharcode()).equals("UAH")) {
                    icon2.setImageResource(R.drawable.ukraine);//not
                }
                if (String.valueOf(user.getCharcode()).equals("AED")) {
                    icon2.setImageResource(R.drawable.denmark);
                }
                if (String.valueOf(user.getCharcode()).equals("SAR")) {
                    icon2.setImageResource(R.drawable.saudi_arabi);
                }
                if (String.valueOf(user.getCharcode()).equals("INR")) {
                    icon2.setImageResource(R.drawable.india);
                }
                if (String.valueOf(user.getCharcode()).equals("PLN")) {
                    icon2.setImageResource(R.drawable.poland);
                }
                if (String.valueOf(user.getCharcode()).equals("MYR")) {
                    icon2.setImageResource(R.drawable.malaysia);
                }
                if (String.valueOf(user.getCharcode()).equals("THB")) {
                    icon2.setImageResource(R.drawable.thailand);//not
                }
                if (charcode.getText().equals("TJS")){
                    icon2.setImageResource(R.drawable.tajikistan);
                }


                value2.setText(String.valueOf(user.getValue()));
                nominal2.setText(String.valueOf(user.getNominal()));
                charcode2.setText(String.valueOf(user.getCharcode()));
                nomi2 = String.valueOf(user.getNominal());
                valu2 = String.valueOf(user.getValue());
                charc2 = String.valueOf(user.getCharcode());

                if (charcode.getText().equals("TJS")){

                }else {
                    double nom1 = 0, nom2 = 0, val1 = 0, val2 = 0, sum = 1;
                    nom1 = Double.parseDouble(value1.getText().toString());
                    nom2 = Double.parseDouble(nominal2.getText().toString());
                    val1 = Double.parseDouble(nominal1.getText().toString());
                    val2 = Double.parseDouble(valu2);
                    sum = (nom1 * val1 * nom2) / val2;
                    value2.setText("" + sum);
                }


            }
        }
    }

    public void open_dialog(View v){
        alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Все валюты");
        text = "open_one";
        Log.d("text = " , text);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_item, null);
        ListView listView = (ListView) row.findViewById(R.id.list_dialog);
        databaseAdapter = new DatabaseAdapter(this);
        databaseAdapter.open();
        users = databaseAdapter.getUsers();
        adapter = new CustomAdapter(this, (ArrayList<Model>) users);
        adapter.setClickListener(this);
        listView.setAdapter(adapter);
        alertdialog.setView(row);
        dialog = alertdialog.create();
        dialog.show();
        databaseAdapter.close();


    }
    public void open_dialog_get(View v){
        alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Все валюты");
        Log.d("text = " , text);
        text = "open_two";
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_item, null);
        ListView listView = (ListView) row.findViewById(R.id.list_dialog);
        databaseAdapter = new DatabaseAdapter(this);
        databaseAdapter.open();
        users = databaseAdapter.getUsers();
        adapter = new CustomAdapter(this, (ArrayList<Model>) users);
        adapter.setClickListener(this);
        listView.setAdapter(adapter);
        alertdialog.setView(row);
        dialog = alertdialog.create();

        dialog.show();
        databaseAdapter.close();




    }
    @Override
    public void itemClicked(Model item, int positions) {
        userId = item.get_id();
        dialog.hide();
        getData();
    }

    @Override
    public void itemLongClicked(Model item, int position) {

    }
}
