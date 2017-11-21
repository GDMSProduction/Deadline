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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tasks extends AppCompatActivity {

    private Button Butt_Home;
    private ImageButton Create_Task, Butt_Name_Sort, Butt_Date_Sort;
    private boolean bSort_Switch = false;

    private TextView txt_Within, txt_Description, txt_Deadline;

    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;

    final CDeadline[] aTest = new CDeadline[25];

    private FirebaseAuth mAuth;
    private int arrayInc = 0;
    private FirebaseDatabase fBase;
    private DatabaseReference mDataBase;
    ListView TaskList;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    private CRole tempRole = new CRole();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CTask tempTask = new CTask();
        tempTask.setName("My Task");

        tempRole.setTaskPermission(true);


        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        nav_spin = (Spinner) findViewById(R.id.nav_Spinner);
        nav_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getSelectedItem().toString();
                if (selection.equals("Projects") && spin_Clicked){
                    Intent intent = new Intent(Tasks.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(Tasks.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(Tasks.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Invitations") && spin_Clicked){
                    Intent intent = new Intent(Tasks.this, Invitations.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(Tasks.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(Tasks.this, Login.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                spin_Clicked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Create_Task = (ImageButton) findViewById(R.id.createTask);
        Create_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tasks.this, CreateTask.class);
                startActivity(intent);
            }
        });

        Butt_Name_Sort = (ImageButton) findViewById(R.id.sort_name_button);
        Butt_Name_Sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Put name sort stuff here
            }
        });

        Butt_Date_Sort = (ImageButton) findViewById(R.id.sort_date_button);
        Butt_Date_Sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Put date sort stuff here
            }
        });

        txt_Within = (TextView) findViewById(R.id.encasedWithin);
        txt_Deadline = (TextView) findViewById(R.id.encasedDeadline);
        txt_Description = (TextView) findViewById(R.id.encasedDescription);
        mDataBase = FirebaseDatabase.getInstance().getReference("projects").child(((CStoreIDs)getApplication()).getProjectID());
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txt_Within.setText(dataSnapshot.child("name").getValue().toString());
                txt_Deadline.setText(dataSnapshot.child("deadline").getValue().toString());
                txt_Description.setText(dataSnapshot.child("summary").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //txt_Within.setText(((CStoreIDs)getApplication()).getProjectID().toString());
        //ref = ref.child(((CStoreIDs)getApplication()).getProjectID()).child("");
/*
        if(!tempRole.getTaskPermission())
            Create_Task.setVisibility(View.GONE);
*/

        /*tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llTaskOptionsLayout.getVisibility() == View.VISIBLE)
                    llTaskOptionsLayout.setVisibility(View.GONE);
                Intent intent = new Intent(Tasks.this, Jobs.class);
                startActivity(intent);
            }
        });*/

        // btnViewTaskOptions OnCLickListener
/*
        llTaskOptionsLayout = (LinearLayout) findViewById(R.id.llTaskOptions);
        tempButton = (Button) findViewById(R.id.btnViewTaskOptions);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llTaskOptionsLayout.getVisibility() == View.GONE)
                    llTaskOptionsLayout.setVisibility(View.VISIBLE);
                else
                    llTaskOptionsLayout.setVisibility(View.GONE);
            }
        });

        // btnDeleteTask OnCLickListener
        Butt_TaskOption = (Button) findViewById(R.id.btnDeleteTask);
        Butt_TaskOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llTaskOptionsLayout.removeAllViews();
                llTaskOptionsLayout = (LinearLayout)findViewById(R.id.Tasks);
                llTaskOptionsLayout.removeAllViews();
            }
        });
        if(!tempRole.getTaskPermission())
            Butt_TaskOption.setVisibility(View.GONE);
*/

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

        mDataBase = fBase.getReference("users").child(user.getUid());//.child("TaskList");
        mDataBase.addChildEventListener(new ChildEventListener() {
            //IT GETS IN HERE
            //int i = 0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //int i = 0;
                for(DataSnapshot mtest :dataSnapshot.getChildren())
                {
                    if(mtest != null && mtest.getKey().toString().equals(((CStoreIDs)getApplication()).getProjectID()))
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

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Tasks.this, Projects.class);
        startActivity(intent);
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
                                    //*/
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
                                                    /*
                                                    CJob tempJob = new CJob(zName,zDeadline,zSumm,zComplete);
                                                    _array[arrayInc] = tempJob;
                                                    arrayInc++;
                                                    //*/
                                                    //i = k;
                                                }
                                            }
                                        }
                                    }

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
        TaskList = (ListView) findViewById(R.id.TaskListView);
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

        TaskList.setAdapter(testAdapt);
        //THIS IS WHAT NEEDS TO BE ADDED TO MAKE IT DO STUFF ON CLICK
        TaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: make this work lol
                ((CStoreIDs)getApplication()).setTaskID(HomeScreen.global.deadlines[position].getUniqueID());
                Toast.makeText(Tasks.this,((CStoreIDs)getApplication()).getTaskID().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        TaskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ((CStoreIDs)getApplication()).setProjectID(HomeScreen.global.deadlines[position].getUniqueID());
                Intent intent = new Intent(Tasks.this, Jobs.class);
                startActivity(intent);
                return false;
            }
        });
    }
}
