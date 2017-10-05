package com.example.leon.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateProject extends AppCompatActivity {

    private Button projCreateButton;
    private Button Butt_Home;
    private CProject tempProject = new CProject();
    private String tempProjName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        projCreateButton = (Button) findViewById(R.id.projCreate);
        projCreateButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                tempProjName = ((EditText) findViewById(R.id.projName)).getText().toString();
                Intent intent = new Intent(CreateProject.this, Projects.class);
                intent.putExtra("TempProjName", tempProjName);
                startActivity(intent);
            }
        });

        Butt_Home = (Button) findViewById(R.id.Home_Button);
        Butt_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateProject.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }

}
