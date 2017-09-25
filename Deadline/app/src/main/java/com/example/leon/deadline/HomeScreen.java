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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private CUser tempUser;
    private String tempHoldName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                /*FirebaseUser*/
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(CreateAccount.class.getSimpleName(), "onAuthStateChanged:signed_in" + user.getUid());
                } else {
                    Log.d(CreateAccount.class.getSimpleName(), "onAuthStateChanged:signed_out");
                }
            }
        };
        user = mAuth.getCurrentUser();
/*
        try {
            tempUser = new CUser((CUser) getIntent().getSerializableExtra("TempUser"));
            tempHoldName = tempUser.getName();
        } catch (Exception e) {
            //e.printStackTrace();
        }
*/

        //TODO - fix displaying of name
        String test = "Welcome, " + user.getEmail();
        TextView text = (TextView) findViewById(R.id.TempUserInfo);
        text.setText(test);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.newProject);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(HomeScreen.this, ProjectCreationScreen.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onBackPressed() {
        // Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

}
