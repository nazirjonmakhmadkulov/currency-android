package com.developer.valyutaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SortActivity extends AppCompatActivity implements SortAdapter.ClickListener{

    SortAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        ListView listView = (ListView) findViewById(R.id.list_sort);
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();
        Log.d("getUser"," List");
        List<Model> users = adapter.getUsers();
        myAdapter = new SortAdapter(this, (ArrayList<Model>) users);
        myAdapter.setClickListener(this);
        listView.setAdapter(myAdapter);
        adapter.close();
    }

    @Override
    public void itemClicked(Model item, int position) {

    }

    @Override
    public void itemLongClicked(Model item, int position) {

    }
}
