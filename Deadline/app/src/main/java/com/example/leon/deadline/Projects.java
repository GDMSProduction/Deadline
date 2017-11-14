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
import com.google.firebase.auth.FirebaseAuth;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class Projects extends AppCompatActivity {

    public FloatingActionButton Create_Project;
    private Button Butt_Home;
    private Button Butt_ViewProjectOptions;
    private Button Butt_ProjOptions;
    private LinearLayout llProjOptions;

    private FirebaseAuth mAuth;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

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

        mAuth = FirebaseAuth.getInstance();

        nav_spin = (Spinner) findViewById(R.id.nav_Spinner);
        nav_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getSelectedItem().toString();
                if (selection.equals("Projects") && spin_Clicked){
                    Intent intent = new Intent(Projects.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(Projects.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(Projects.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(Projects.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(Projects.this, Login.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                spin_Clicked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    @Override
    protected void onStart() {
        super.onStart();
        nav_spin.setSelection(0);
    }
}
