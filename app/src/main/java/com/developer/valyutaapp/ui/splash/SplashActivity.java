package com.developer.valyutaapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.valyutaapp.R;
import com.developer.valyutaapp.ui.main.MainActivity;

import butterknife.BindView;

public class SplashActivity extends AppCompatActivity {


    @Nullable
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        tvTitle.setText(R.string.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3 * 1000);
    }
}