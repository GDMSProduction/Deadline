package com.DeadLine.leon.deadline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Jobs extends AppCompatActivity {

    private Button Butt_Home;
    private FloatingActionButton Fab_CreateJob;
    private LinearLayout llJobOption;
    private Button Butt_JobOption;


    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;

    final CDeadline[] aTest = new CDeadline[25];

    private FirebaseAuth mAuth;
    private int arrayInc = 0;
    private FirebaseDatabase fBase;
    private DatabaseReference mDataBase;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    private CRole tempRole = new CRole();

    ListView JobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CJob tempJob = new CJob();
        tempJob.setName("My Job");

        tempRole.setJobPermission(true);

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Jobs.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        Fab_CreateJob = (FloatingActionButton) findViewById(R.id.createJob);
        Fab_CreateJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Jobs.this, CreateJob.class);
                startActivity(intent);
            }
        });
        if(!tempRole.getJobPermission())
            Fab_CreateJob.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        nav_spin = (Spinner) findViewById(R.id.nav_Spinner);
        nav_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getSelectedItem().toString();
                if (selection.equals("Projects") && spin_Clicked){
                    Intent intent = new Intent(Jobs.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(Jobs.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(Jobs.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(Jobs.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(Jobs.this, Login.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                spin_Clicked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button tempButton = (Button) findViewById(R.id.btnJobs);
        tempButton.setText(tempJob.getName());

        /*tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Jobs.this, Jobs.class);
                startActivity(intent);
            }
        });*/

        tempButton = (Button) findViewById(R.id.btnEditJob);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Jobs.this, EditJob.class);
                startActivity(intent);
            }
        });
        if(!tempRole.getJobPermission())
            tempButton.setVisibility(View.GONE);


        // btnViewJobOptions OnCLickListener
        llJobOption = (LinearLayout) findViewById(R.id.llJobOptions);
        tempButton = (Button) findViewById(R.id.btnViewJobOptions);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llJobOption.getVisibility() == View.GONE)
                    llJobOption.setVisibility(View.VISIBLE);
                else
                    llJobOption.setVisibility(View.GONE);
            }
        });

        // btnDeleteJob OnCLickListener
        Butt_JobOption = (Button) findViewById(R.id.btnDeleteJob);
        Butt_JobOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llJobOption.removeAllViews();
                llJobOption = (LinearLayout)findViewById(R.id.llJobs);
                llJobOption.removeAllViews();
            }
        });
        if(!tempRole.getJobPermission())
            Butt_JobOption.setVisibility(View.GONE);

        fBase = FirebaseDatabase.getInstance();
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

        mDataBase = fBase.getReference("users").child(user.getUid());//.child("projectList");
        mDataBase.addChildEventListener(new ChildEventListener() {
            //IT GETS IN HERE
            //int i = 0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //int i = 0;
                for(DataSnapshot mtest :dataSnapshot.getChildren())
                {
                    if(mtest != null)
                    {
                        String projectKey = mtest.getValue().toString();
                        sDatabase(projectKey, aTest, arrayInc);
                        //i++;
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
    protected void onStart() {
        super.onStart();
        nav_spin.setSelection(0);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    public void sDatabase(final String _key, final CDeadline[] _array,final int _j)
    {
        DatabaseReference pDataBase = fBase.getReference().child("projects");
        pDataBase.addChildEventListener(new ChildEventListener() {
            int i = 0;
            int k = 0;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int j = _j;
                String name = "";
                String deadline = "";
                Boolean complete = false;
                String summary = "";
                Boolean addProj = false;
                for(DataSnapshot mtest : dataSnapshot.getChildren())
                {
                    if(mtest != null && dataSnapshot.getKey().toString().equals(_key))
                    {
                        switch(mtest.getKey().toString())
                        {
                            case "name":
                                name = mtest.getValue().toString();
                                //i++;
                                j++;
                                break;
                            case "deadline":
                                deadline = mtest.getValue().toString();
                                //i++;
                                j++;
                                break;
                            case "summary":
                                summary = mtest.getValue().toString();
                                //i++;
                                if(!dataSnapshot.child("tasks").exists())
                                {
                                    /*
                                    CProject temp = new CProject(name, deadline, summary, complete);
                                    temp.setUniqueID(_key);
                                    _array[arrayInc] = temp;
                                    arrayInc++;
                                    */
                                }
                                j++;
                                break;
                            case "complete":
                                complete = (boolean) mtest.getValue();
                                //i++;
                                j++;
                                break;
                            case "roles":
                                break;
                            case "tasks":
                                for(DataSnapshot qTest : mtest.getChildren())
                                {
                                    String tName = qTest.child("name").getValue().toString();
                                    String tDeadline = qTest.child("deadline").getValue().toString();
                                    String tSumm = qTest.child("summary").getValue().toString();
                                    Boolean tComplete = (Boolean) qTest.child("complete").getValue();
                                    if(qTest.child("jobs").exists())
                                    {
                                        for(DataSnapshot zTest : qTest.getChildren())
                                        {
                                            if(zTest.getKey().equals("jobs"))
                                            {
                                                for(DataSnapshot fTest : zTest.getChildren())
                                                {
                                                    //k = i;
                                                    Boolean zComplete = (Boolean) fTest.child("complete").getValue();
                                                    String zDeadline = fTest.child("deadline").getValue().toString();
                                                    String zName = fTest.child("name").getValue().toString();
                                                    String zSumm = fTest.child("summary").getValue().toString();

                                                    CJob tempJob = new CJob(zName,zDeadline,zSumm,zComplete);
                                                    _array[arrayInc] = tempJob;
                                                    arrayInc++;
                                                    //*/
                                                    //i = k;
                                                }
                                            }
                                        }
                                    }
/*
                                    CTask tempTask = new CTask(tName,tDeadline,tSumm,tComplete);
                                    _array[arrayInc] = tempTask;
                                    arrayInc++;
                                    //*/
                                }
                                //TESTING
                                /*
                                CProject temp = new CProject(name, deadline, summary, complete);
                                temp.setUniqueID(_key);
                                _array[arrayInc] = temp;
                                arrayInc++;
                                //*/
                                j++;
                                break;
                            default:
                                break;
                        }
                        /*
                        if(name != "" && deadline != "" && addProj)
                        {

                            CProject temp = new CProject(name, deadline, summary, complete);
                            temp.setUniqueID(_key);
                            _array[arrayInc] = temp;
                            arrayInc++;
                            //i++;
                        }
                        //*/
                    }

                    if(dataSnapshot.getChildrenCount() < j+1 && dataSnapshot.getChildrenCount() != 0)
                    {
                        populateScreen(_array);
                    }
                    //*/
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

    public void populateScreen(final CDeadline[] _proj)
    {
        int j = 0;
        //TODO LW10 - (FIXED) Get List from database onto Home Screen
        JobList = (ListView) findViewById(R.id.JobListView);
        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };
                */
        /*
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
        HomeScreen.global.setDeadlines(_proj);
        for(int i = 0; i < arrayInc; ++i)
        {
            if(_proj[i] != null )
            {
                deadlines.add(_proj[i]);
                HomeScreen.global.setColorID(_proj[i].getTypeID());
                //j = i + 1;
            }
        }

      /*TODO LW11 - FIGURE OUT HOW TO GET MULTIPLE COLORS WORKING FOR SPECIFIC TYPES
//*/
        //_proj[j] =  new CTask("test","test","test",true);

        //deadlines.add(_proj[j]);
        //global.setColorID(2);
        //*/
        final ListAdapter testAdapt = new CustomAdapter(this,
                deadlines,
                R.layout.row_layout,
                new String[] {CDeadline.ITEM_NAME, CDeadline.DEADLINE_DATE},
                new int[] {R.id.item_name,R.id.deadline_date});

        JobList.setAdapter(testAdapt);
        //THIS IS WHAT NEEDS TO BE ADDED TO MAKE IT DO STUFF ON CLICK
        JobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int typeID = HomeScreen.global.deadlines[position].getTypeID();
                switch (typeID){
                    // I am not using breaks in between cases so it sets all the IDS it can starting from lowest Jobs level, up to the Project Level

                    // CProject
                    case 0: {
                        // ToDo: Change this from Hard Coded "Another One" Project ID to the actual Project ID
                        ((CStoreIDs)getApplication()).setProjectID(HomeScreen.global.deadlines[position].getUniqueID());

                      /*Intent intent = new Intent(HomeScreen.this, Projects.class);
                      startActivity(intent);*/
                        break;
                    }
                    // CJob
                    case 1:{
                        // ToDo: For now, hard code the Unique ID of a prexisting Job to test
                        // ToDO: When possible replace with actual Job ID
                        // ToDo: Set the Task and Project ID as well
                        //((CStoreIDs)getApplication()).setJobID("HardCodedIDHere");
                        //((CStoreIDs)getApplication()).setTaskID("ActualTaskID");
                        //((CStoreIDs)getApplication()).setProjectID("ActualProjectID");
                        //getJobsParentsIDs(global.deadlines[position].getUniqueID());
                        //((CStoreIDs)getApplication()).setJobID(global.deadlines[position].getUniqueID());

                      /*Intent intent = new Intent(HomeScreen.this, Jobs.class);
                      startActivity(intent);*/
                        break;
                    }
                    // CTask
                    case 2: {
                        // ToDo: For now, hard code the Unique ID of a prexisting Task to test
                        //((CStoreIDs)getApplication()).setTaskID("HardCodedIDHere");
                        //((CStoreIDs)getApplication()).setProjectID("ActualProjectID");

                        //getTasksParentProjectID(global.deadlines[position].getUniqueID());
                        //((CStoreIDs)getApplication()).setTaskID(global.deadlines[position].getUniqueID());

                      /*Intent intent = new Intent(HomeScreen.this, Tasks.class);
                      startActivity(intent);*/
                        break;
                    }
                    default:{break;}
                }
                //Toast.makeText(HomeScreen.this,global.deadlines[position].getUniqueID().toString(),Toast.LENGTH_SHORT).show();
                //TODO: Somehow retrieve the project ID from the list object that was clicked and store that ID with CStoreIDs

            }
        });

        JobList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int typeID = HomeScreen.global.deadlines[position].getTypeID();
                switch (typeID){
                    // I am not using breaks in between cases so it sets all the IDS it can starting from lowest Jobs level, up to the Project Level

                    // CProject
                    case 0: {
                        // ToDo: Change this from Hard Coded "Another One" Project ID to the actual Project ID

                        //((CStoreIDs)getApplication()).setProjectID(global.deadlines[position].getUniqueID());

                        //Intent intent = new Intent(HomeScreen.this, Tasks.class);
                        //startActivity(intent);
                        break;
                    }
                    // CJob


                    case 1:{
                        // ToDo: For now, hard code the Unique ID of a prexisting Job to test
                        // ToDO: When possible replace with actual Job ID
                        // ToDo: Set the Task and Project ID as well
                        //((CStoreIDs)getApplication()).setJobID("HardCodedIDHere");
                        //((CStoreIDs)getApplication()).setTaskID("ActualTaskID");
                        //((CStoreIDs)getApplication()).setProjectID("ActualProjectID");


                    /*getJobsParentsIDs(global.deadlines[position].getUniqueID());
                    ((CStoreIDs)getApplication()).setJobID(global.deadlines[position].getUniqueID());*/

                        break;
                    }
                    // CTask
                    case 2: {
                        // ToDo: For now, hard code the Unique ID of a prexisting Task to test
                        //((CStoreIDs)getApplication()).setTaskID("HardCodedIDHere");
                        //((CStoreIDs)getApplication()).setProjectID("ActualProjectID");

                    /*getTasksParentProjectID(global.deadlines[position].getUniqueID());
                    ((CStoreIDs)getApplication()).setTaskID(global.deadlines[position].getUniqueID());*/

                        //Intent intent = new Intent(HomeScreen.this, Tasks.class);
                        //startActivity(intent);
                        break;
                    }
                    default:{break;}
                }
                Toast.makeText(Jobs.this,HomeScreen.global.deadlines[position].getUniqueID().toString(),Toast.LENGTH_SHORT).show();
                //TODO: Somehow retrieve the project ID from the list object that was clicked and store that ID with CStoreIDs

                return false;
            }
        });
        //*/

        //*/
    }
}
