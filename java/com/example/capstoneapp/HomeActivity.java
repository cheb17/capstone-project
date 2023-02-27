package com.example.capstoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button btnstart;
    Button btnRecords;
    Button btnCred;
    Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnstart = findViewById(R.id.startGame);
        btnRecords = findViewById(R.id.records);
        btnCred = findViewById(R.id.credentials);
        btnSignOut = findViewById(R.id.signout);

        btnstart.setOnClickListener(v -> opengameDetailsActivity());

        btnRecords.setOnClickListener(v -> openRecordsActivity());

        btnCred.setOnClickListener(v -> openCredentialsActivity());

        btnSignOut.setOnClickListener(v -> openStartActivity());
    }
    public void opengameDetailsActivity(){
        Intent intent = new Intent(this, gameDetailsActivity.class);
        startActivity(intent);
    }

    public void openRecordsActivity(){
        Intent intent = new Intent(this, RecordsActivity.class);
        startActivity(intent);
    }

    public void openCredentialsActivity(){
        Intent intent = new Intent(this, CredentialsActivity.class);
        startActivity(intent);
    }

    public void openStartActivity(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}