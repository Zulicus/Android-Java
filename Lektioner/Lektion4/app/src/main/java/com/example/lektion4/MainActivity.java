package com.example.lektion4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("lifeCycle", "onCreate: " + getApplicationContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifeCycle", "onStart: " + getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifeCycle", "onResume: " + getApplicationContext());

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifeCycle", "onPause: " + getApplicationContext());

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifeCycle", "onStop: " + getApplicationContext());

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifeCycle", "onRestart: " + getApplicationContext());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifeCycle", "onDestroy: " + getApplicationContext());

    }
}