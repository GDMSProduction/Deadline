package com.example.leon.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Projects extends AppCompatActivity {

    public FloatingActionButton Create_Project;
    private Button Butt_Home;
    private Button Butt_Edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CProject tempProject = new CProject();
        tempProject.setName("My Project");

        Create_Project = (FloatingActionButton) findViewById(R.id.CreateProject);
        Create_Project.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Projects.this, ProjectCreationScreen.class);
                startActivity(intent);
            }
        });

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Projects.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        Butt_Edit = (Button) findViewById(R.id.btnEditProject);
        Butt_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Projects.this, EditProject.class);
                startActivity(intent);
            }
        });

        Button tempButton = (Button) findViewById(R.id.btnProject);
        tempButton.setText(tempProject.getName());

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Projects.this, Tasks.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Projects.this, HomeScreen.class);
        startActivity(intent);
    }

}
