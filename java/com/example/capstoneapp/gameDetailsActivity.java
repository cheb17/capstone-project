package com.example.capstoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class gameDetailsActivity extends AppCompatActivity {

    Button start;
    OkHttpClient client;
    String postURL = "";
    EditText teamName;
    EditText opponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        start = findViewById(R.id.btnStart);
        teamName = findViewById(R.id.editTeamName);
        opponent = findViewById(R.id.editOpponent);

        start.setOnClickListener(v -> openPlotActivity());
    }

    public void openPlotActivity(){
        Intent intent = new Intent(this, PlotActivity.class);
        intent.putExtra("filename", "myfilename.txt");
        startActivity(intent);
        post();
    }
    public void post(){
        RequestBody reqBody = new FormBody.Builder()
                .add("teamName","teamName")
                .add("opponent", "opponent")
                .build();
        Request request = new Request.Builder().url(postURL).post(reqBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            teamName.setText(response.body().string());
                            opponent.setText(response.body().string());
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}