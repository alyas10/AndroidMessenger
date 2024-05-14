package com.example.androidmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    FirebaseUser firebaseUser;
    private static int SPLASH_TIME_OUT = 10500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Здесь можно добавить загрузку приложения
                int progress = 0;
                while (progress < 100) {
                    try {
                        Thread.sleep(1000);
                        progress += 10;
                        progressBar.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (firebaseUser == null) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
