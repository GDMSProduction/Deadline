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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity {

    private Button create1;
    private Button Butt_Home;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private EditText ePass;
    private EditText eEmail;
    private EditText eName;
    private EditText eConf;

    private String pass;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
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

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        create1 = (Button) findViewById(R.id.finalizeCreate);
        create1.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v)
                                      {
                                          eEmail = (EditText) findViewById(R.id.email1);
                                          ePass = (EditText) findViewById(R.id.password1);
                                          eName = (EditText) findViewById(R.id.accName);
                                          eConf = (EditText) findViewById(R.id.passConf);

                                          if(!eName.getText().toString().equals("")
                                                  &&!eEmail.getText().toString().equals("")
                                                  && !ePass.getText().toString().equals("")
                                                  && !eConf.getText().toString().equals(""))
                                          {
                                              if(ePass.getText().toString().equals(eConf.getText().toString()))
                                              {
                                                  email = eEmail.getText().toString();
                                                  pass = ePass.getText().toString();
                                                  /*TODO
                                                  * check if any fields are blank
                                                  * check if password and confirmation password are the same
                                                  * */
                                                  createAccount(email,pass);

                                                  Intent intent = new Intent(CreateAccount.this, HomeScreen.class);
                                                  startActivity(intent);
                                              }
                                              else
                                              {
                                                  Toast.makeText(CreateAccount.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                                              }

                                          }
                                          else
                                          {
                                              Toast.makeText(CreateAccount.this,"Please fill out the entire document",Toast.LENGTH_SHORT).show();
                                          }

                                      }
                                  }
        );
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

    public void createAccount(String email, String pass)
    {
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                Log.d(CreateAccount.class.getSimpleName(), "createUserWithEmail:onComplete:" + task.isSuccessful());

                if(!task.isSuccessful())
                {
                    Toast.makeText(CreateAccount.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(CreateAccount.this, R.string.auth_succ, Toast.LENGTH_SHORT).show();
                }
            }
        });

    };

}
