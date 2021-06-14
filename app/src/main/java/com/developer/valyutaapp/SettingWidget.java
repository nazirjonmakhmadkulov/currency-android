package com.developer.valyutaapp;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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

            if (charcode.getText().equals("THB")) {
                icon.setImageResource(R.drawable.thailand);//not
            }



            if (charcode2.getText().equals("USD")) {
                icon2.setImageResource(R.drawable.america);
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

            if (charcode.getText().equals("THB")) {
                icon.setImageResource(R.drawable.thailand);//not
            }




            if (charcode2.getText().equals("USD")) {
                icon2.setImageResource(R.drawable.america);
            }

            if (charcode2.getText().equals("THB")) {
                icon2.setImageResource(R.drawable.thailand);//not
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
    }

    @Override
    public void itemLongClicked(Model item, int position) {

    }
}
