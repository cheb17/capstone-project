package com.example.capstoneapp;

import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecordsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(RecordsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    List<Record> records = parseResponse(responseBody);

                    runOnUiThread(() -> {
                        RecordsAdapter adapter = new RecordsAdapter(records);
                        recyclerView.setAdapter(adapter);
                    });
                } else {
                    // Handle error
                }
            }
        });
    }

    private List<Record> parseResponse(String responseBody) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Record>>(){}.getType();
        List<Record> records = gson.fromJson(responseBody, listType);
        return records;
    }
}
