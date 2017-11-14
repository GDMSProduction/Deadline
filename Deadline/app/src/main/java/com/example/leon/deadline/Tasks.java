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
import com.google.firebase.auth.FirebaseAuth;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

public class Tasks extends AppCompatActivity {

    private Button Butt_Home;
    private FloatingActionButton Create_Task;
    private LinearLayout llTaskOptionsLayout;
    private Button Butt_TaskOption;

    private FirebaseAuth mAuth;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

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

        mAuth = FirebaseAuth.getInstance();

        nav_spin = (Spinner) findViewById(R.id.nav_Spinner);
        nav_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getSelectedItem().toString();
                if (selection.equals("Projects") && spin_Clicked){
                    Intent intent = new Intent(Tasks.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(Tasks.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(Tasks.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(Tasks.this, "Version: 171109_P3", Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(Tasks.this, Login.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                spin_Clicked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
    protected void onStart() {
        super.onStart();
        nav_spin.setSelection(0);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Tasks.this, Projects.class);
        startActivity(intent);
    }

}
