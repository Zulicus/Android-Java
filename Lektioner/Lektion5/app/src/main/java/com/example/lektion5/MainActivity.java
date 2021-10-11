package com.example.lektion5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editEmail, editPass, editNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("lifeCycle", "onCreate: " + getApplicationContext());
        editEmail = findViewById(R.id.editTextTextEmailAddress);
        editPass = findViewById(R.id.editTextTextPassword);
        editNumber = findViewById(R.id.editTextPhone);
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

    public void Click(View view) {


        Intent intent = new Intent(this, SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        String editEmailValue = editEmail.getText().toString();
        String editPassValue = editPass.getText().toString();
        String editNumberValue = editNumber.getText().toString();
        //intent.putExtra("editEmail",editEmailValue); Only one Value!

        Bundle bundle = new Bundle();

        bundle.putString("editEmail", editEmailValue);
        bundle.putString("editPass", editPassValue);
        bundle.putString("editNumber", editNumberValue);
        intent.putExtra("editBundle", bundle);
        startActivity(intent);
    }
}