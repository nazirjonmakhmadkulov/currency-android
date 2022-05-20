package com.developer.valyutaapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import com.developer.valyutaapp.R
import android.os.Bundle
import android.content.Intent
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.databinding.ActivityMainBinding
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.service.auto.AutoService
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewBinding by viewBinding(ActivityMainBinding::bind, R.id.container)
    private val prefs: SharedPreference by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (prefs.getBool() == "1") {
            startService(Intent(this, AutoService::class.java))
        } else if (prefs.getBool() == "0") {
            stopService(Intent(this, AutoService::class.java))
        }
    }
}