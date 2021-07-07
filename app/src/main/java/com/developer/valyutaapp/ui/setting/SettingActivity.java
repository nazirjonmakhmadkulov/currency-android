package com.developer.valyutaapp.ui.setting;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;

import com.developer.valyutaapp.R;
import com.developer.valyutaapp.service.auto.AutoService;
import com.developer.valyutaapp.ui.sort.SortActivity;
import com.developer.valyutaapp.ui.widget.WidgetActivity;
import com.developer.valyutaapp.utils.SheredPreference;


public class SettingActivity extends PreferenceActivity {

    SwitchPreference autoUpdate;
    Preference sort, widget;
    SheredPreference sheredPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        sheredPreference = new SheredPreference(this);

        sort = (Preference) findPreference("sort");
        widget = (Preference) findPreference("checked");
        autoUpdate = (SwitchPreference) findPreference("auto");

        sort.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(SettingActivity.this, SortActivity.class);
                startActivity(intent);
                return false;
            }
        });

        widget.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(SettingActivity.this, WidgetActivity.class);
                startActivity(intent);
                return false;
            }
        });
        if (sheredPreference.getBool().equals("1")){
            autoUpdate.setChecked(true);
        }else if (sheredPreference.getBool().equals("0")){
            autoUpdate.setChecked(false);
        }

        autoUpdate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (autoUpdate.isChecked()) {
                    autoUpdate.setChecked(true);
                    sheredPreference.saveBool("1");
                    startService(new Intent(SettingActivity.this, AutoService.class));
                } else {
                    autoUpdate.setChecked(false);
                    sheredPreference.saveBool("0");
                    stopService(new Intent(SettingActivity.this, AutoService.class));
                }
                return false;
            }
        });

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar, root, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bar.getNavigationIcon().mutate().setColorFilter(SettingActivity.this.getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        }
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}