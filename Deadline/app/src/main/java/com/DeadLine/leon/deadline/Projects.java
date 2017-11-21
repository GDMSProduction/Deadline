package com.DeadLine.leon.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Projects extends AppCompatActivity {

    private ImageButton Create_Project, Butt_Sort, Butt_Filter;
    private boolean bSort_Switch = false;

    private Button Butt_Home, Butt_Edit;

    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;

    final CDeadline[] aTest = new CDeadline[25];

    private FirebaseAuth mAuth;
    private int arrayInc = 0;
    private FirebaseDatabase fBase;
    private DatabaseReference mDataBase;
    ListView ProjectList;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    private CRole tempRole = new CRole();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CProject tempProject = new CProject();
        tempProject.setName("My Project");
        tempRole.setProjectPermission(true);

        fBase = FirebaseDatabase.getInstance();

        Create_Project = (ImageButton) findViewById(R.id.createProj);
        Create_Project.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Projects.this, ProjectCreationScreen.class);
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

        if(!tempRole.getProjectPermission())
            Create_Project.setVisibility(View.GONE);


        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Projects.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        Butt_Edit = (Button) findViewById(R.id.editProj);
        Butt_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CStoreIDs)getApplication()).getProjectID().toString() != null){
                    Intent intent = new Intent(Projects.this, EditProject.class);
                    startActivity(intent);
                }
            }
        });

        //mAuth = FirebaseAuth.getInstance();

        nav_spin = (Spinner) findViewById(R.id.nav_Spinner);
        nav_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getSelectedItem().toString();
                if (selection.equals("Projects") && spin_Clicked){
                    //Intent intent = new Intent(Projects.this, Projects.class);
                    nav_spin.setSelection(0);
                    //startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(Projects.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(Projects.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Invitations") && spin_Clicked){
                    Intent intent = new Intent(Projects.this, Invitations.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(Projects.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(Projects.this, Login.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                spin_Clicked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Butt_Sort = (ImageButton) findViewById(R.id.sort_button);
        Butt_Sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bSort_Switch) {
                    sortDeadlinesNamesDescending();
                    bSort_Switch = true;
                }
                else{
                    sortDeadlinesNamesAscending();
                    bSort_Switch = false;
                }
                populateScreen(aTest);
            }
        });

        Butt_Filter = (ImageButton) findViewById(R.id.filter_button);
        Butt_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

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
    public void onBackPressed()
    {
        Intent intent = new Intent(Projects.this, HomeScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
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
                                    CProject temp = new CProject(name, deadline, summary, complete);
                                    temp.setUniqueID(_key);
                                    _array[arrayInc] = temp;
                                    arrayInc++;
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
/*
                                    CTask tempTask = new CTask(tName,tDeadline,tSumm,tComplete);
                                    _array[arrayInc] = tempTask;
                                    arrayInc++;
                                    //*/
                                }
                                //TESTING
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
        ProjectList = (ListView) findViewById(R.id.ProjectListView);
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

        //THIS IS WHAT NEEDS TO BE ADDED TO MAKE IT DO STUFF ON CLICK
        ProjectList.setAdapter(testAdapt);
        ProjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((CStoreIDs)getApplication()).setProjectID(HomeScreen.global.deadlines[position].getUniqueID());
                //Toast.makeText(Projects.this,((CStoreIDs)getApplication()).getProjectID(),Toast.LENGTH_SHORT).show();
            }
        });

        ProjectList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ((CStoreIDs)getApplication()).setProjectID(HomeScreen.global.deadlines[position].getUniqueID());
                Intent intent = new Intent(Projects.this, Tasks.class);
                startActivity(intent);
                return false;
            }
        });
    }
    public void sortDeadlinesNamesDescending(){
        List<CDeadline> sortTasks = new ArrayList<>();
        List<CDeadline> oldTasks = new ArrayList<>();
        for(int i = 0; i < aTest.length; i++){
            if(aTest[i] != null)
                oldTasks.add(aTest[i]);
        }
        int taskIndex;
        while(oldTasks.size() > 0) {
            taskIndex = 0;
            for (int i = 0; i < oldTasks.size(); i++) {
                if(oldTasks.get(taskIndex).getName().toLowerCase().compareTo(oldTasks.get(i).getName().toLowerCase()) > 0)
                    taskIndex = i;
            }
            sortTasks.add(new CDeadline(oldTasks.get(taskIndex)));
            oldTasks.remove(taskIndex);
        }

        for(int i = 0; i < aTest.length; i++){
            if(aTest[i] != null) {
                aTest[i] = sortTasks.get(0);
                sortTasks.remove(0);
            }
        }

    }

    public void sortDeadlinesNamesAscending(){
        List<CDeadline> sortTasks = new ArrayList<>();
        List<CDeadline> oldTasks = new ArrayList<>();
        for(int i = 0; i < aTest.length; i++){
            if(aTest[i] != null)
                oldTasks.add(aTest[i]);
        }
        int taskIndex;
        while(oldTasks.size() > 0) {
            taskIndex = 0;
            for (int i = 0; i < oldTasks.size(); i++) {
                if(oldTasks.get(taskIndex).getName().toLowerCase().compareTo(oldTasks.get(i).getName().toLowerCase()) < 0)
                    taskIndex = i;
            }
            sortTasks.add(new CDeadline(oldTasks.get(taskIndex)));
            oldTasks.remove(taskIndex);
        }

        for(int i = 0; i < aTest.length; i++){
            if(aTest[i] != null) {
                aTest[i] = sortTasks.get(0);
                sortTasks.remove(0);
            }
        }

    }

    public void sortDeadlinesDescending(){
        List<CDeadline> sortTasks = new ArrayList<>();
        List<CDeadline> oldTasks = new ArrayList<>();
        for(int i = 0; i < aTest.length; i++){
            if(aTest[i] != null)
                oldTasks.add(aTest[i]);
        }
        int taskIndex;
        while(oldTasks.size() > 0) {
            taskIndex = 0;
            for (int i = 0; i < oldTasks.size(); i++) {
                if(oldTasks.get(taskIndex).getDeadline().compareTo(oldTasks.get(i).getDeadline()) > 0)
                    taskIndex = i;
            }
            sortTasks.add(new CDeadline(oldTasks.get(taskIndex)));
            oldTasks.remove(taskIndex);
        }

        for(int i = 0; i < aTest.length; i++){
            if(aTest[i] != null) {
                aTest[i] = sortTasks.get(0);
                sortTasks.remove(0);
            }
        }

    }

    public void sortDeadlinesAscending(){
        List<CDeadline> sortTasks = new ArrayList<>();
        List<CDeadline> oldTasks = new ArrayList<>();
        for(int i = 0; i < aTest.length; i++){
            if(aTest[i] != null)
                oldTasks.add(aTest[i]);
        }
        int taskIndex;
        while(oldTasks.size() > 0) {
            taskIndex = 0;
            for (int i = 0; i < oldTasks.size(); i++) {
                if(oldTasks.get(taskIndex).getDeadline().compareTo(oldTasks.get(i).getDeadline()) < 0)
                    taskIndex = i;
            }
            sortTasks.add(new CDeadline(oldTasks.get(taskIndex)));
            oldTasks.remove(taskIndex);
        }

        for(int i = 0; i < aTest.length; i++){
            if(aTest[i] != null) {
                aTest[i] = sortTasks.get(0);
                sortTasks.remove(0);
            }
        }

    }
}
