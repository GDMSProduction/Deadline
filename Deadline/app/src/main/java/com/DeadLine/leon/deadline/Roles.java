package com.DeadLine.leon.deadline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Roles extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button Butt_Home;

    public FloatingActionButton Create_Role;

    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    private LinearLayout llRoleLayout;
    private Button Butt_RoleOptions;
    private Button Butt_ViewRole;
    private Button Butt_EditRole;
    private Button Butt_DeleteRole;


    private CRole tempRole = new CRole();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tempRole.setRolePermission(true);

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
                else if (selection.equals("Invitations") && spin_Clicked){
                    Intent intent = new Intent(Roles.this, Invitations.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(Roles.this, R.string.version_number, Toast.LENGTH_SHORT).show();
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
        if(!tempRole.getRolePermission())
            Create_Role.setVisibility(View.GONE);


       Butt_EditRole = (Button) findViewById(R.id.btnEditRole);
        Butt_EditRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Roles.this, EditRoles.class);
                startActivity(intent);
            }
        });
        if(!tempRole.getRolePermission())
            Butt_EditRole.setVisibility(View.GONE);

        Butt_ViewRole = (Button) findViewById(R.id.btnRoles);
        Butt_ViewRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Roles.this, RolePermissions.class);
                startActivity(intent);
            }
        });


        // btnViewJobOptions OnCLickListener
        llRoleLayout = (LinearLayout) findViewById(R.id.llRoleOptions);
        Butt_RoleOptions = (Button) findViewById(R.id.btnViewRoleOptions);
        Butt_RoleOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llRoleLayout.getVisibility() == View.GONE)
                    llRoleLayout.setVisibility(View.VISIBLE);
                else
                    llRoleLayout.setVisibility(View.GONE);
            }
        });

        // btnDeleteJob OnCLickListener
        Butt_DeleteRole = (Button) findViewById(R.id.btnDeleteRole);
        Butt_DeleteRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llRoleLayout.removeAllViews();
                llRoleLayout = (LinearLayout)findViewById(R.id.llRoles);
                llRoleLayout.removeAllViews();
            }
        });
        if(!tempRole.getRolePermission())
            Butt_DeleteRole.setVisibility(View.GONE);

    }


    @Override
    protected void onStart() {
        super.onStart();
        nav_spin.setSelection(0);
    }
}
