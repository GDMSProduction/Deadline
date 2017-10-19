package com.example.leon.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
<<<<<<< HEAD
import android.widget.Button;
import android.widget.TextView;
<<<<<<< HEAD
import android.widget.Toast;
=======
>>>>>>> parent of b8fa916... Organization and renaming
=======
>>>>>>> b8fa916634ac663204313142166276ba5faac0c8

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private DatabaseReference mDataBase;
    int projectSize;

    private CUser tempUser;
    private String tempHoldName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

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

        if(user.getDisplayName() == null)
        {
            mAuth.signInWithEmailAndPassword(getIntent().getStringExtra("email"),getIntent().getStringExtra("pass"));
            user = mAuth.getCurrentUser();
        }
        if(!user.isEmailVerified())
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>()
            {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        Log.d(CreateAccount.class.getSimpleName(),"Verification Email sent");
                    }
                }
            });
        }

/*
        try {
            tempUser = new CUser((CUser) getIntent().getSerializableExtra("TempUser"));
            tempHoldName = tempUser.getName();
        } catch (Exception e) {
            //e.printStackTrace();
        }
*/
        try{
            Thread.sleep(1000);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //TODO - fix displaying of name
<<<<<<< HEAD
<<<<<<< HEAD
        String test = "Welcome, " + user.getEmail();
        TextView text = (TextView) findViewById(R.id.TempUserInfo);
        text.setText(test);
        if (null == tempHoldName) {
            text.setText("Welcome, " + tempHoldEmail);
        }
=======
        //String test = "Welcome, " + user.getEmail();
        //TextView text = (TextView) findViewById(R.id.TempUserInfo);
        //text.setText(test);
        //if (null == tempHoldName) {
        //    text.setText("Welcome, " + tempHoldEmail);
        //}
>>>>>>> parent of b8fa916... Organization and renaming
=======
        String test = "Welcome, " + user.getDisplayName();
        TextView text = (TextView) findViewById(R.id.TempUserInfo);
        text.setText(test);
>>>>>>> 7acf32bc9716c996de80ea9c370b11a60dcccf7f

        mDataBase = FirebaseDatabase.getInstance().getReference("user").child(user.getDisplayName()).child("projectListSize");

        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null)
                 projectSize = dataSnapshot.getValue(int.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        if(projectSize == 0)
        {
            String blah = "You SUCK GET GOOD N00B";
            TextView emptyText = (TextView) findViewById(R.id.empty_Prompt);
            emptyText.setText(blah);
        }
=======
        //String test = "Welcome, " + user.getEmail();
        //TextView text = (TextView) findViewById(R.id.TempUserInfo);
        //text.setText(test);
        //if (null == tempHoldName) {
        //    text.setText("Welcome, " + tempHoldEmail);
        //}
>>>>>>> 4d0b31668fb6a10f1501babe54214725ab4f47a8

<<<<<<< HEAD
<<<<<<< HEAD
=======
        //TODO - fix displaying of name
        //String test = "Welcome, " + user.getEmail();
        //TextView text = (TextView) findViewById(R.id.TempUserInfo);
        //text.setText(test);
        //if (null == tempHoldName) {
        //    text.setText("Welcome, " + tempHoldEmail);
        //}

>>>>>>> parent of b8fa916... Organization and renaming
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


<<<<<<< HEAD


=======
>>>>>>> parent of b8fa916... Organization and renaming
=======
>>>>>>> b8fa916634ac663204313142166276ba5faac0c8
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
