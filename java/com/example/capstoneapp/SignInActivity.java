package com.example.capstoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {

    private Button btnSignIn;
    private EditText email;
    private EditText password;

    private OkHttpClient client = new OkHttpClient();
    private String postURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.btnEnter);
        email = findViewById(R.id.editEmailAddress);
        password = findViewById(R.id.editPassword);

        btnSignIn.setOnClickListener(v -> postCredentials());
    }

    private void postCredentials() {
        RequestBody reqBody = new FormBody.Builder()
                .add("email", email.getText().toString())
                .add("password", password.getText().toString())
                .build();
        Request request = new Request.Builder()
                .url(postURL)
                .post(reqBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignInActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String bodyText = response.body().string();
                Log.d("SignInActivity", "Response: " + bodyText);
                if (response.isSuccessful()) {
                    // If the response code is 2xx (successful), proceed to the HomeActivity
                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    // If the response code is anything else, display an error message
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignInActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
