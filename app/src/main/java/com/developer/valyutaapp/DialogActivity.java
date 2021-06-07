package com.developer.valyutaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by User on 15.08.2018.
 */

public class DialogActivity extends Activity implements WidgetAdapter.ClickListener {

    DatabaseAdapter databaseAdapter;
    long userId = 0;
    String charc, nomi, val, dat;
    ListView listView;
    WidgetAdapter myAdapter;
    String text = "one";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_item);
        listView = (ListView) findViewById(R.id.list_dialog);
        if (text == "one"){
            databaseAdapter = new DatabaseAdapter(this);
            databaseAdapter.open();
            Log.d("getUser"," List");
            List<Model> users = databaseAdapter.getUsers();
            myAdapter = new WidgetAdapter(this, (ArrayList<Model>) users);
            myAdapter.setClickListener(this);
            listView.setAdapter(myAdapter);
            databaseAdapter.close();
        }else if (text == "two"){
            onUserLeaveHint();
            this.finish();
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    @Override
    public void onBackPressed() {
        DialogActivity.this.finish();
        super.onBackPressed();
        onUserLeaveHint();
    }
//    public void Method() {
//        if (userId > 0) {
//            // получаем элемент по id из бд
//            databaseAdapter.open();
//            final Model user = databaseAdapter.getUser(userId);
//            Paper.init(this);
//            Paper.book().write("charcode", charc = user.getCharcode());
//            Paper.book().write("nominal", nomi = String.valueOf(user.getNominal()));
//            Paper.book().write("value", val = user.getValue());
//            Paper.book().write("dat", dat = user.getDate());
//            Log.d("widget", " = " + user.getCharcode());
//        }
//    }
    @Override
    public void itemClicked(Model item, int position) {
        userId = item.get_id();
       // Method();
        text = "two";
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("_id", "2");
        startActivity(i);
        this.finish();
    }
    @Override
    public void itemLongClicked(Model item, int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
