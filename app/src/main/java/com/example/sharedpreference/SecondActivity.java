package com.example.sharedpreference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sharedpreference.LoginModel.Login;
import com.example.sharedpreference.databinding.ActivitySecondBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding binding;
    private ArrayList<Login> loginArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setToolbar();
        loadData();

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.tvLogin.getText().toString())) {
                    Toast.makeText(SecondActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    String phone = "+91" + binding.tvLogin.getText().toString();
                    Login login = new Login();
                    login.PhoneNo = phone;
                    loginArrayList.add(login);
                    saveData();
                }

                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }


    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson1 = new Gson();
        String json = gson1.toJson(loginArrayList);
        editor.putString("Login", json);
        editor.apply();
        Toast.makeText(this, "Saved Array List to Shared Preferences.", Toast.LENGTH_LONG).show();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared Preferences", MODE_PRIVATE);
        Gson gson1 = new Gson();
        String json = sharedPreferences.getString("Login", null);
        Type type = new TypeToken<ArrayList<Login>>() {
        }.getType();
        loginArrayList = gson1.fromJson(json, type);

        if (loginArrayList == null) {
            loginArrayList = new ArrayList<>();
        }
    }
}