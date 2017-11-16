package com.project.us.deadline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class Jobs extends AppCompatActivity {

    private Button Butt_Home;
    private FloatingActionButton Fab_CreateJob;
    private LinearLayout llJobOption;
    private Button Butt_JobOption;

    private FirebaseAuth mAuth;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    private CRole tempRole = new CRole();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CJob tempJob = new CJob();
        tempJob.setName("My Job");

        tempRole.setJobPermission(true);

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
        if(!tempRole.getJobPermission())
            Fab_CreateJob.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        nav_spin = (Spinner) findViewById(R.id.nav_Spinner);
        nav_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getSelectedItem().toString();
                if (selection.equals("Projects") && spin_Clicked){
                    Intent intent = new Intent(Jobs.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(Jobs.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(Jobs.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(Jobs.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(Jobs.this, Login.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                spin_Clicked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        if(!tempRole.getJobPermission())
            tempButton.setVisibility(View.GONE);


        // btnViewJobOptions OnCLickListener
        llJobOption = (LinearLayout) findViewById(R.id.llJobOptions);
        tempButton = (Button) findViewById(R.id.btnViewJobOptions);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llJobOption.getVisibility() == View.GONE)
                    llJobOption.setVisibility(View.VISIBLE);
                else
                    llJobOption.setVisibility(View.GONE);
            }
        });

        // btnDeleteJob OnCLickListener
        Butt_JobOption = (Button) findViewById(R.id.btnDeleteJob);
        Butt_JobOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llJobOption.removeAllViews();
                llJobOption = (LinearLayout)findViewById(R.id.llJobs);
                llJobOption.removeAllViews();
            }
        });
        if(!tempRole.getJobPermission())
            Butt_JobOption.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        nav_spin.setSelection(0);
    }
}
