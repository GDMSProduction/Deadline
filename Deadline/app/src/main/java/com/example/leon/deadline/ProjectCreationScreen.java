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

import java.util.Date;

public class ProjectCreationScreen extends AppCompatActivity {

    private Button projCreateButton;
    private Button Butt_Home;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private EditText eProjName;
    private DatePicker eDate;
    private Switch priv;

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

                _projName = eProjName.getText().toString();
                _deadlineDate = (eDate.getMonth() + 1) + "/" + eDate.getDayOfMonth() + "/"  + eDate.getYear() ;
                test = (Switch) findViewById(R.id.projPrivate);
                _privateProj = test.isChecked();

                if(!_projName.equals("") &&
                        !_deadlineDate.equals(""))
                {
                    CreateProject(_projName,_deadlineDate, _privateProj);
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
    }

    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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
        CProject temp = new CProject(_name,_date, _private);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(user.getDisplayName().toString()).child("projectList").child(_name).setValue(temp);

        Intent intent = new Intent(ProjectCreationScreen.this, Projects.class);
        //intent.putExtra("TempProj", tempProject);
        startActivity(intent);
    }
}
