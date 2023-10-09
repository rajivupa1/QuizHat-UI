package com.winbee.quizhat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.winbee.quizhat.R;
import com.winbee.quizhat.Utils.PrefManager;
import com.winbee.quizhat.Utils.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    prefManager = new PrefManager(SplashActivity.this);
                    if (prefManager.isFirstTimeLaunch()) {

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    } else {
                        launchHomeScreen();
                        finish();

                    }


                }
            }
        };
        timer.start();
    }


    private void launchHomeScreen() {
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, OverViewActivity.class));
            return;
        }else {
            finish();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }
    }