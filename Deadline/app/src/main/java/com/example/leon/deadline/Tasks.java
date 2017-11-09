package com.example.leon.deadline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.Task;

public class Tasks extends AppCompatActivity {

    private Button Butt_Home;
    private Button Butt_Op;
    private FloatingActionButton Create_Task;
    private LinearLayout llTaskOptionsLayout;
    private Button Butt_TaskOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CTask tempTask = new CTask();
        tempTask.setName("My Task");


        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        Butt_Op = (Button) findViewById(R.id.Options_Button);
        Butt_Op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks.this, Settings.class);
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


        Button tempButton = (Button) findViewById(R.id.btnTask);
        tempButton.setText(tempTask.getName());

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llTaskOptionsLayout.getVisibility() == View.VISIBLE)
                    llTaskOptionsLayout.setVisibility(View.GONE);
                Intent intent = new Intent(Tasks.this, Jobs.class);
                startActivity(intent);
            }
        });


        tempButton = (Button) findViewById(R.id.btnEditTask);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks.this, EditTask.class);
                startActivity(intent);
            }
        });



        // btnViewTaskOptions OnCLickListener
        llTaskOptionsLayout = (LinearLayout) findViewById(R.id.llTaskOptions);
        tempButton = (Button) findViewById(R.id.btnViewTaskOptions);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llTaskOptionsLayout.getVisibility() == View.GONE)
                    llTaskOptionsLayout.setVisibility(View.VISIBLE);
                else
                    llTaskOptionsLayout.setVisibility(View.GONE);
            }
        });

        // btnDeleteTask OnCLickListener
        Butt_TaskOption = (Button) findViewById(R.id.btnDeleteTask);
        Butt_TaskOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llTaskOptionsLayout.removeAllViews();
                llTaskOptionsLayout = (LinearLayout)findViewById(R.id.Tasks);
                llTaskOptionsLayout.removeAllViews();
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
