package com.example.leon.deadline;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TestActivity extends AppCompatActivity{

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
        setContentView(R.layout.activity_test);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Log.d(TestActivity.class.getSimpleName(), "onAuthStateChanged:signed_in" + user.getUid());
                }
                else
                {
                    Log.d(TestActivity.class.getSimpleName(), "onAuthStateChanged:signed_out");
                }
            }


        };


        create = (Button) findViewById(R.id.create_acc);
        create.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v)
                                      {
                                          /*eEmail = (EditText) findViewById(R.id.email);
                                          ePass = (EditText) findViewById(R.id.password);

                                          email = eEmail.getText().toString();
                                          pass = ePass.getText().toString();

                                          createAccount(email,pass);*/
                                          Intent intent = new Intent(TestActivity.this, CreateAccount.class);
                                          startActivity(intent);
                                      }
                                  }
        );

        signIn = (Button) findViewById(R.id.sign_in);
        signIn.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v)
                                      {
                                          /*eEmail = (EditText) findViewById(R.id.email);
                                          ePass = (EditText) findViewById(R.id.password);

                                          email = eEmail.getText().toString();
                                          pass = ePass.getText().toString();

                                          signIn(email,pass);*/
                                          Intent intent = new Intent(TestActivity.this, HomeScreen.class);
                                          startActivity(intent);
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
                        Log.d(TestActivity.class.getSimpleName(), "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if(!task.isSuccessful())
                        {
                            Toast.makeText(TestActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(TestActivity.this, R.string.auth_succ, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    };

    public void signIn(String email, String pass)
    {
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                Log.d(TestActivity.class.getSimpleName(), "signInWithEmail:onComplete:" + task.isSuccessful());

                if(!task.isSuccessful())
                {
                    Log.w(TestActivity.class.getSimpleName(),"signInWithEmail:failed", task.getException());
                    Toast.makeText(TestActivity.this, R.string.auth_failed,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(TestActivity.this, R.string.auth_succ, Toast.LENGTH_SHORT).show();
                }

                user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null)
                {
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();

                    String uid = user.getUid();
                }
            }
        });
    }
}
