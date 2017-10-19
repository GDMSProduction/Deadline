package com.example.leon.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
<<<<<<< HEAD:Deadline/app/src/main/java/com/example/leon/deadline/ProjectCreationScreen.java
=======
import android.widget.EditText;
>>>>>>> parent of b8fa916... Organization and renaming:Deadline/app/src/main/java/com/example/leon/deadline/ProjectCreationScreen.java

public class ProjectCreationScreen extends AppCompatActivity {

    private Button projCreateButton;
    private Button Butt_Home;
    private CProject tempProject = new CProject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_creation_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        projCreateButton = (Button) findViewById(R.id.projCreate);
        projCreateButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
<<<<<<< HEAD:Deadline/app/src/main/java/com/example/leon/deadline/ProjectCreationScreen.java
                Intent intent = new Intent(ProjectCreationScreen.this, Projects.class);
                intent.putExtra("TempProj", tempProject);
=======
                tempProjName = ((EditText) findViewById(R.id.projName)).getText().toString();
                Intent intent = new Intent(ProjectCreationScreen.this, Projects.class);
                intent.putExtra("TempProjName", tempProjName);
>>>>>>> parent of b8fa916... Organization and renaming:Deadline/app/src/main/java/com/example/leon/deadline/ProjectCreationScreen.java
                startActivity(intent);
            }
        });

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectCreationScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }

}
