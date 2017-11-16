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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CreateEditRoles extends AppCompatActivity {
    private Spinner nav_spin;
    private Boolean spin_Clicked = false;

    private Button Butt_Home;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private EditText etxtName;
    private Switch swAddMembersPermission;
    private Switch swRemoveMemberPermission;
    private Switch swEditMemberPermission;
    private Switch swJobPermission;
    private Switch swTaskPermission;
    private Switch swRolePermission;
    private Switch swProjectPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_roles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEditRoles.this, HomeScreen.class);
                startActivity(intent);
            }
        });


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

        nav_spin = (Spinner) findViewById(R.id.nav_Spinner);
        nav_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getSelectedItem().toString();
                if (selection.equals("Projects") && spin_Clicked){
                    Intent intent = new Intent(CreateEditRoles.this, Projects.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Settings") && spin_Clicked){
                    Intent intent = new Intent(CreateEditRoles.this, Settings.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("Account") && spin_Clicked){
                    Intent intent = new Intent(CreateEditRoles.this, AccountInfo.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                else if (selection.equals("About") && spin_Clicked){
                    Toast.makeText(CreateEditRoles.this, R.string.version_number, Toast.LENGTH_SHORT).show();
                    nav_spin.setSelection(0);
                }
                else if (selection.equals("Logout") && spin_Clicked){
                    mAuth.signOut();
                    Intent intent = new Intent(CreateEditRoles.this, Login.class);
                    nav_spin.setSelection(0);
                    startActivity(intent);
                }
                spin_Clicked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ToDo: Store the Task and Job Ids the Role has access to as well
        etxtName                    = (EditText) findViewById(R.id.etxtRoleName);
        swAddMembersPermission      = (Switch) findViewById(R.id.swAddMember);
        swEditMemberPermission      = (Switch) findViewById(R.id.swEditMember);
        swRemoveMemberPermission    = (Switch) findViewById(R.id.swRemoveMember);
        swJobPermission             = (Switch) findViewById(R.id.swJobControl);
        swTaskPermission            = (Switch) findViewById(R.id.swTaskControl);
        swRolePermission            = (Switch) findViewById(R.id.swRoleControl);
        swProjectPermission         = (Switch) findViewById(R.id.swProjectControl);

        //TODO: Add a button to the content xml for creating the role, make an onclicklistener for that button inside of oncreate and put this inside the
        //TODO: onclicklistener with some error checking in front of it

        Button tempBtn = (Button) findViewById(R.id.btnCreateRole);
        tempBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etxtName.getText().toString().isEmpty()) {
                    CreateRole(etxtName.getText().toString(), swAddMembersPermission.isChecked(), swEditMemberPermission.isChecked(), swRemoveMemberPermission.isChecked(),
                            swJobPermission.isChecked(), swTaskPermission.isChecked(), swRolePermission.isChecked(), swProjectPermission.isChecked());
                }
            }
        });
        /*CreateRole(etxtName.getText().toString(), swAddMembersPermission.isChecked(), swEditMemberPermission.isChecked(), swRemoveMemberPermission.isChecked(),
                    swJobPermission.isChecked(), swTaskPermission.isChecked(), swRolePermission.isChecked(), swProjectPermission.isChecked());*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        nav_spin.setSelection(0);
    }


    public void CreateRole(String Name, boolean AddMembersPermission, boolean RemoveMemberPermission, boolean EditMemberPermission,
                           boolean JobPermission, boolean TaskPermission, boolean RolePermission, boolean ProjectPermission)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("projects");


        //TODO: Make this key dynamic instead of set
        //TODO: In order to add this under the correct project the CStoreIDs will have to be updated with the correct ids
        //TODO: In order to get those ids we need to be able to click on the custom xmls and have it update the id to what was clicked


        String newKey = ref.child(user.getUid()).child("roles").push().getKey();
        ref = ref.child(((CStoreIDs)getApplication()).getProjectID()).child("roles");

        ref.child(newKey).child("Name").setValue(Name);
        ref.child(newKey).child("AddMembersPermission").setValue(AddMembersPermission);
        ref.child(newKey).child("RemoveMemberPermission").setValue(RemoveMemberPermission);
        ref.child(newKey).child("EditMemberPermission").setValue(EditMemberPermission);
        ref.child(newKey).child("JobPermission").setValue(JobPermission);
        ref.child(newKey).child("TaskPermission").setValue(TaskPermission);
        ref.child(newKey).child("RolePermission").setValue(RolePermission);
        ref.child(newKey).child("ProjectPermission").setValue(ProjectPermission);
        //*/

        Toast.makeText(CreateEditRoles.this,"Role creation successful",Toast.LENGTH_SHORT).show();
        //*/


        Intent intent = new Intent(CreateEditRoles.this, Roles.class);
        startActivity(intent);
    }
}
