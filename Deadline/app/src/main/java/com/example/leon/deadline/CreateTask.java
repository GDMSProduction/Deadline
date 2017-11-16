package com.example.leon.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;

import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class CreateTask extends AppCompatActivity {

    private Button taskCreateButton;
    private Button Butt_Home;

    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    private String tName, tDate, tSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = mAuth.getCurrentUser();

        taskCreateButton = (Button) findViewById(R.id.taskCreate);
        taskCreateButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                tName = ((EditText) findViewById(R.id.taskName)).getText().toString();
                tDate = ((DatePicker) findViewById(R.id.datePicker)).getMonth() + 1 + "/" + ((DatePicker) findViewById(R.id.datePicker)).getDayOfMonth() + "/" + ((DatePicker) findViewById(R.id.datePicker)).getYear();
                tSummary = ((EditText) findViewById(R.id.taskDescription)).getText().toString();
                DatePicker tCalendar = (DatePicker) findViewById(R.id.datePicker);
                Calendar validDate = Calendar.getInstance();
                validDate.set(tCalendar.getYear(),tCalendar.getMonth(),tCalendar.getDayOfMonth());

                if(!tName.equals("") &&
                        !tDate.equals(""))
                {
                    if(!Calendar.getInstance().after(validDate))
                    {
                        //TODO: add a create task method in this java file
                        CreateTask(tName,tDate,tSummary);
                    }
                    else
                    {
                        Toast.makeText(CreateTask.this, "Please enter a valid date", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CreateTask.this, "Please fill out the form completely", Toast.LENGTH_SHORT).show();
                }

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

        mAuth = FirebaseAuth.getInstance();

        nav_spin = (Spinner) findViewById(R.id.nav_Spinner);
        nav_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getSelectedItem().toString();
                if (selection.equals("Projects") && spin_Clicked){
                    Intent intent = new Intent(CreateTask.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(CreateTask.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(CreateTask.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(CreateTask.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(CreateTask.this, Login.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                spin_Clicked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        nav_spin.setSelection(0);
    }

    public void CreateTask(String _name, String _date, String _summary)
    {
        CTask temp = new CTask(_name,_date,_summary);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tasks");
    }
}
