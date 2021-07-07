package com.developer.valyutaapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.valyutaapp.R;
import com.developer.valyutaapp.adapter.ValCursAdapter;
import com.developer.valyutaapp.data.local_data_source.database.ValuteDataSource;
import com.developer.valyutaapp.data.local_data_source.database.ValuteDatabase;
import com.developer.valyutaapp.data.local_data_source.repository.ValuteRespository;
import com.developer.valyutaapp.model.Valute;
import com.developer.valyutaapp.service.auto.AutoService;
import com.developer.valyutaapp.ui.setting.SettingActivity;
import com.developer.valyutaapp.ui.valute.ValuteActivity;
import com.developer.valyutaapp.utils.SheredPreference;
import com.developer.valyutaapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements MainViewInterface, ValCursAdapter.ClickListener {

    @Nullable
    @BindView(R.id.recyclerValCurs)
    RecyclerView recyclerValCurs;

    @Nullable
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String TAG = "MainActivity";

    ValCursAdapter adapter;
    MainPresenter mainPresenter;
    private ValuteRespository valuteRespository;

    List<Valute> valuteList = new ArrayList<>();

    SheredPreference sheredPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new ValCursAdapter(valuteList, MainActivity.this);
        adapter.setClickListener(this);
        recyclerValCurs.setAdapter(adapter);

        ValuteDatabase valuteDatabase = ValuteDatabase.getInstance(this); //create db
        Log.d("Database ", valuteDatabase.toString());
        valuteRespository = ValuteRespository.getInstance(ValuteDataSource.getInstance(valuteDatabase.valuteDAO()));

        sheredPreference = new SheredPreference(this);

        if (sheredPreference.getBool().equals("1")) {
            startService(new Intent(this, AutoService.class));
        }else if (sheredPreference.getBool().equals("0")){
            stopService(new Intent(this, AutoService.class));
        }


        setupMVP();
        setupViews();
        getValuteList();
    }

    private void setupMVP() {
        mainPresenter = new MainPresenter(this.getApplicationContext(), this, valuteRespository);
    }

    private void setupViews(){
        recyclerValCurs.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getValuteList() {
        if (Utils.hasConnection(this)) {
            mainPresenter.getValutesRemote();
        } else {
            mainPresenter.getValutesLocal();
        }
    }

    private void onGetAllValuteSuccess(List<Valute> valutes) {
        valuteList.clear();
        valuteList.addAll(valutes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
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
    public void displayValutes(List<Valute> valutes) {
        if(valutes!=null) {
            onGetAllValuteSuccess(valutes);
        }else{
            Log.d(TAG,"valutes response null");
        }
    }

    @Override
    public void displayError(String e) {
        showToast(e);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }else if (id == R.id.update){
            setupViews();
            getValuteList();

        }
//        else if (id == R.id.about){
//            Intent intent = new Intent(this, AboutActivity.class);
//            startActivity(intent);
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClicked(Valute item, int position) {
        if(item!=null) {
            Intent intent = new Intent(getApplicationContext(), ValuteActivity.class);
            intent.putExtra("id", item.getId());
            intent.putExtra("click",22);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}