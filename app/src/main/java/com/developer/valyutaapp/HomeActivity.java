package com.developer.valyutaapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.valyutaapp.dialog.AboutActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import java.util.Calendar;
import java.util.Locale;

import io.paperdb.Paper;

/**
 * Created by User on 07.06.2018.
 */

public class HomeActivity extends AppCompatActivity implements MyAdapter.ClickListener, RequestVygruzka.ResponseListener{

    private InterstitialAd mInterstitialAd;
    RequestVygruzka requestVygruzka;
    Context context;
    MyAdapter myAdapter;
    DatabaseAdapter adapter;
    long userId;
    private ListView userList;
    final int DIALOG_RU = 1;
    String data[] = {"English", "Русский"};
    Setting setting;
    SQLiteDatabase database;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ico);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        setting = new Setting(this);
        MobileAds.initialize(this, "ca-app-pub-7733697700012664~1688146414");
        context = this;
        adapter = new DatabaseAdapter(this);
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        userList = (ListView) findViewById(R.id.list_item);

        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-7733697700012664/4842236445");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.show();

        if (setting.getBool().equals("1")) {
             startService(new Intent(this, AutoService.class));
        }else if (setting.getBool().equals("0")){
            stopService(new Intent(this, AutoService.class));
        }

        if (setting.getValue().equals("1")){
            startService(new Intent(this, UpdateService.class));
        }else if (setting.getValue().equals("0")){
            stopService(new Intent(this, UpdateService.class));
        }
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

    }



    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        switch (id) {
            case DIALOG_RU:
                adb.setTitle(R.string.language);
                adb.setSingleChoiceItems(data, -1, myClickListener);
                break;
        }
        adb.setPositiveButton(R.string.ok, myClickListener);
        adb.setNeutralButton("Chancel", myClickListener);
        return adb.create();
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            ListView lv = ((AlertDialog) dialog).getListView();
            switch (which) {
                case 0:
                    setLocale("en");
                    break;
                case 1:
                    setLocale("ru");
                    break;
            }
        }
    };

    public void setLocale(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale locale = new Locale(lang);
        setting.saveLang(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
        protected boolean isOnline() {
            String cs = Context.CONNECTIVITY_SERVICE;
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(cs);
            if (cm.getActiveNetworkInfo() == null) {
                return false;
            } else {
                return true;
            }
        }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();
        Log.d("getUser", " List");
        List<Model> users = adapter.getUsers();
        myAdapter = new MyAdapter(this, (ArrayList<Model>) users);
        myAdapter.setClickListener(this);
        userList.setAdapter(myAdapter);
        adapter.close();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("_id");
            this.finish();
            Log.d("finish", "HOME");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (item.isChecked()){
            item.setChecked(false);
        }else {
            item.setChecked(true);
        }
        if (id == R.id.setting) {
            Intent intent = new Intent(this, PrefActivity.class);
            startActivity(intent);
        }else if (id == R.id.update){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String datef = df.format(Calendar.getInstance().getTime());
            Model getDates = adapter.getDataDate();
            ModelDate modelDate = adapter.getDate();
            boolean model = adapter.getDataNULL();
            ContentValues cv = new ContentValues();

            if ( !isOnline() ){
                Toast.makeText(context, "Нет соединения с интернетом!",Toast.LENGTH_LONG).show();
            }else {
                if (model == false) {
                    if (getDates.getDate().toString().equals(datef)) {
                        DBSimple.deleteRow(context);
                        Log.d("remove ", " = " +  DBSimple.deleteRow(context));
                    }
                    if (modelDate.get_dates().toString().equals(datef)){
                        DBSimple.deleteDate(context);

                    }
                    Toast.makeText(context, "Есть соединения с интернетом!", Toast.LENGTH_LONG).show();
                    cv.put(DatabaseHelper.COLUMN_DATE, datef);
                    database.insert(DatabaseHelper.TABLE_DATE, null, cv);
                    requestVygruzka = new RequestVygruzka(context);//Получение данние
                    requestVygruzka.setResponseListener(this);
                    requestVygruzka.execute();
                    Toast.makeText(context, "Обновлено данные", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Есть соединения с интернетом!", Toast.LENGTH_LONG).show();
                    cv.put(DatabaseHelper.COLUMN_DATE, datef);
                    database.insert(DatabaseHelper.TABLE_DATE, null, cv);
                    requestVygruzka = new RequestVygruzka(context);//Получение данние
                    requestVygruzka.setResponseListener(this);
                    requestVygruzka.execute();
                    Toast.makeText(context, "Обновлено данные", Toast.LENGTH_SHORT).show();
                }
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

            }
        }else if (id == R.id.about){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClicked(Model item, int position) {
        if(item!=null) {
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            intent.putExtra("id", item.get_id());
            intent.putExtra("click", 25);
            startActivity(intent);
            Log.d("pos" , " = " + item.get_id());
        }
    }

    @Override
    public void itemLongClicked(Model item, int position) {

    }

    @Override
    public void responseVygruzkiCome(boolean res, String message) {
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();
        Log.d("getUser"," List");
        List<Model> users = adapter.getUsers();
        myAdapter = new MyAdapter(this, (ArrayList<Model>) users);
        myAdapter.setClickListener(this);
        userList.setAdapter(myAdapter);
        adapter.close();

    }

    @Override
    public void progressMessage(String message) {

    }
}