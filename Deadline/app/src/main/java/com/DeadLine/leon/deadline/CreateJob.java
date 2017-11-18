package com.DeadLine.leon.deadline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class CreateJob extends AppCompatActivity {

    //Buttons
    private Button jobCreateButton;
    private Button Butt_Home;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    //Spinner
    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    //Task
    private String jName, jDate, jSummary;
    private Boolean jComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                /*FirebaseUser*/ user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Log.d(CreateAccount.class.getSimpleName(), "onAuthStateChanged:signed_in" + user.getUid());
                }
                else
                {
                    Log.d(CreateAccount.class.getSimpleName(), "onAuthStateChanged:signed_out");
                }
            }

        };


        user = mAuth.getCurrentUser();

        jobCreateButton = (Button) findViewById(R.id.jobCreate);
        jobCreateButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                jName = ((EditText) findViewById(R.id.jobName)).getText().toString();
                jDate = ((DatePicker) findViewById(R.id.datePicker)).getMonth() + 1 + "/" + ((DatePicker) findViewById(R.id.datePicker)).getDayOfMonth() + "/" + ((DatePicker) findViewById(R.id.datePicker)).getYear();
                jSummary = ((EditText) findViewById(R.id.jobDescription)).getText().toString();
                DatePicker tCalendar = (DatePicker) findViewById(R.id.datePicker);
                Calendar validDate = Calendar.getInstance();
                validDate.set(tCalendar.getYear(),tCalendar.getMonth(),tCalendar.getDayOfMonth());
                //TODO: Create a toggle for this in the edit screen or from the view screen under the 3 dots options menu
                jComplete = false;

                if(!jName.equals("") &&
                        !jDate.equals(""))
                {
                    if(!Calendar.getInstance().after(validDate))
                    {
                        //TODO: Add a create task method in this java file
                        CreateJob(jName,jDate,jSummary,jComplete);
                    }
                    else
                    {
                        Toast.makeText(CreateJob.this, "Please enter a valid date", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CreateJob.this, "Please fill out the form completely", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(CreateJob.this, Tasks.class);
                startActivity(intent);
            }
        });


        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateJob.this, HomeScreen.class);
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
                    Intent intent = new Intent(CreateJob.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(CreateJob.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(CreateJob.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(CreateJob.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(CreateJob.this, Login.class);
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

    public void CreateJob(String _name, String _date, String _summary, Boolean _complete)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("projects").child("-Kz2t03YZZjEUWnGQhJr").child("tasks");

        String newKey = ref.child(user.getUid()).child("jobs").push().getKey();

        //TODO: Need to replace hardcoded key with active task key as determined by CStoreIDs
        ref = ref.child("-Kz4xDLxMl_ugG8YUaAa").child("jobs");

        ref.child(newKey).child("name").setValue(_name);
        ref.child(newKey).child("deadline").setValue(_date);
        ref.child(newKey).child("summary").setValue(_summary);
        ref.child(newKey).child("complete").setValue(_complete);

        Toast.makeText(CreateJob.this,"Job creation successful",Toast.LENGTH_SHORT).show();

    }
}
