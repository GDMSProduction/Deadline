package com.DeadLine.leon.deadline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class EditProject extends AppCompatActivity {

    private Button Butt_Home;
    private Button Butt_Save;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private Calendar currentDay;
    private Calendar validDate;

    private EditText pName;
    private DatePicker pDate;
    private EditText pSummary;
    private CheckBox pComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
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


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("projects");
        ref = ref.child(((CStoreIDs)getApplication()).getProjectID());
        //TODO: Set the text by pulling from FB
        pName = (EditText) findViewById(R.id.projName);
        pName.setText(ref.child("name").toString());
        pDate = (DatePicker) findViewById(R.id.datePicker);
        pSummary = (EditText) findViewById(R.id.projDescription);
        pSummary.setText(ref.child("summary").toString());
        pComplete = (CheckBox) findViewById(R.id.projComplete);
        pComplete.setChecked(false);

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProject.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        Butt_Save = (Button) findViewById(R.id.projEdit);
        Butt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentDay = Calendar.getInstance();
                validDate =  Calendar.getInstance();
                validDate.set(pDate.getYear(),pDate.getMonth(),pDate.getDayOfMonth());

                if(!pName.equals("") &&
                        !pDate.equals(""))
                {
                    if(!currentDay.after(validDate))
                    {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("projects");
                        ref = ref.child(((CStoreIDs)getApplication()).getProjectID());
                        ref.child("name").setValue(pName.getText().toString());
                        ref.child("deadline").setValue(((DatePicker) findViewById(R.id.datePicker)).getMonth() + 1 + "/" + ((DatePicker) findViewById(R.id.datePicker)).getDayOfMonth() + "/" + ((DatePicker) findViewById(R.id.datePicker)).getYear());
                        ref.child("summary").setValue(pSummary.getText().toString());
                        ref.child("complete").setValue(pComplete.isChecked());
                    }
                    else
                    {
                        Toast.makeText(EditProject.this, "Please enter a valid date", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(EditProject.this, "Please fill out the form completely", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(EditProject.this, Projects.class);
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
                    Intent intent = new Intent(EditProject.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(EditProject.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(EditProject.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(EditProject.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(EditProject.this, Login.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                spin_Clicked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        nav_spin.setSelection(0);
    }
}
