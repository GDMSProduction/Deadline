package com.Deadline.leon.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class SplashScreen extends AppCompatActivity {

    public final int waitTime = 3000;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this,Login.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
        }, waitTime);
    }

}
