package com.developer.valyutaapp.ui.sort;

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
import com.developer.valyutaapp.adapter.SortAdapter;
import com.developer.valyutaapp.data.local_data_source.database.ValuteDataSource;
import com.developer.valyutaapp.data.local_data_source.database.ValuteDatabase;
import com.developer.valyutaapp.data.local_data_source.repository.ValuteRespository;
import com.developer.valyutaapp.model.Valute;
import com.developer.valyutaapp.ui.setting.SettingActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortActivity extends AppCompatActivity implements SortViewInterface, SortAdapter.ClickListener {

    @Nullable
    @BindView(R.id.recyclerValCurs)
    RecyclerView recyclerValCurs;

    @Nullable
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String TAG = "SortActivity";

    SortAdapter adapter;
    SortPresenter sortPresenter;
    private ValuteRespository valuteRespository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        ButterKnife.bind(this);

        ValuteDatabase valuteDatabase = ValuteDatabase.getInstance(this); //create db
        Log.d("Database ", valuteDatabase.toString());
        valuteRespository = ValuteRespository.getInstance(ValuteDataSource.getInstance(valuteDatabase.valuteDAO()));

        setupMVP();
        setupViews();
        getValuteList();
    }

    private void setupMVP() {
        sortPresenter = new SortPresenter(this.getApplicationContext(), this);
    }

    private void setupViews(){
        recyclerValCurs.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getValuteList() {
        sortPresenter.getValutes();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(SortActivity.this,str,Toast.LENGTH_LONG).show();
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
            adapter = new SortAdapter(valutes, SortActivity.this);
            adapter.setClickListener(this);
            recyclerValCurs.setAdapter(adapter);
        }else{
            Log.d(TAG,"valutes response null");
        }
    }

    @Override
    public void displayError(String e) {
        showToast(e);
    }

    @Override
    public void itemClicked(Valute item, int position, int sortValute) {
        if(item!=null) {
            item.setSortValute(sortValute);
           // sortPresenter.updateValute(item);
        }
    }
}