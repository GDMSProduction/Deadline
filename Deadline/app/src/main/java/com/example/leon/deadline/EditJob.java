package com.example.leon.deadline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class EditJob extends AppCompatActivity {

    private Button Butt_Home;
    private Button Butt_Save;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private Calendar currentDay;
    private Calendar validDate;

    private EditText jName;
    private DatePicker jDate;
    private EditText jSummary;
    private CheckBox jComplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditJob.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("projects");
        ref = ref.child(((CStoreIDs)getApplication()).getProjectID()).child(((CStoreIDs)getApplication()).getTaskID()).child(((CStoreIDs)getApplication()).getJobID());
        jName = (EditText) findViewById(R.id.jobName);
        jName.setText(ref.child("name").toString());
        jDate = (DatePicker) findViewById(R.id.datePicker);
        jSummary = (EditText) findViewById(R.id.jobDescription);
        jSummary.setText(ref.child("summary").toString());
        jComplete = (CheckBox) findViewById(R.id.jobComplete);
        jComplete.setChecked(false);

        Butt_Save = (Button) findViewById(R.id.jobEdit);
        Butt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentDay = Calendar.getInstance();
                validDate =  Calendar.getInstance();
                validDate.set(jDate.getYear(),jDate.getMonth(),jDate.getDayOfMonth());

                if(!jName.equals("") &&
                        !jDate.equals(""))
                {
                    if(!currentDay.after(validDate))
                    {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("projects");
                        ref = ref.child(((CStoreIDs)getApplication()).getProjectID());
                        ref.child("name").setValue(jName.getText().toString());
                        ref.child("deadline").setValue(((DatePicker) findViewById(R.id.datePicker)).getMonth() + 1 + "/" + ((DatePicker) findViewById(R.id.datePicker)).getDayOfMonth() + "/" + ((DatePicker) findViewById(R.id.datePicker)).getYear());
                        ref.child("summary").setValue(jSummary.getText().toString());
                        ref.child("complete").setValue(jComplete.isChecked());
                    }
                    else
                    {
                        Toast.makeText(EditJob.this, "Please enter a valid date", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(EditJob.this, "Please fill out the form completely", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(EditJob.this, Projects.class);
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
                    Intent intent = new Intent(EditJob.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(EditJob.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(EditJob.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(EditJob.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(EditJob.this, Login.class);
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
}
