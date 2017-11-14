package com.example.leon.deadline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Roles extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button Butt_Home;

    public FloatingActionButton Create_Role;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        nav_spin = (Spinner) findViewById(R.id.nav_Spinner);
        nav_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getSelectedItem().toString();
                if (selection.equals("Projects") && spin_Clicked){
                    Intent intent = new Intent(Roles.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(Roles.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(Roles.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(Roles.this, "Version: 171109_P3", Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(Roles.this, Login.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                spin_Clicked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Roles.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        Create_Role = (FloatingActionButton) findViewById(R.id.createRole);
        Create_Role.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Roles.this, CreateEditRoles.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        nav_spin.setSelection(0);
    }
}
