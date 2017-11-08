package com.example.leon.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CreateTask extends AppCompatActivity {

    private Button taskCreateButton;
    private Button Butt_Home;
    private Button Butt_Op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        taskCreateButton = (Button) findViewById(R.id.taskCreate);
        taskCreateButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTask.this, Tasks.class);
                startActivity(intent);
            }
        });

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTask.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        Butt_Op = (Button) findViewById(R.id.Options_Button);
        Butt_Op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTask.this, Settings.class);
                startActivity(intent);
            }
        });
    }

}
