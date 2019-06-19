package com.cossu.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.cossu.myapplication.activitys.ListeRecettesActivity;
import com.cossu.myapplication.activitys.LoginActivity;




public class SplashScreen extends Activity implements Runnable{
    private int SPLASH_TIME_OUT = 2000;

    String response;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        response = getSharedPreferences("APP", MODE_PRIVATE).getString("connected", null);

        new Handler().postDelayed(this, SPLASH_TIME_OUT);

    }


    @Override
    public void run() {

        if("oui".equals(response)) {
            Intent intent = new Intent(SplashScreen.this, ListeRecettesActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}
