package com.example.leon.deadline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

public class Jobs extends AppCompatActivity {

    private Button Butt_Home;
    private FloatingActionButton Fab_CreateJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CJob tempJob = new CJob();
        tempJob.setName("My Job");

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Jobs.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        Fab_CreateJob = (FloatingActionButton) findViewById(R.id.createJob);
        Fab_CreateJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Jobs.this, CreateJob.class);
                startActivity(intent);
            }
        });

        Button tempButton = (Button) findViewById(R.id.btnJobs);
        tempButton.setText(tempJob.getName());

        /*tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Jobs.this, Jobs.class);
                startActivity(intent);
            }
        });*/

        tempButton = (Button) findViewById(R.id.btnEditJob);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Jobs.this, EditJob.class);
                startActivity(intent);
            }
        });
    }

}
