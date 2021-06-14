package com.developer.valyutaapp;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by User on 08.07.2018.
 */

public class PrefActivity extends PreferenceActivity {

    CheckBoxPreference autoUpdate, polUpdate;
    Preference sort, widget;
    Setting setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
        setting = new Setting(this);

        sort = (Preference) findPreference("sort");
        widget = (Preference) findPreference("checked");
        autoUpdate = (CheckBoxPreference) findPreference("auto");
        polUpdate = (CheckBoxPreference) findPreference("update");

        sort.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(PrefActivity.this, SortActivity.class);
                startActivity(intent);
                return false;
            }
        });

        widget.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(PrefActivity.this, SettingWidget.class);
                startActivity(intent);
                return false;
            }
        });
        if (setting.getBool().equals("1")){
            autoUpdate.setChecked(true);
        }else if (setting.getBool().equals("0")){
            autoUpdate.setChecked(false);
        }
        if (setting.getValue().equals("1")){
            polUpdate.setChecked(true);
        }else if (setting.getValue().equals("0")){
            polUpdate.setChecked(false);

        }


        autoUpdate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (autoUpdate.isChecked()) {
                    autoUpdate.setChecked(true);
                    setting.saveBool("1");
                }else {
                    autoUpdate.setChecked(false);
                    setting.saveBool("0");
                }
                return false;
            }
        });

        polUpdate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (polUpdate.isChecked()) {
                    polUpdate.setChecked(true);
                    setting.saveValue("1");
                }else {
                    polUpdate.setChecked(false);
                    setting.saveValue("0");
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
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
