package com.example.leon.deadline;

import android.accounts.Account;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountInfo extends AppCompatActivity {

    private Button Butt_Home;
    private Button Butt_Op;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private FirebaseDatabase fBase;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d(CreateAccount.class.getSimpleName(), "onAuthStateChanged:signed_in" + user.getUid());
                } else {
                    Log.d(CreateAccount.class.getSimpleName(), "onAuthStateChanged:signed_out");
                }
            }
        };

        user = mAuth.getCurrentUser();

        //Possible check. See if there is a different way to go about making this work
        if(user.getDisplayName() == null)
        {
            mAuth.signInWithEmailAndPassword(getIntent().getStringExtra("email"),getIntent().getStringExtra("pass"));
            user = mAuth.getCurrentUser();
        }

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountInfo.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        Butt_Op = (Button) findViewById(R.id.Options_Button);
        Butt_Op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountInfo.this, Settings.class);
                startActivity(intent);
            }
        });

        mDataBase = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        mDataBase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //CUser temp = dataSnapshot.getValue(CUser.class);
                TextView txtText;

                switch(dataSnapshot.getKey())
                {
                    case "bio" :
                        txtText = (TextView) findViewById(R.id.txtBio);
                        txtText.setText(dataSnapshot.getValue().toString());
                        break;
                    case "name" :
                        txtText = (TextView) findViewById(R.id.txtName);
                        txtText.setText(dataSnapshot.getValue().toString());
                        break;
                    case "username" :
                        txtText = (TextView) findViewById(R.id.txtUserName);
                        txtText.setText(dataSnapshot.getValue().toString());
                        break;
                    case "phone" :
                        txtText = (TextView) findViewById(R.id.txtPhone);
                        txtText.setText(dataSnapshot.getValue().toString());
                        break;
                    case "email" :
                        txtText = (TextView) findViewById(R.id.txtEmail);
                        txtText.setText(dataSnapshot.getValue().toString());
                        break;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*TextView txtText = (TextView) findViewById(R.id.txtName);
        txtText.setText("");

        txtText = (TextView) findViewById(R.id.txtUserName);
        txtText.setText("");

        txtText = (TextView) findViewById(R.id.txtPhone);
        txtText.setText("0987654321");

        txtText = (TextView) findViewById(R.id.txtBio);
        txtText.setText("Stuff about Sean goes hear.");
        */
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
        if(mAuthListener != null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
