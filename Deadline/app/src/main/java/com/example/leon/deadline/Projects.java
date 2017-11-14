package com.example.leon.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class Projects extends AppCompatActivity {

    public FloatingActionButton Create_Project;
    private Button Butt_Home;
    private Button Butt_Op;
    private Button Butt_ViewProjectOptions;
    private Button Butt_ProjOptions;
    private LinearLayout llProjOptions;

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

        Butt_Op = (Button) findViewById(R.id.Options_Button);
        Butt_Op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Projects.this, Settings.class);
                startActivity(intent);
            }
        });

        llProjOptions = (LinearLayout) findViewById(R.id.llProjectOptions);

        Butt_ViewProjectOptions = (Button) findViewById(R.id.btnViewProjectOptions);
        Butt_ViewProjectOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llProjOptions.getVisibility() == View.GONE)
                    llProjOptions.setVisibility(View.VISIBLE);
                else
                    llProjOptions.setVisibility(View.GONE);
            }
        });

        Button tempButton = (Button) findViewById(R.id.btnProject);
        tempButton.setText(tempProject.getName());

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llProjOptions.getVisibility() == View.VISIBLE)
                    llProjOptions.setVisibility(View.GONE);
                Intent intent = new Intent(Projects.this, Tasks.class);
                startActivity(intent);
            }
        });

        // btnEditProject OnCLickListener
        Butt_ProjOptions = (Button) findViewById(R.id.btnEditProject);
        Butt_ProjOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Projects.this, EditProject.class);
                startActivity(intent);
            }
        });


        // btnViewEditRoles OnCLickListener
        Butt_ProjOptions = (Button) findViewById(R.id.btnViewRoles);
        Butt_ProjOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Projects.this, Roles.class);
                startActivity(intent);
            }
        });

        // btnDeleteProject OnCLickListener
        Butt_ProjOptions = (Button) findViewById(R.id.btnDeleteProject);
        Butt_ProjOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llProjOptions.removeAllViews();
                llProjOptions = (LinearLayout) findViewById(R.id.llProjects);
                llProjOptions.removeAllViews();
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
