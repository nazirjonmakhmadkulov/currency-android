package com.developer.valyutaapp.ui.setting

import android.preference.PreferenceActivity
import android.os.Bundle
import com.developer.valyutaapp.R
import android.content.Intent
import com.developer.valyutaapp.ui.widget.WidgetActivity
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.os.Build
import android.graphics.PorterDuff
import android.preference.Preference
import android.preference.SwitchPreference
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.developer.valyutaapp.core.database.SharedPreference

class SettingActivity : PreferenceActivity() {
    var autoUpdate: SwitchPreference? = null
    var sort: Preference? = null
    var widget: Preference? = null
    var sheredPreference: SharedPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
        sheredPreference = SharedPreference(this)
        sort = findPreference("sort") as Preference
        widget = findPreference("checked") as Preference
        autoUpdate = findPreference("auto") as SwitchPreference
//        sort!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
//            val intent = Intent(this@SettingActivity, SortActivity::class.java)
//            startActivity(intent)
//            false
//        }
        widget!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(this@SettingActivity, WidgetActivity::class.java)
            startActivity(intent)
            false
        }
//        if (sheredPreference!!.bool == "1") {
//            autoUpdate!!.isChecked = true
//        } else if (sheredPreference!!.bool == "0") {
//            autoUpdate!!.isChecked = false
//        }
//        autoUpdate!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
//            if (autoUpdate!!.isChecked) {
//                autoUpdate!!.isChecked = true
//                sheredPreference!!.saveBool("1")
//                startService(Intent(this@SettingActivity, AutoService::class.java))
//            } else {
//                autoUpdate!!.isChecked = false
//                sheredPreference!!.saveBool("0")
//                stopService(Intent(this@SettingActivity, AutoService::class.java))
//            }
//            false
//        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val root = findViewById<View>(android.R.id.list).parent.parent.parent as LinearLayout
        val bar = LayoutInflater.from(this).inflate(R.layout.toolbar, root, false) as Toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bar.navigationIcon!!
                .mutate().setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN)
        }
        root.addView(bar, 0) // insert at top
        bar.setNavigationOnClickListener { finish() }
    }
}