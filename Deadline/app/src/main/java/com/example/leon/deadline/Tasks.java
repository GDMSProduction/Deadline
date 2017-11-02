package com.example.leon.deadline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import com.google.android.gms.tasks.Task;

public class Tasks extends AppCompatActivity {

    private Button Butt_Home;
    private FloatingActionButton Create_Task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        Create_Task = (FloatingActionButton) findViewById(R.id.CreateTask);
        Create_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks.this, CreateTask.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Tasks.this, Projects.class);
        startActivity(intent);
    }

}
