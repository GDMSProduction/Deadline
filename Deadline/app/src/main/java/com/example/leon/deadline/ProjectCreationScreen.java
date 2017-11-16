package com.example.leon.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class ProjectCreationScreen extends AppCompatActivity {

    private Button Butt_Home;

    private Button projCreateButton;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private EditText eProjName;
    private DatePicker eDate;
    private Calendar currentDay;
    private Calendar validDate;

    private String _projName;
    private String _deadlineDate;
    private Boolean _privateProj;

    private Switch test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_creation_screen);
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

        projCreateButton = (Button) findViewById(R.id.projCreate);
        projCreateButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                eProjName = (EditText) findViewById(R.id.projName);
                eDate =  (DatePicker) findViewById(R.id.datePicker);

                currentDay = Calendar.getInstance();
                validDate =  Calendar.getInstance();
                validDate.set(eDate.getYear(),eDate.getMonth(),eDate.getDayOfMonth());

                _projName = eProjName.getText().toString();
                _deadlineDate = (eDate.getMonth() + 1) + "/" + eDate.getDayOfMonth() + "/"  + eDate.getYear() ;
                test = (Switch) findViewById(R.id.projPrivate);
                _privateProj = test.isChecked();

                if(!_projName.equals("") &&
                        !_deadlineDate.equals(""))
                {
                    if(!currentDay.after(validDate))
                    {
                        CreateProject(_projName,_deadlineDate, _privateProj);
                    }
                    else
                    {
                        Toast.makeText(ProjectCreationScreen.this, "Please enter a valid date", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ProjectCreationScreen.this, "Please fill out the form completely", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectCreationScreen.this, HomeScreen.class);
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
                    Intent intent = new Intent(ProjectCreationScreen.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(ProjectCreationScreen.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(ProjectCreationScreen.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(ProjectCreationScreen.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(ProjectCreationScreen.this, Login.class);
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

    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        nav_spin.setSelection(0);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if(mAuthListener != null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void CreateProject(String _name, String _date, Boolean _private)
    {
        /*
        CProject temp = new CProject(_name,_date, _private);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        String newKey = ref.child(user.getUid()).child("projectList").push().getKey();
        ref.child(user.getUid()).child("projectList").child(newKey).setValue(temp);

        //TODO: Make ProjectList Database
        ref = FirebaseDatabase.getInstance().getReference("projects");
        ref.child(newKey).setValue(temp);

        Toast.makeText(ProjectCreationScreen.this,"Project creation successful",Toast.LENGTH_SHORT).show();
        */

        CProject temp = new CProject(_name,_date, _private);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        String newKey = ref.child(user.getUid()).child("projectKeys").push().getKey();
        ref.child(user.getUid()).child("projectKeys").child(newKey).setValue(newKey);

        //PROJECT DATABASE
        ref = FirebaseDatabase.getInstance().getReference("projects");
        ref.child(newKey).setValue(temp);

        Toast.makeText(ProjectCreationScreen.this,"Project creation successful",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ProjectCreationScreen.this, Projects.class);
        //intent.putExtra("TempProj", tempProject);
        startActivity(intent);
    }
}
