package com.example.androidptit.view;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidptit.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private ActivityHomeBinding activityHomeBinding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());
    }
}