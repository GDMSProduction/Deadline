package com.example.leon.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CreateAccount extends AppCompatActivity {

    private Button create1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        create1 = (Button) findViewById(R.id.finalizeCreate);
        create1.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v)
                                      {
                                          /*eEmail = (EditText) findViewById(R.id.email);
                                          ePass = (EditText) findViewById(R.id.password);

                                          email = eEmail.getText().toString();
                                          pass = ePass.getText().toString();

                                          createAccount(email,pass);*/
                                          Intent intent = new Intent(CreateAccount.this, HomeScreen.class);
                                          startActivity(intent);
                                      }
                                  }
        );
    }

}
