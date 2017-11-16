package com.example.leon.deadline;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private FirebaseDatabase fBase;
    private DatabaseReference mDataBase;
    final CDeadline[] aTest = new CDeadline[10];

    //private final ListView HomeList;
    //private ArrayList<String> projecterino = new ArrayList<String>();
    private ArrayAdapter<String> HomeAdapter;


    int projectSize;
    ListView HomeList;

    public static GLOBALS global = new GLOBALS();

    private CUser tempUser;
    private String tempHoldName;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    private ImageButton homeProjCreate;
    private ImageButton Butt_Sort;
    private boolean sort_Switch = false;

/*
    private Button projJump;
    private Button taskJump;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Butt_Sort = (ImageButton) findViewById(R.id.sort_button);
        Butt_Sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            sortDeadlinesDescending();
            populateScreen(aTest);
            }
        });

        homeProjCreate = (ImageButton) findViewById(R.id.homeCreateProj);
        homeProjCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, ProjectCreationScreen.class);
                startActivity(intent);
            }
        });



        nav_spin = (Spinner) findViewById(R.id.nav_Spinner);
        nav_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getSelectedItem().toString();
                if (selection.equals("Projects") && spin_Clicked){
                    Intent intent = new Intent(HomeScreen.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(HomeScreen.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(HomeScreen.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(HomeScreen.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(HomeScreen.this, Login.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                spin_Clicked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*Remove all of these and "ungone" the visibility of the actual display linear layout
        projJump = (Button) findViewById(R.id.btnProject);
        projJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Tasks.class);
                startActivity(intent);
            }
        });
        projJump = (Button) findViewById(R.id.projDate);
        projJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Tasks.class);
                startActivity(intent);
            }
        });

        taskJump = (Button) findViewById(R.id.btnTask);
        taskJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Jobs.class);
                startActivity(intent);
            }
        });
        taskJump = (Button) findViewById(R.id.taskDate);
        taskJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Jobs.class);
                startActivity(intent);
            }
        });
        //*/

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
        //TODO LW5 -(FIXED) FIX DISPLAYING OF NAME
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

        //Do we even need this?
        if(projectSize == 0)
        {
            String empty = "You do not have any open projects! Please click the button to create a new project";
            TextView emptyText = (TextView) findViewById(R.id.empty_Prompt);
            emptyText.setText(empty);
        }

        mDataBase = FirebaseDatabase.getInstance().getReference("users");
        fBase = FirebaseDatabase.getInstance();
       // final CDeadline[] aTest = new CDeadline[10];
        //aTest = new CDeadline[10];
        mDataBase = fBase.getReference("users").child(user.getUid());//.child("projectList");
        mDataBase.addChildEventListener(new ChildEventListener() {
            //IT GETS IN HERE
            //int i = 0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int i = 0;
                for(DataSnapshot mtest :dataSnapshot.getChildren())
                {
                    if(mtest != null)
                    {
                        String projectKey = mtest.getValue().toString();
                        sDatabase(projectKey, aTest, i);
                        i++;
                        /*
                        CProject temp = new CProject(mtest.child("name").getValue().toString(), mtest.child("deadline").getValue().toString(), (Boolean) mtest.child("bPrivate").getValue());
                        aTest[i] = temp;
                        i++;
                        */
                    }
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
                Log.d(HomeScreen.class.getSimpleName(), "Did not completo");
            }
        });

    }

    @Override
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
        int j = 0;
        //TODO LW10 - (FIXED) Get List from database onto Home Screen
        HomeList = (ListView) findViewById(R.id.HomeListView);
        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };
                */

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < _proj.length; ++i)
        {
            if(_proj[i] != null)
            {
                list.add(_proj[i].getName());
            }
        }
        /*
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        HomeList.setAdapter(adapter);

        //*/
        //TODO LW12- (FIXED) CHANGE ARRAY TO CUSTOM
        List<Map<String, String>> deadlines = new ArrayList<Map<String, String>>();
        int colorID;
      /*
      TODO LW9 - (FIXED) Figure out why this isn't working
      *The list array created above isn't the same type needed in
      *the parameters of the function.
      *
      *Look at the tutorial again and see if there is anything needed.
      *Check and see if the
      *

      */
      global.setDeadlines(_proj);
      for(int i = 0; i < _proj.length; ++i)
      {
          if(_proj[i] != null )
          {
              deadlines.add(_proj[i]);
              global.setColorID(_proj[i].getTypeID());
          j = i + 1;
          }
      }

      /*TODO LW11 - FIGURE OUT HOW TO GET MULTIPLE COLORS WORKING FOR SPECIFIC TYPES
//*/
      _proj[j] =  new CTask("test","test","test",true);

      deadlines.add(_proj[j]);
      //global.setColorID(2);
      //*/
      final ListAdapter testAdapt = new CustomAdapter(this,
              deadlines,
              R.layout.row_layout,
              new String[] {CDeadline.ITEM_NAME, CDeadline.DEADLINE_DATE},
              new int[] {R.id.item_name,R.id.deadline_date});

      HomeList.setAdapter(testAdapt);
      //THIS IS WHAT NEEDS TO BE ADDED TO MAKE IT DO STUFF ON CLICK
      HomeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              int typeID = global.deadlines[position+1].getTypeID();
              switch (typeID){
                  // I am not using breaks in between cases so it sets all the IDS it can starting from lowest Jobs level, up to the Project Level

                  // CJob
                  case 1:{
                      // ToDo: For now, hard code the Unique ID of a prexisting Job to test
                      // ToDO: When possible replace with actual Job ID
                      // ToDo: Set the Task and Project ID as well
                      //((CStoreIDs)getApplication()).setJobID("HardCodedIDHere");
                      //((CStoreIDs)getApplication()).setTaskID("ActualTaskID");
                      //((CStoreIDs)getApplication()).setProjectID("ActualProjectID");
                      break;
                  }
                  // CTask
                  case 2: {
                      // ToDo: For now, hard code the Unique ID of a prexisting Task to test
                      //((CStoreIDs)getApplication()).setTaskID("HardCodedIDHere");
                      //((CStoreIDs)getApplication()).setProjectID("ActualProjectID");
                      break;
                  }
                  // CProject
                  case 0: {
                      // ToDo: Change this from Hard Coded "Another One" Project ID to the actual Project ID
                      ((CStoreIDs)getApplication()).setProjectID("-Kz2t03YZZjEUWnGQhJr");
                      break;
                  }
                  default:{break;}
              }
              Toast.makeText(HomeScreen.this,"-Kz2t03YZZjEUWnGQhJr",Toast.LENGTH_SHORT).show();
              //TODO: Somehow retrieve the project ID from the list object that was clicked and store that ID with CStoreIDs

          }
      });
       //*/
    }

    public void sDatabase(final String _key, final CDeadline[] _array,final int _j)
    {
        DatabaseReference pDataBase = fBase.getReference().child("projects");
        pDataBase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int i = 0;
                String name = "";
                String deadline = "";
                Boolean complete = false;
                String summary = "";
                for(DataSnapshot mtest :dataSnapshot.getChildren())
                {
                    if(mtest != null && dataSnapshot.getKey().toString().equals(_key))
                    {
                        switch(mtest.getKey().toString())
                        {
                            case "name":
                                name = mtest.getValue().toString();
                                i++;
                                break;
                            case "deadline":
                                deadline = mtest.getValue().toString();
                                i++;
                                break;
                            case "summary":
                                summary = mtest.getValue().toString();
                                i++;
                                break;
                            case "complete":
                                complete = (boolean) mtest.getValue();
                                i++;
                                break;
                            default:
                                break;
                        }
                        if(name != "" && deadline !="")
                        {
                            CProject temp = new CProject(name, deadline, summary, complete);
                            _array[_j] = temp;
                            //i++;
                        }
                    }
                    if(dataSnapshot.getChildrenCount() < i+1 && dataSnapshot.getChildrenCount() != 0)
                    {
                        populateScreen(_array);
                    }
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
    }

    public void sortDeadlinesDescending() {
        List<CDeadline> deadlines = new ArrayList<>();
        for (int i = 0; i < aTest.length; i++) {
            deadlines.add(aTest[i]);
        }

        Collections.sort(deadlines, new CDeadlineComparator());

        for (int i = 0; i < aTest.length; i++) {
            aTest[i] = deadlines.get(i);
        }
    }


}
