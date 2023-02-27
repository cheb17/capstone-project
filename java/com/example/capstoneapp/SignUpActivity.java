package com.example.capstoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
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

public class SignUpActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText conPassword;
    Button signUp;
    OkHttpClient client;
    String postURL = "";
//    private EditText androidIdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        conPassword = findViewById(R.id.editConfirmPassword);
        signUp = findViewById(R.id.btnSignUp);
        client = new OkHttpClient();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
    }
    public void post(){
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String userConPassword = conPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userEmail)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.setError("Please enter a valid email address");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            password.setError("Please enter a password");
            password.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userConPassword)) {
            conPassword.setError("Please confirm your password");
            conPassword.requestFocus();
            return;
        }

        if (!userPassword.equals(userConPassword)) {
            conPassword.setError("Passwords do not match");
            conPassword.requestFocus();
            return;
        }

        RequestBody reqBody = new FormBody.Builder()
                .add("email", userEmail)
                .add("password", userPassword)
                .add("conPassword", userConPassword)
                .build();

        Request request = new Request.Builder()
                .url(postURL)
                .post(reqBody)
                .build();

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
                            String responseBody = response.body().string();
                            if (response.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, responseBody, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, StartActivity.class);
                                startActivity(intent);
                            } else {
                                // handle error response
                                Toast.makeText(SignUpActivity.this, "Error: " + responseBody, Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }
}