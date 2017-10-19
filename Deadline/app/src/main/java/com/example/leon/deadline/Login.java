package com.example.leon.deadline;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Login extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private EditText ePass;
    private EditText eEmail;

    private String pass;
    private String email;

    private Button create;
    private Button signIn;

    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Log.d(Login.class.getSimpleName(), "onAuthStateChanged:signed_in" + user.getUid());
                }
                else
                {
                    Log.d(Login.class.getSimpleName(), "onAuthStateChanged:signed_out");
                }
            }


        };


        create = (Button) findViewById(R.id.create_acc);
        create.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v)
                                      {

                                          Intent intent = new Intent(Login.this, CreateAccount.class);
                                          startActivity(intent);
                                      }
                                  }
        );

        signIn = (Button) findViewById(R.id.sign_in);
        signIn.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
<<<<<<< HEAD
                                      public void onClick(View v)
                                      {
=======
                                      public void onClick(View v) {
>>>>>>> 4d0b31668fb6a10f1501babe54214725ab4f47a8
                                          eEmail = (EditText) findViewById(R.id.email);
                                          ePass = (EditText) findViewById(R.id.password);

                                          if(!eEmail.getText().toString().equals("")
                                                  && !ePass.getText().toString().equals(""))
                                          {
                                              email = eEmail.getText().toString();
                                              pass = ePass.getText().toString();

                                              signIn(email,pass);
                                          }
                                          else
                                          {
                                              Toast.makeText(Login.this, "Please fill out both fields", Toast.LENGTH_SHORT).show();
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

    public void signIn(String email, String pass)
    {

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                Log.d(Login.class.getSimpleName(), "signInWithEmail:onComplete:" + task.isSuccessful());

                if(!task.isSuccessful())
                {
                    Log.w(Login.class.getSimpleName(),"signInWithEmail:failed", task.getException());
                    Toast.makeText(Login.this, R.string.auth_failed,Toast.LENGTH_SHORT).show();
                }
<<<<<<< HEAD
                else
                {
                    //Toast.makeText(Login.this, R.string.auth_succ, Toast.LENGTH_SHORT).show();
                    user = mAuth.getCurrentUser();
                    if(!user.isEmailVerified())
                    {
                        mAuth.signOut();
                        Toast.makeText(Login.this,"Please Verify your email", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent intent = new Intent(Login.this, HomeScreen.class);
                        //intent.putExtra("TempUser", user.getDisplayName());
                        startActivity(intent);
                    }
                }
=======

                //user = FirebaseAuth.getInstance().getCurrentUser();
                //if(user != null)
                //{
                //    String name = user.getDisplayName();
                //    String email = user.getEmail();
                //    Uri photoUrl = user.getPhotoUrl();
//
                //    String uid = user.getUid();
                //}
>>>>>>> 4d0b31668fb6a10f1501babe54214725ab4f47a8
            }
        });
    }
}
