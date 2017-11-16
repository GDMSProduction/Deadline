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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    private Button create1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private EditText ePass;
    private EditText eEmail;
    private EditText eName;
    private EditText eConf;
    private EditText ePhone;
    private EditText eUserName;
    private EditText eBio;

    private String name;
    private String pass;
    private String email;
    private String phone;
    private String username;
    private String bio;

    private CUser tempUser = new CUser("name", "email");

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

        create1 = (Button) findViewById(R.id.finalizeCreate);
        create1.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v)
                                      {
                                          eEmail = (EditText) findViewById(R.id.accEmail);
                                          ePass = (EditText) findViewById(R.id.accPassword);
                                          eName = (EditText) findViewById(R.id.accName);
                                          eConf = (EditText) findViewById(R.id.passConf);
                                          ePhone = (EditText) findViewById(R.id.phoneNum);
                                          eUserName = (EditText) findViewById(R.id.userName);
                                          eBio = (EditText) findViewById(R.id.userBio);

                                          if(!eName.getText().toString().equals("")
                                                  &&!eEmail.getText().toString().equals("")
                                                  && !ePass.getText().toString().equals("")
                                                  && !eConf.getText().toString().equals("")
                                                  && !ePhone.getText().toString().equals("")
                                                  && !eUserName.getText().toString().equals("")
                                                  && !eBio.getText().toString().equals(""))
                                          {
                                              if(ePass.getText().toString().equals(eConf.getText().toString()))
                                              {
                                                  email = eEmail.getText().toString();
                                                  pass = ePass.getText().toString();
                                                  name = eName.getText().toString();
                                                  phone = ePhone.getText().toString();
                                                  username = eUserName.getText().toString();
                                                  bio = eBio.getText().toString();

                                                  /*TODO LW1 - (FIXED) CHECK FIELDS
                                                  * check if any fields are blank
                                                  * check if password and confirmation password are the same
                                                  * */
                                                  createAccount(name, email, pass, phone, username, bio);

                                                  tempUser.setName(name);
                                                  tempUser.setEmail((email));

                                                  //Intent intent = new Intent(CreateAccount.this, HomeScreen.class);
                                                  //intent.putExtra("TempUser", tempUser);
                                                  //startActivity(intent);

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

    /*TODO LW2 - (FIXED) BUG 1:
    Button must be clicked a second time after a successful result. For some
    reason the onCompleteListener gets called after the if check instead of before.
    FIXED: moved the user creation code and the intent switching into the else statement.
    */
    public void createAccount(final String name, final String email,final String pass, final String phone, final String username, final String bio)
    {

         mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 Log.d(CreateAccount.class.getSimpleName(), "createUserWithEmail:onComplete:" + task.isSuccessful());

                 if (!task.isSuccessful()) {
                     Toast.makeText(CreateAccount.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(CreateAccount.this, R.string.auth_succ, Toast.LENGTH_SHORT).show();

                     CUser temp = new CUser(name, email, username, phone, bio);
                     DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                     String key = ref.push().getKey();
                     ref.child(user.getUid()).setValue(temp);
                     //ref.setValue(temp);

                     UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                     user.updateProfile(profileUpdates);

                     Intent intent = new Intent(CreateAccount.this, HomeScreen.class);
                     intent.putExtra("email",email);
                     intent.putExtra("pass",pass);
                     startActivity(intent);
                 }
             }
         });
    };
}
