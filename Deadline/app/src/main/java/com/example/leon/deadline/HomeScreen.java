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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    private Button Butt_Op;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private FirebaseDatabase fBase;
    private DatabaseReference mDataBase;

    //private final ListView HomeList;
    //private ArrayList<String> projecterino = new ArrayList<String>();
    private ArrayAdapter<String> HomeAdapter;


    int projectSize;

    private CUser tempUser;
    private String tempHoldName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Butt_Op = (Button) findViewById(R.id.Options_Button);
        Butt_Op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Settings.class);
                startActivity(intent);
            }
        });

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
        String test = "Welcome, " + user.getDisplayName();
        TextView text = (TextView) findViewById(R.id.TempUserInfo);
        text.setText(test);

        mDataBase = FirebaseDatabase.getInstance().getReference("users").child(user.getDisplayName()).child("projectListSize");

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
            String empty = "You do not have any open projects! Please click the button to create a new project";
            TextView emptyText = (TextView) findViewById(R.id.empty_Prompt);
            emptyText.setText(empty);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.gotoProjects);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(HomeScreen.this, Projects.class);
                startActivity(intent);
            }
        });

        Button AccBtn = (Button) findViewById(R.id.Account_Button);
        AccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(HomeScreen.this, AccountInfo.class);
                startActivity(intent);
            }
        });

        Button tempCreateRoleBtn = (Button) findViewById(R.id.btnTempCreateRole);
        tempCreateRoleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(HomeScreen.this, CreateEditRoles.class);
                startActivity(intent);
            }
        });

        //mDataBase = FirebaseDatabase.getInstance().getReference("users");
        fBase = FirebaseDatabase.getInstance();
        final CDeadline[] aTest = new CDeadline[10];
        mDataBase = fBase.getReference("users").child(user.getUid());//.child("projectList");
       /* mDataBase.addChildEventListener(new ChildEventListener() {
            //IT GETS IN HERE
            //int i = 0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int i = 0;
                for(DataSnapshot mtest :dataSnapshot.getChildren())
                {
                    if(mtest != null)
                    {
                        CProject temp = new CProject(mtest.child("name").getValue().toString(), mtest.child("deadline").getValue().toString(), (Boolean) mtest.child("bPrivate").getValue());
                        aTest[i] = temp;
                        i++;
                    }
                    if(dataSnapshot.getChildrenCount() < i+1 && dataSnapshot.getChildrenCount() != 0)
                    {
                        populateScreen(aTest);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int i = 0;
                for(DataSnapshot mtest :dataSnapshot.getChildren())
                {
                    CProject temp = new CProject(mtest.child("name").getValue().toString(), mtest.child("deadline").getValue().toString(), (Boolean) mtest.child("bPrivate").getValue());
                    aTest[i] = temp;
                    i++;
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int i = 0;
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                int i = 0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(HomeScreen.class.getSimpleName(), "Did not completo");
            }
        });*/




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

    public void populateScreen(final CDeadline[] _proj)
    {
        //TODO: Get List from database onto Home Screen
        final ListView HomeList = (ListView) findViewById(R.id.HomeListView);
        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };
                */

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < _proj.length; ++i) {
            if(_proj[i] != null)
            {
                list.add(_proj[i].getName());
            }
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        HomeList.setAdapter(adapter);
    }
}
