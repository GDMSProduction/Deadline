package com.example.leon.deadline;

import android.accounts.Account;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

public class AccountInfo extends AppCompatActivity {

    private Button Butt_Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountInfo.this, HomeScreen.class);
                startActivity(intent);
            }
        });


        TextView txtText = (TextView) findViewById(R.id.txtName);
        txtText.setText("Sean Lopez");

        txtText = (TextView) findViewById(R.id.txtUserName);
        txtText.setText("sml91490");

        txtText = (TextView) findViewById(R.id.txtPhone);
        txtText.setText("0987654321");

        txtText = (TextView) findViewById(R.id.txtBio);
        txtText.setText("Stuff about Sean goes hear.");
    }



}
